package com.example.demo.controllers.users;

import com.example.demo.dto.TokenRefreshRequest;
import com.example.demo.models.ResponseObject;
import com.example.demo.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public/user")
public class RefreshTokenController {
    private final JwtService jwtService;

    @Autowired
    public RefreshTokenController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseObject> refresh(@RequestBody TokenRefreshRequest request) {
        String requestToken = request.getRefreshToken();
        // Directly attempt to refresh the token without checking its validity
        String newRefreshToken = jwtService.refreshExpiredToken(requestToken);
        if (newRefreshToken != null) {
            // If a new refresh token is successfully created, return it
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Refresh token successfully", newRefreshToken)
            );
        } else {
            // If the token is invalid (null), return an error response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("failed", "Refresh token is invalid", null)
            );
        }
    }
}
