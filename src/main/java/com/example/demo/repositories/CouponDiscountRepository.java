package com.example.demo.repositories;

import com.example.demo.models.CouponDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponDiscountRepository extends JpaRepository<CouponDiscount, Integer>{
    Optional<CouponDiscount> findByCouponCode(String couponCode);

    boolean existsByCouponCode(String couponCode);
}
