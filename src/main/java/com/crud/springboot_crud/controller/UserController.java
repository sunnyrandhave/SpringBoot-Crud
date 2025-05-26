package com.crud.springboot_crud.controller;

import com.crud.springboot_crud.DTO.UserDTO;
import com.crud.springboot_crud.entity.User;
import com.crud.springboot_crud.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody User user) throws Exception {
        Map<String, Object> map = new HashMap<>();
        try {
            UserDTO registeredUser = userService.register(user);
            map.put("message", "User Registered Successfully!");
            map.put("user", registeredUser);
            return new ResponseEntity<>(map, HttpStatus.CREATED);
        } catch (Exception e) {
            map.put("error", "Failed to fetch users: " + e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ResponseBody
    @PostMapping("/login-user")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) throws Exception {
        Map<String, Object> map = new HashMap<>();
        try {
            UserDTO loggedInUser = userService.login(user);
            map.put("message", "User Login Successful!");
            map.put("user", loggedInUser);
            return ResponseEntity.ok(map);
        }catch (Exception e) {
            map.put("error", "Failed to fetch users: " + e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllUsers")
    @ResponseBody
    public ResponseEntity<Map<Object, Object>> getAllUsers() throws Exception {
        Map<Object, Object> map = new HashMap<>();
        try {
            map.put("message", "All Users Fetched!");
            map.put("users", userService.getUsers());
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            System.err.println("Error fetching all users: " + e.getMessage());
            map.put("error", "Failed to fetch users: " + e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable int id) {
        Map<String, String> map = new HashMap<>();
        try {
            map.put("message", userService.deleteUser(id));
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            System.err.println("Error deleting user with ID " + id + ": " + e.getMessage());
            map.put("error", "Failed to delete user: " + e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<Map<Object, Object>> updateUser(@PathVariable int id, @RequestBody User user) {
        Map<Object, Object> map = new HashMap<>();
        try {
            UserDTO updatedUser = userService.updateUser(id, user);
            map.put("message", "User Details Updated Successfully!");
            map.put("user", updatedUser);
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            System.err.println("Error updating user with ID " + id + ": " + e.getMessage());
            map.put("error", "Failed to update user: " + e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
    }
}