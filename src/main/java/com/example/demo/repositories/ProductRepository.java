package com.example.demo.repositories;

import com.example.demo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  ProductRepository extends JpaRepository <Product, Integer> {
//    method find product with its name
    List<Product> findByProductNameContaining (String productName);
    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    List<Product> findAllByOrderByPriceAsc();
    List<Product> findAllByOrderByPriceDesc();
}
