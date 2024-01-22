package com.example.demo.controllers.admin;

import com.example.demo.models.Customer;
import com.example.demo.models.ResponseObject;
import com.example.demo.services.CustomerService;
import com.example.demo.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminLoginControllerAPI {

    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService; // JWT Service

    @Autowired
    public AdminLoginControllerAPI(PasswordEncoder passwordEncoder, CustomerService customerService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.customerService = customerService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService; // Initialize JWT Service
    }

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<ResponseObject> loginCustomer(@RequestBody  Customer customer, HttpServletRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(customer.getUsername(), customer.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate JWT
            String jwt = jwtService.generateAccessToken(authentication.getName());

            // Return JWT in the response
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Login success", jwt)
            );
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("failed", "Login failed", null)
            );
        }

    }

}
