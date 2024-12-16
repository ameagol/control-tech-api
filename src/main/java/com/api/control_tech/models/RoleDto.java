package com.api.control_tech.models;

import com.api.control_tech.persistence.entities.Role;
import lombok.Data;
@Data
public class RoleDto {
    private Long id;
    private String name;

    public RoleDto(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }
}
