package com.example.security.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigLoader {
    @Value("${spring.security.pepper}")
    private String PEPPER;

    private static String PEPPER_PASSWORD;
    @PostConstruct
    public void init() {
        PEPPER_PASSWORD=PEPPER;
    }

    public static String getPepperPassword() {
        return PEPPER_PASSWORD;
    }
}
