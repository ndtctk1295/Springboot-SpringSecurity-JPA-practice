package com.example.demo.services;

import com.example.demo.models.CouponDiscount;
import com.example.demo.repositories.CouponDiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponDiscountService {
    private final CouponDiscountRepository couponDiscountRepository;

    @Autowired
    public CouponDiscountService(CouponDiscountRepository couponDiscountRepository) {
        this.couponDiscountRepository = couponDiscountRepository;
    }
    public List<CouponDiscount> getAllCouponDiscounts() {
        return couponDiscountRepository.findAll();
    }
    public boolean isValidCoupon (CouponDiscount couponDiscount) {
        if(couponDiscountRepository.findByCouponCode(couponDiscount.getCouponCode()).isPresent()){
            return couponDiscountRepository.findByCouponCode(couponDiscount.getCouponCode()).get().isActive();
        }
        return false;
    }
    public CouponDiscount findByCouponCode (String couponCode) {
        return couponDiscountRepository.findByCouponCode(couponCode).get();
    }
    public boolean isCouponCodeExist (CouponDiscount couponDiscount) {
        return couponDiscountRepository.findByCouponCode(couponDiscount.getCouponCode()).isPresent();
    }


    public CouponDiscount insertCouponDiscount(CouponDiscount newCouponDiscount) {
        return couponDiscountRepository.save(newCouponDiscount);
    }
    public boolean deleteCouponDiscount(Integer coupon_id) {
        if (couponDiscountRepository.findById(coupon_id).isPresent()) {
            couponDiscountRepository.deleteById(coupon_id);
            return true;
        }
        return false;
    }
}
