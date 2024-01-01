package com.example.demo.repositories;

import com.example.demo.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Integer>{
}
