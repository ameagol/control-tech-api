package com.api.control_tech.persistence.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String serial;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String fru;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String owner;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String branch;

    @Column(nullable = false)
    private String invoice;

    @Column(nullable = false)
    private String description;
}
