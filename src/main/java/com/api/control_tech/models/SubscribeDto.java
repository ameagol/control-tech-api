package com.api.control_tech.models;

import lombok.Data;

@Data
public class SubscribeDto {
    private String email;
    private String password;
    private String company;
}
