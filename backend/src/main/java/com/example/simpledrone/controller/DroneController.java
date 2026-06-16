package com.example.simpledrone.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.simpledrone.domain.Drone;
import com.example.simpledrone.dto.ApiResponse;
import com.example.simpledrone.dto.DroneRequest;
import com.example.simpledrone.service.DroneService;

@RestController
@RequestMapping("/api/drones")
public class DroneController {
    private final DroneService droneService;

    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @GetMapping
    public ApiResponse<List<Drone>> list(@RequestParam(required = false) String keyword) {
        return ApiResponse.ok(droneService.list(keyword));
    }

    @PostMapping
    public ApiResponse<Drone> create(@Valid @RequestBody DroneRequest request) {
        return ApiResponse.ok(droneService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<Drone> update(@PathVariable Long id, @Valid @RequestBody Drone drone) {
        return ApiResponse.ok(droneService.update(id, drone));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        droneService.delete(id);
        return ApiResponse.ok(null);
    }
}
