package com.example.demo.controllers.admin;

import com.example.demo.models.BlacklistToken;
import com.example.demo.models.ResponseObject;
import com.example.demo.services.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
public class TokenController {
    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/tokens")
    public ResponseEntity<ResponseObject> getTokens() {
        List<Map<String, String>> activeTokens = tokenService.getActiveTokens();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "Query tokens successfully", activeTokens)
        );
    }
    @PostMapping("/tokens/revoke")
    public ResponseEntity<ResponseObject> revokeToken(@RequestBody BlacklistToken token) {
        boolean isRevoked = tokenService.deactivateToken(token.getToken());
        if (isRevoked) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Token revoked successfully", null)
            );
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("error", "Failed to revoke token", null)
            );
        }
    }
}
