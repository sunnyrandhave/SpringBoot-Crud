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

@Slf4j
@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        log.info("Login-Form Served");
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
            log.info("user {} registered successfully",user.getName());
            return new ResponseEntity<>(map, HttpStatus.CREATED);
        } catch (Exception e) {
            map.put("error", "Failed to fetch users: " + e.getMessage());
            log.error("error : {}", e.getMessage());
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
            log.info("User {} Logged In!",loggedInUser.getName());
            return ResponseEntity.ok(map);
        }catch (Exception e) {
            map.put("error", "Failed to fetch users: " + e.getMessage());
            log.error("Error : {}",e.getMessage());
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
            log.info("All Users Fetched!");
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            map.put("error", "Failed to fetch users: " + e.getMessage());
            log.error("Error : {}",e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable int id) {
        Map<String, String> map = new HashMap<>();
        try {
            map.put("message", userService.deleteUser(id));
            log.info("User with Id : {} Deleted!",id);
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            log.error("Error : {}",e.getMessage());
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
            log.info("User {}'s details updated Successfully!",updatedUser.getName());
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            map.put("error", "Failed to update user: " + e.getMessage());
            log.error("Error : {}",e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
    }
}