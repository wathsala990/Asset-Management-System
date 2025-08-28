package com.example.AMS.dto;

import lombok.Data;
import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private Set<String> roles;
    private boolean enabled;

    public UserDto(Long id, String username, String fullName, String email, Set<String> roles, boolean enabled) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.roles = roles;
        this.enabled = enabled;
    }

    public UserDto(Long id2, String username2, String fullName2, String email2, String profilePhotoUrl,
            Set<String> roleNames, boolean enabled2) {
        //TODO Auto-generated constructor stub
    }
}