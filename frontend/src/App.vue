<template>
  <main class="page">
    <section v-if="!user.authenticated" class="login-card">
      <h1>无人机信息管理</h1>
      <form @submit.prevent="login">
        <label>用户名<input v-model.trim="loginForm.username" class="form-control"></label>
        <label>密码<input v-model="loginForm.password" type="password" class="form-control"></label>
        <button class="btn btn-primary btn-block" type="submit">登录</button>
      </form>
      <p v-if="message" class="message error">{{ message }}</p>
    </section>

    <section v-else>
      <header class="topbar">
        <div>
          <h1>无人机信息管理</h1>
          <span>{{ user.username }}</span>
        </div>
        <button class="btn btn-light" @click="logout">退出</button>
      </header>

      <section class="toolbar">
        <input v-model.trim="keyword" class="form-control" placeholder="搜索名称、型号、厂商、序列号" @keyup.enter="loadDrones">
        <button class="btn btn-primary" @click="loadDrones">查询</button>
        <button class="btn btn-success" @click="newDrone">新增</button>
      </section>

      <p v-if="message" :class="['message', messageType]">{{ message }}</p>

      <section class="layout">
        <div class="table-box">
          <table class="table table-hover">
            <thead>
              <tr>
                <th>名称</th>
                <th>型号</th>
                <th>厂商</th>
                <th>类型</th>
                <th>续航</th>
                <th>速度</th>
                <th>状态</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="drone in drones" :key="drone.id">
                <td>{{ drone.name }}<small>{{ drone.serialNumber }}</small></td>
                <td>{{ drone.model }}</td>
                <td>{{ drone.manufacturer }}</td>
                <td>{{ drone.droneType }}</td>
                <td>{{ drone.flightMinutes }} 分钟</td>
                <td>{{ drone.speedKmh }} km/h</td>
                <td><span class="badge badge-success">{{ drone.status }}</span></td>
                <td>
                  <button class="btn btn-sm btn-outline-primary" @click="editDrone(drone)">改</button>
                  <button class="btn btn-sm btn-outline-danger" @click="deleteDrone(drone)">删</button>
                </td>
              </tr>
              <tr v-if="drones.length === 0">
                <td colspan="8" class="empty">暂无数据</td>
              </tr>
            </tbody>
          </table>
        </div>

        <aside v-if="editor.visible" class="editor">
          <h2>{{ editor.mode === 'create' ? '新增无人机' : '修改无人机' }}</h2>
          <form @submit.prevent="saveDrone">
            <label>名称<input v-model.trim="form.name" class="form-control"></label>
            <label>型号<input v-model.trim="form.model" class="form-control" :readonly="editor.mode === 'create'"></label>
            <label>厂商<input v-model.trim="form.manufacturer" class="form-control" :readonly="editor.mode === 'create'"></label>
            <label>序列号<input v-model.trim="form.serialNumber" class="form-control" :readonly="editor.mode === 'create'"></label>
            <label>类型<input v-model.trim="form.droneType" class="form-control" :readonly="editor.mode === 'create'"></label>
            <label>状态
              <select v-model="form.status" class="form-control">
                <option>待检</option>
                <option>在役</option>
                <option>维修</option>
                <option>退役</option>
              </select>
            </label>
            <label>续航<input v-model.number="form.flightMinutes" type="number" class="form-control" :readonly="editor.mode === 'create'"></label>
            <label>速度<input v-model.number="form.speedKmh" type="number" class="form-control" :readonly="editor.mode === 'create'"></label>
            <label>载重<input v-model.number="form.payloadKg" type="number" step="0.1" class="form-control" :readonly="editor.mode === 'create'"></label>
            <label>说明<textarea v-model.trim="form.description" class="form-control" rows="3" :readonly="editor.mode === 'create'"></textarea></label>
            <div class="buttons">
              <button class="btn btn-primary" type="submit">保存</button>
              <button class="btn btn-light" type="button" @click="closeEditor">取消</button>
            </div>
          </form>
        </aside>
      </section>
    </section>
  </main>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { authApi, droneApi } from './api'

const user = reactive({ authenticated: false, username: '' })
const loginForm = reactive({ username: 'admin', password: 'admin123' })
const keyword = ref('')
const drones = ref([])
const message = ref('')
const messageType = ref('error')
const editor = reactive({ visible: false, mode: 'create', id: null })
const form = reactive(emptyForm())

onMounted(checkLogin)

async function checkLogin() {
  try {
    const res = await authApi.me()
    Object.assign(user, res.data)
    if (user.authenticated) loadDrones()
  } catch {
    user.authenticated = false
  }
}

async function login() {
  await run(async () => {
    const res = await authApi.login(loginForm)
    localStorage.setItem('simple-drone-token', res.data.token)
    Object.assign(user, res.data)
    await loadDrones()
  }, '登录成功')
}

async function logout() {
  await authApi.logout().catch(() => null)
  localStorage.removeItem('simple-drone-token')
  user.authenticated = false
  drones.value = []
}

async function loadDrones() {
  await run(async () => {
    const res = await droneApi.list(keyword.value)
    drones.value = res.data || []
  })
}

function newDrone() {
  Object.assign(form, emptyForm())
  editor.mode = 'create'
  editor.id = null
  editor.visible = true
}

function editDrone(drone) {
  Object.assign(form, drone)
  editor.mode = 'edit'
  editor.id = drone.id
  editor.visible = true
}

async function saveDrone() {
  await run(async () => {
    if (editor.mode === 'create') {
      await droneApi.create({ name: form.name, status: form.status })
    } else {
      await droneApi.update(editor.id, form)
    }
    closeEditor()
    await loadDrones()
  }, '保存成功')
}

async function deleteDrone(drone) {
  if (!confirm(`确认删除 ${drone.name}？`)) return
  await run(async () => {
    await droneApi.remove(drone.id)
    await loadDrones()
  }, '删除成功')
}

function closeEditor() {
  editor.visible = false
}

async function run(task, okMessage) {
  message.value = ''
  try {
    await task()
    if (okMessage) {
      messageType.value = 'success'
      message.value = okMessage
    }
  } catch (error) {
    messageType.value = 'error'
    message.value = error.message
  }
}

function emptyForm() {
  return {
    name: '',
    model: '',
    manufacturer: '',
    serialNumber: '',
    droneType: '',
    flightMinutes: '',
    speedKmh: '',
    payloadKg: '',
    status: '待检',
    description: ''
  }
}
</script>
