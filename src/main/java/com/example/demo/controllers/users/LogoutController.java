//package com.example.demo.controllers.users;
//
//import com.example.demo.services.TokenService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/v1/public/user")
//public class LogoutController {
//
//
//    private final TokenService tokenService;
//    @Autowired
//    public LogoutController(TokenService tokenService) {
//
//        this.tokenService = tokenService;
//    }
//
//    @CrossOrigin()
//    @PutMapping("/logout")
//    public ResponseEntity<?> logout(@RequestBody Map<String, String> json) {
//        String token = json.get("token");
//        tokenService.deactivateToken(token);
//        return ResponseEntity.ok().build();
//    }
//}
