package com.example.demo.repositories;

import com.example.demo.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findByCustomerNameContaining (String customerName);
    Customer findByPhoneNumberContaining (String phoneNumber);
    Customer findByUsername (String username);
    Customer findByUsernameAndPassword (String username, String password);
}
