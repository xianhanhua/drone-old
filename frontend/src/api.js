import axios from 'axios'

const http = axios.create({
  baseURL: 'http://localhost:8092/api',
  timeout: 10000
})

http.interceptors.request.use(config => {
  const token = localStorage.getItem('simple-drone-token')
  if (token) {
    config.headers['X-Auth-Token'] = token
  }
  return config
})

http.interceptors.response.use(
  response => response.data,
  error => Promise.reject(new Error(error.response?.data?.message || error.message || '请求失败'))
)

export const authApi = {
  login: data => http.post('/auth/login', data),
  me: () => http.get('/auth/me'),
  logout: () => http.post('/auth/logout')
}

export const droneApi = {
  list: keyword => http.get('/drones', { params: { keyword } }),
  create: data => http.post('/drones', data),
  update: (id, data) => http.put(`/drones/${id}`, data),
  remove: id => http.delete(`/drones/${id}`)
}
