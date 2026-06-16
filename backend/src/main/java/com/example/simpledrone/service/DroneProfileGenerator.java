package com.example.simpledrone.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.example.simpledrone.domain.Drone;

@Component
public class DroneProfileGenerator {
    private static final String[] TYPES = {"巡检型", "测绘型", "物流型", "安防型", "农业型"};
    private static final String[] MAKERS = {"AeroVision", "SkyMatrix", "CloudForge", "HelioWorks"};

    public Drone generate(String name) {
        String safeName = name == null || name.trim().isEmpty() ? "无人机" : name.trim();
        int seed = Math.abs(safeName.hashCode() == Integer.MIN_VALUE ? 0 : safeName.hashCode());

        Drone drone = new Drone();
        drone.setName(safeName);
        drone.setModel("SD-" + (100 + seed % 900));
        drone.setManufacturer(MAKERS[seed % MAKERS.length]);
        drone.setSerialNumber("SIMPLE-" + Integer.toHexString(seed).toUpperCase());
        drone.setDroneType(TYPES[seed % TYPES.length]);
        drone.setFlightMinutes(25 + seed % 80);
        drone.setSpeedKmh(40 + seed % 90);
        drone.setPayloadKg(BigDecimal.valueOf(0.5 + (seed % 80) / 10.0).setScale(1, BigDecimal.ROUND_HALF_UP));
        drone.setDescription("系统自动生成属性，适合" + drone.getDroneType().replace("型", "") + "场景使用。");
        return drone;
    }
}
