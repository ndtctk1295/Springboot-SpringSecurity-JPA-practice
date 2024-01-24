package com.example.demo.controllers;

import com.example.demo.models.CouponDiscount;
import com.example.demo.models.ResponseObject;
import com.example.demo.services.CouponDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coupon")
public class CouponDiscountController {
    private final CouponDiscountService couponDiscountService;
    @Autowired
    public CouponDiscountController(CouponDiscountService couponDiscountService) {
        this.couponDiscountService = couponDiscountService;
    }

    @CrossOrigin()
    @GetMapping("/get-all")
    public ResponseEntity<ResponseObject> getAllCouponDiscounts() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "Query couponDiscount successfully", couponDiscountService.getAllCouponDiscounts()));
    }

    @CrossOrigin()
    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> insertCouponDiscount(@RequestBody CouponDiscount newCouponDiscount) {
        CouponDiscount couponDiscount = couponDiscountService.insertCouponDiscount(newCouponDiscount);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "Insert couponDiscount successfully", couponDiscount));
    }
    @CrossOrigin()
    @GetMapping("/is-valid")
    public ResponseEntity<ResponseObject> isValidCoupon(@RequestBody CouponDiscount couponDiscount) {
                System.out.println(SecurityContextHolder.getContext().getAuthentication());
        if(couponDiscountService.isCouponCodeExist(couponDiscount)){
            if(couponDiscountService.isValidCoupon(couponDiscount)){
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("success", "Coupon is valid", true));
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Coupon is expired", false));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "Coupon is not exist", false));
    }
    @CrossOrigin
    @DeleteMapping("/delete/{coupon_id}")
    public ResponseEntity<ResponseObject> deleteCoupon(@PathVariable Integer coupon_id){
        if(couponDiscountService.deleteCouponDiscount(coupon_id)){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Delete coupon successfully", true));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "Delete coupon failed", false));
    }
    @CrossOrigin
    @PostMapping("/get-by-code")
    public ResponseEntity<ResponseObject> getCouponByCode(@RequestBody CouponDiscount couponCode){
        if(couponDiscountService.isCouponCodeExist(couponCode)){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Get coupon successfully", couponDiscountService.findByCouponCode(couponCode.getCouponCode())));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "Get coupon failed", null));
    }
}
