package com.example.simpledrone.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.simpledrone.domain.Drone;
import com.example.simpledrone.dto.DroneRequest;
import com.example.simpledrone.mapper.DroneMapper;
import com.example.simpledrone.service.DroneProfileGenerator;
import com.example.simpledrone.service.DroneService;

@Service
public class DroneServiceImpl implements DroneService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final DroneMapper droneMapper;
    private final DroneProfileGenerator generator;

    public DroneServiceImpl(DroneMapper droneMapper, DroneProfileGenerator generator) {
        this.droneMapper = droneMapper;
        this.generator = generator;
    }

    @Override
    public List<Drone> list(String keyword) {
        return droneMapper.selectList(keyword == null ? "" : keyword.trim());
    }

    @Override
    @Transactional
    public Drone create(DroneRequest request) {
        Drone drone = generator.generate(request.getName());
        drone.setStatus(request.getStatus() == null || request.getStatus().trim().isEmpty()
                ? "待检"
                : request.getStatus().trim());
        drone.setCreatedAt(LocalDateTime.now().format(FORMATTER));
        droneMapper.insert(drone);
        return drone;
    }

    @Override
    @Transactional
    public Drone update(Long id, Drone drone) {
        drone.setId(id);
        droneMapper.update(drone);
        return droneMapper.selectById(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        droneMapper.deleteById(id);
    }
}
