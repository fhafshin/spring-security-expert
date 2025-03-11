package com.example.security.controller;

import com.example.security.entity.Customer;
import com.example.security.config.CustomUserDetailService;
import org.springdoc.core.service.GenericResponseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Value("${spring.security.pepper}")
private String test;
    private final CustomUserDetailService customUserDetailService;
    private final GenericResponseService responseBuilder;

    public UserController(CustomUserDetailService customUserDetailService, GenericResponseService responseBuilder) {
        this.customUserDetailService = customUserDetailService;
        this.responseBuilder = responseBuilder;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody Customer user) {
        customUserDetailService.createUser(user);
        return ResponseEntity.ok("create user successfully");
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok(test);
    }
}
