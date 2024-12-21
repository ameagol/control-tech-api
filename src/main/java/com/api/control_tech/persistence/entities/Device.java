package com.api.control_tech.persistence.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String deviceGroup;

    @Column
    private Integer channels;

    @Column
    private String description;

    @Column
    private String function;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String patrim;

    @Column
    private String memorySize;

    @Column
    private Integer memorySlots;

    @Column
    private String memoryType;

    @Column
    private String cpuModel;

    @Column
    private Integer cpuQuantity;

    @Column
    private Integer cpuGeneration;

    @Column
    private LocalDate purchaseDate;

    @Column(precision = 10, scale = 2)
    private BigDecimal purchaseValue;

    @Column
    private String screenSize;

    @Column(nullable = false, unique = true)
    private String serial;

    @Column
    private String storage;

    @Column
    private String technology;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
}

