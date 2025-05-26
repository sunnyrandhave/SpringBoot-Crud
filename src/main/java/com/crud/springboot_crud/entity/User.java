package com.crud.springboot_crud.entity;


import com.crud.springboot_crud.util.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Username cannot be blank")
    private String name;
    @NotBlank(message = "Email cannot be blank")
    @Column(unique = true)
    @Email(message = "Enter a Valid Email Address")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    private String password;
    @Size(min = 10,max = 10,message = "Phone Should be 10 digits Only")
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;

    public User(LocalDateTime createdAt) {
        this.createdAt = LocalDateTime.now();
    }
}
