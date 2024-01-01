package com.example.demo.controllers.admin;

import com.example.demo.models.Customer;
import com.example.demo.models.ResponseObject;
import com.example.demo.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminSignUpController {
    @Autowired
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;

    public AdminSignUpController(PasswordEncoder passwordEncoder, CustomerService customerService) {
        this.passwordEncoder = passwordEncoder;
        this.customerService = customerService;
    }
    @CrossOrigin()
    @PostMapping("/register")
    public ResponseEntity<ResponseObject> registerAdmin(@RequestBody Customer newCustomer){

        if(customerService.findByUsername(newCustomer.getCustomerName()) != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("failed", "Username already exist, please try to log in", null));
        } else if (customerService.findByPhoneNumber(newCustomer.getPhoneNumber()) != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("failed", "Phone number already exist, please try a different phone number", null));
        }
//        newCustomer.setPassword(passwordEncoder.encode(newCustomer.getPassword()));
        newCustomer.setPassword(newCustomer.getPassword());
        newCustomer.setRole("ROLE_ADMIN");
        Customer customer = customerService.insertCustomer(newCustomer);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "Register success", customer));
    }
}
