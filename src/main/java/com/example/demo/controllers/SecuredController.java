package com.example.demo.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/secured")
public class SecuredController {
    @CrossOrigin
    @GetMapping("/secured-endpoint")
    public String securedEndpoint(HttpServletRequest request) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                System.out.println("Granted Authority: " + authority.getAuthority());
            }
        }else {
            System.out.println("Authentication is null");
        }
        return "This is a secured endpoint";
    }
}
