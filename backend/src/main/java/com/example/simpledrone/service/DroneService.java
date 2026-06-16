package com.example.simpledrone.service;

import java.util.List;

import com.example.simpledrone.domain.Drone;
import com.example.simpledrone.dto.DroneRequest;

public interface DroneService {
    List<Drone> list(String keyword);
    Drone create(DroneRequest request);
    Drone update(Long id, Drone drone);
    void delete(Long id);
}
