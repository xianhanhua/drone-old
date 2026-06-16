package com.example.simpledrone.domain;

import java.math.BigDecimal;

public class Drone {
    private Long id;
    private String name;
    private String model;
    private String manufacturer;
    private String serialNumber;
    private String droneType;
    private Integer flightMinutes;
    private Integer speedKmh;
    private BigDecimal payloadKg;
    private String status;
    private String description;
    private String createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
    public String getDroneType() { return droneType; }
    public void setDroneType(String droneType) { this.droneType = droneType; }
    public Integer getFlightMinutes() { return flightMinutes; }
    public void setFlightMinutes(Integer flightMinutes) { this.flightMinutes = flightMinutes; }
    public Integer getSpeedKmh() { return speedKmh; }
    public void setSpeedKmh(Integer speedKmh) { this.speedKmh = speedKmh; }
    public BigDecimal getPayloadKg() { return payloadKg; }
    public void setPayloadKg(BigDecimal payloadKg) { this.payloadKg = payloadKg; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
