package com.example.demo.controllers.users;

import com.example.demo.models.Customer;
import com.example.demo.models.ResponseObject;
import com.example.demo.services.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
@RequestMapping("/api/v1/public/user")
public class LoginController {
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginController(PasswordEncoder passwordEncoder, CustomerService customerService, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.customerService = customerService;
        this.authenticationManager = authenticationManager;
    }


    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<ResponseObject> loginCustomer(@RequestBody Customer customer, HttpServletRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(customer.getUsername(), customer.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //        set a new session for each login
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "Login success", customerService.findByUsername(customer.getUsername()))
        );
    }

}
