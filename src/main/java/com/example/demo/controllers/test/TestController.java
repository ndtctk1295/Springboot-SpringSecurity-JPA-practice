package com.example.demo.controllers.test;

import com.example.demo.models.ResponseObject;
import com.example.demo.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    private final JwtService jwtService;
    @Autowired
    public TestController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @CrossOrigin()
    @GetMapping("/get-phone-number-from-jwt")
    public ResponseEntity<ResponseObject> getPhoneNumberFromJWT(@RequestHeader("Authorization") String token){
        // Extract the JWT from the Authorization header
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        // Retrieve the phone number from the JWT
        String phoneNumber = jwtService.getPhoneNumberFromToken(token);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "Get phone number from JWT successfully", phoneNumber)
        );

    }
}
