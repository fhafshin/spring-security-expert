package com.example.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomEncodePassword implements PasswordEncoder {
    @Value("${spring.security.pepper}")
    private static String PEPPER;


    private final PasswordEncoder passwordEncoder;

    public CustomEncodePassword() {
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword.toString()+PEPPER);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword+PEPPER, encodedPassword);
    }
}
