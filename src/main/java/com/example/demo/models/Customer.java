package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment in database
    @Column(name = "customer_id")
    private Integer customer_id;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;
    @Column(name = "username", length = 255, nullable = false, unique = true)
    private String username;
    @Column(name = "password" , length = 255, nullable = false)
    private String password;
    @Column(name = "role", length = 255, nullable = false)
    private String role;
    public Customer(){
    }

    public Customer(Integer customer_id, String customerName, String phoneNumber) {
        this.customer_id = customer_id;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;

    }

    public Customer(Integer customer_id, String customerName, String phoneNumber, String username, String password, String role) {
        this.customer_id = customer_id;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customer_id=" + customer_id +
                ", customerName='" + customerName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }


}

