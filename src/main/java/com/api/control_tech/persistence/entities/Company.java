package com.api.control_tech.persistence.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@Data
@NoArgsConstructor
@Table(name = "companies", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "user_id"}))
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Company(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public void setName(String name) {
        this.name = capitalizeFirstLetter(name);
    }

    private String capitalizeFirstLetter(String name) {
        return Optional.ofNullable(name)
                .filter(n -> !n.isEmpty())
                .map(n -> n.substring(0, 1).toUpperCase() + n.substring(1).toLowerCase())
                .orElse(name);
    }
}

