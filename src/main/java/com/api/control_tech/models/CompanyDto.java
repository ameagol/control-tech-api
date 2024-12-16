package com.api.control_tech.models;

import com.api.control_tech.persistence.entities.Company;
import com.api.control_tech.persistence.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor // Optional for convenience
public class CompanyDto {
    private Long id;
    private String name;
    private UserDto user;

    public CompanyDto(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.user = company.getUser() != null ? new UserDto(company.getUser()) : null;
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


