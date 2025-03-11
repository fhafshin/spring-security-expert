package com.example.security.config;

import org.springframework.core.env.Environment;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomEncodePassword implements PasswordEncoder {



    private static String PEPPER ;

    public CustomEncodePassword( ) {

       // this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        this.passwordEncoder =   new Argon2PasswordEncoder(16, 32,
                1, 60000, 12);
        PEPPER=ConfigLoader.getPepperPassword();
    }

    private final PasswordEncoder passwordEncoder;


    @Override
    public String encode(CharSequence rawPassword) {
        System.out.println(PEPPER);
        return passwordEncoder.encode(rawPassword.toString() + PEPPER);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword + PEPPER, encodedPassword);
    }
}
