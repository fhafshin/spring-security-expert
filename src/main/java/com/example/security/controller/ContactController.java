package com.example.security.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class ContactController {


    @GetMapping("/contact")
    List<String> saveContactInquiryDetails(){
        return Collections.singletonList( "here is the contact save to db");
    }
}
