package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "coupon_discount")
public class CouponDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Integer coupon_id;

    @Column(name = "coupon_code")
    private String couponCode;

    @Column(name = "discount_percent")
    private Double discountPercent;

    @Column(name = "active")
    private boolean active;


    public CouponDiscount(Integer coupon_id, String couponCode, Double discountPercent, boolean active) {
        this.coupon_id = coupon_id;
        this.couponCode = couponCode;
        this.discountPercent = discountPercent;
        this.active = active;
    }

    public CouponDiscount() {
    }

    public Integer getId() {
        return coupon_id;
    }

    public void setId(Integer id) {
        this.coupon_id = id;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "CouponDiscount{" +
                "id=" + coupon_id +
                ", couponCode='" + couponCode + '\'' +
                ", discountPercent=" + discountPercent +
                ", active=" + active +
                '}';
    }
}
