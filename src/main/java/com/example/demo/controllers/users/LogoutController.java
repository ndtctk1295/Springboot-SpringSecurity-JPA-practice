//package com.example.demo.controllers.users;
//
//import com.example.demo.models.ResponseObject;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/v1/public/user")
//public class LogoutController {
//    @CrossOrigin()
//    @PostMapping("/logout")
//    public ResponseEntity<ResponseObject> logout(HttpServletRequest request) {
//        // Invalidate the session to clear the context
//        request.getSession().invalidate();
//        SecurityContextHolder.clearContext(); // Clear the security context
//
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject("success", "Logout success", null));
//    }
//}
