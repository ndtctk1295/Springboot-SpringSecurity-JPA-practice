//package com.example.demo.handler;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.HashMap;
//import java.util.Map;
//
//public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
//
//    @Override
//    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//
//        // Set the response status and content type
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        response.setContentType("application/json");
//
//        // Create a response body
//        Map<String, Object> data = new HashMap<>();
//        data.put("timestamp", DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now()));
//        data.put("message", "Authentication failed");
//
//        // Add more details based on the type of exception
//        if (exception instanceof BadCredentialsException) {
//            data.put("details", "Invalid username or password");
//        } else if (exception instanceof DisabledException) {
//            data.put("details", "Account is disabled");
//        } else {
//            data.put("details", exception.getMessage());
//        }
//
//        // Write the response body
//        OutputStream out = response.getOutputStream();
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.writeValue(out, data);
//        out.flush();
//    }
//}
//
//
