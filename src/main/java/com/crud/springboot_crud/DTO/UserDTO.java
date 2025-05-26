package com.crud.springboot_crud.DTO;


import com.crud.springboot_crud.entity.User;
import com.crud.springboot_crud.util.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;
    @JsonProperty("lastLogin")
    private LocalDateTime lastLogin;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("role")
    private Role role;


    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.createdAt = user.getCreatedAt();
        this.lastLogin = user.getLastLogin();
        this.role = user.getRole();
    }
}
