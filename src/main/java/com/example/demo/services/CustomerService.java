package com.example.demo.services;

import com.example.demo.models.Customer;
import com.example.demo.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public CustomerService(CustomerRepository repository, PasswordEncoder passwordEncoder){
        this.customerRepository = repository;
        this.passwordEncoder = passwordEncoder;
    }



    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }
    public Optional<Customer> findById(Integer customer_id){
        return customerRepository.findById(customer_id);
    }
    public List<Customer> findByCustomerName(String customerName) {
        return customerRepository.findByCustomerNameContaining(customerName.trim());
    }
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username.trim());
    }

    public Customer findByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumberContaining(phoneNumber.trim());
    }
    public Customer findByUsernameAndPassword(String username, String password) {
        return customerRepository.findByUsernameAndPassword(username.trim(), password.trim());
    }
    public Customer insertCustomer (Customer newCustomer){
            newCustomer.setPassword(passwordEncoder.encode(newCustomer.getPassword()));
            if(newCustomer.getRole() == null){
                newCustomer.setRole("ROLE_USER");
            }
            return customerRepository.save(newCustomer);
    }
    public Customer updateOrInsertCustomer(Customer newCustomer, Integer customer_id) {
        Optional<Customer> existingCustomer = customerRepository.findById(customer_id);
        return existingCustomer.map(customer -> {
            customer.setCustomerName(newCustomer.getCustomerName());
            customer.setPhoneNumber(newCustomer.getPhoneNumber());
            customer.setUsername(newCustomer.getUsername());
            customer.setPassword(passwordEncoder.encode(newCustomer.getPassword()));
            return customerRepository.save(customer);
        }).orElseGet(() -> {
            newCustomer.setCustomer_id(customer_id);
            return customerRepository.save(newCustomer);
        });
    }
    public boolean deleteCustomer(Integer customer_id) {
        if(!customerRepository.existsById(customer_id)){
            return false;
        }
        customerRepository.deleteById(customer_id);
        return true;
    }
}
