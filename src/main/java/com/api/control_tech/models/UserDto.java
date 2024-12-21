package com.api.control_tech.models;

import com.api.control_tech.persistence.entities.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UserDto {

    private Long id;
    private String email;
    private List<RoleDto> roles;
    private String name;
    private String resetHash;
    private Set<GrantedAuthority> authorities;

    public UserDto(String userName, Set<GrantedAuthority> authorities) {
        this.name = userName;
        this.authorities = authorities;
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
//        this.resetHash = user.getResetHash();
        this.roles = user.getRoles() != null
                ? user.getRoles().stream().map(RoleDto::new).collect(Collectors.toList())
                : null;
    }
}
