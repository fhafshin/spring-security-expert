package com.example.security.controller;

import com.example.security.config.CustomUserDetailService;
import com.example.security.constant.ApplicationConstant;
import com.example.security.dto.RequestLoginDto;
import com.example.security.dto.ResponseLoginDto;
import com.example.security.entity.Customer;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springdoc.core.service.GenericResponseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    @Value("${spring.security.pepper}")
    private String test;
    private final CustomUserDetailService customUserDetailService;
    private final GenericResponseService responseBuilder;
    private final AuthenticationManager authenticationManager;
    private final Environment env;

    public UserController(CustomUserDetailService customUserDetailService, GenericResponseService responseBuilder, AuthenticationManager authenticationManager, Environment env) {
        this.customUserDetailService = customUserDetailService;
        this.responseBuilder = responseBuilder;
        this.authenticationManager = authenticationManager;
        this.env = env;
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

    @PostMapping("/apiLogin")
    public ResponseEntity<ResponseLoginDto> apiLogin(@RequestBody RequestLoginDto requestLogin) {


        String jwt = null;

        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(
                requestLogin.username(), requestLogin.password()
        );

        Authentication authenticationResult = authenticationManager.authenticate(authentication);
        if (null != authenticationResult && authenticationResult.isAuthenticated()) {

            if (env != null) {
                String secret = env.getProperty(ApplicationConstant.JWT_SECRET_KEY,
                        ApplicationConstant.JWT_DEFAULT_SECRET_KEY);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                jwt = Jwts.builder().issuer("bank")
                        .subject("JWT Token")
                        .claim("username", authentication.getName())
                        .claim("Authorities", authentication.getAuthorities().stream().
                                map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                        .issuedAt(new Date())
                        .expiration(new Date(new Date().getTime() + 1000 * 60 * 60))
                        .signWith(secretKey)
                        .compact();


            }

        }

        return ResponseEntity.status(HttpStatus.OK).header(ApplicationConstant.JWT_HEADER, jwt)
                .body(new ResponseLoginDto(ApplicationConstant.JWT_HEADER, jwt));
    }
}
