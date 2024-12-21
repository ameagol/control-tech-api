package com.api.control_tech.models;

import com.api.control_tech.persistence.entities.Device;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DeviceDto {
    private Long id;
    private LocalDateTime createdAt;
    private String brand;
    private Integer channels;
    private Integer cpuGeneration;
    private String cpuModel;
    private Integer cpuQuantity;
    private String description;
    private String deviceGroup;
    private String function;
    private String model;
    private String patrim;
    private String memorySize;
    private Integer memorySlots;
    private String memoryType;
    private LocalDate purchaseDate;
    private BigDecimal purchaseValue;
    private String screenSize;
    private String serial;
    private String storage;
    private String technology;
    private String type;
    private String status;
    private String companyName;

    public DeviceDto(Device device) {
        this.id = device.getId();
        this.createdAt = device.getCreatedAt();
        this.brand = device.getBrand();
        this.channels = device.getChannels();
        this.cpuGeneration = device.getCpuGeneration();
        this.cpuModel = device.getCpuModel();
        this.cpuQuantity = device.getCpuQuantity();
        this.description = device.getDescription();
        this.function = device.getFunction();
        this.model = device.getModel();
        this.patrim = device.getPatrim();
        this.memorySize = device.getMemorySize();
        this.memorySlots = device.getMemorySlots();
        this.memoryType = device.getMemoryType();
        this.purchaseDate = device.getPurchaseDate();
        this.purchaseValue = device.getPurchaseValue();
        this.screenSize = device.getScreenSize();
        this.serial = device.getSerial();
        this.storage = device.getStorage();
        this.technology = device.getTechnology();
        this.type = device.getType();
        this.status = device.getStatus();
        this.deviceGroup = device.getDeviceGroup();
        this.companyName = device.getCompany().getName();
    }
}
