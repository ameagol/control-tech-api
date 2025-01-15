package com.api.control_tech.models;

import com.api.control_tech.persistence.entities.Status;
import com.api.control_tech.persistence.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StatusDto {
    private Long id;
    private String name;

    private UserDto user;

    public StatusDto(Status status) {
        this.id = status.getId();
        this.name = status.getName() != null ? status.getName().toUpperCase() : null;
        this.user = status.getUser() != null ? new UserDto(status.getUser()) : null;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserDto {
        private String email;

        public UserDto(User user) {
            this.email = user.getEmail();
        }
    }
}
