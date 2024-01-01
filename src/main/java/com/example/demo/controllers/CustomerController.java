package com.example.demo.controllers;

import com.example.demo.models.Customer;
import com.example.demo.models.ResponseObject;
import com.example.demo.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/customers")
public class CustomerController {

    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;

    @Autowired

    public CustomerController(PasswordEncoder passwordEncoder, CustomerService customerService) {
        this.passwordEncoder = passwordEncoder;
        this.customerService = customerService;
    }

//    GET all customers
    @CrossOrigin
    @GetMapping("/get-all")
    public ResponseEntity<ResponseObject> getAllCustomers() {
        List<Customer> customerList = customerService.getAllCustomers();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "Query customers successfully", customerList)
        );
    }
//     GET customer by id
    @CrossOrigin
    @GetMapping("/byId/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable Integer id) {
        Optional<Customer> foundCustomer = customerService.findById(id);
        if (foundCustomer.isPresent()) {
            Customer customer = foundCustomer.get();
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Query product successfully", customer)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find product with id: " + id, null)
            );
        }
    }
//  GET customer by name
    @CrossOrigin()
    @GetMapping("/byName/{customerName}")
    public ResponseEntity<ResponseObject> findByProductName(@PathVariable String customerName){
        List<Customer> foundCustomers = customerService.findByCustomerName(customerName);
        if (!foundCustomers.isEmpty()) {
//            Product product = foundProduct.get();
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Query product successfully", foundCustomers)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find product with name: " + customerName, null)
            );
        }
    }

//    GET customer by phone number
    @CrossOrigin()
    @GetMapping("/byPhoneNumber/{phoneNumber}")
    public ResponseEntity<ResponseObject> findByPhoneNumber(@PathVariable String phoneNumber){
        Customer foundCustomer = customerService.findByPhoneNumber(phoneNumber);
        if (foundCustomer != null) {
//            Product product = foundProduct.get();
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Query product successfully", foundCustomer)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find product with name: " + phoneNumber, null)
            );
        }
    }

//    POST new customer to DB
@CrossOrigin
@PostMapping("/insert")
public ResponseEntity<ResponseObject> insertCustomer(@RequestBody Customer newCustomer) {
    Customer foundCustomers = customerService.findByPhoneNumber(newCustomer.getPhoneNumber());

    if (foundCustomers != null) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObject("failed", "Phone Number already exists", null)
        );
    }

    Customer insertedCustomer = customerService.insertCustomer(newCustomer);

    return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject("success", "Sign up successfully", insertedCustomer)
    );
}

    //    upsert (update or insert if not found)
    @CrossOrigin
    @PutMapping("/{customer_id}")
    public ResponseEntity<ResponseObject> updateCustomer(@RequestBody Customer newCustomer, @PathVariable Integer customer_id) {
        Customer updatedCustomer = customerService.updateOrInsertCustomer(newCustomer, customer_id);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "Updated or Inserted Customer successfully", updatedCustomer)
        );
    }
    @CrossOrigin
    @DeleteMapping("/{customer_id}")
    public ResponseEntity<ResponseObject> deleteCustomer(@PathVariable Integer customer_id) {
        boolean deleted = customerService.deleteCustomer(customer_id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Delete customer successfully", null)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find customer to delete", null)
            );
        }
    }
    @CrossOrigin
    @PostMapping("/find-by-username-and-password")
    public ResponseEntity<ResponseObject> findByUsernameAndPassword(@RequestBody Customer customer) {
        Customer foundCustomer = customerService.findByUsernameAndPassword(customer.getUsername(), customer.getPassword());
        if (foundCustomer != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Query customer successfully", foundCustomer)
            );
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find customer with username: " + customer.getUsername(), null)
        );
    }
    @CrossOrigin
    @PostMapping("/find-by-username")
    public ResponseEntity<ResponseObject> findByUsername(@RequestBody Customer customer) {
        Customer foundCustomer = customerService.findByUsername(customer.getUsername());
        if (foundCustomer != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Query customer successfully", foundCustomer)
            );
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find customer with username: " + customer.getUsername(), null)
        );
    }
}
