package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((request)->request
                 .requestMatchers(
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html",
                         "/notices",
                         "/contact"
                ).permitAll()
                .anyRequest().authenticated())
                .csrf(csrf->csrf.ignoringRequestMatchers("v3/api-docs/**","/swagger-ui/**"));

        http.httpBasic(withDefaults());
        http.formLogin(withDefaults());
        return http.build();
    }
}
