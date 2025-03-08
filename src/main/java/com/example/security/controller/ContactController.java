package com.example.security.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {


    @GetMapping("/contact")
     String  saveContactInquiryDetails(){
        return "here is the contact save to db";
    }
}
