package com.example.demo.services;

import com.example.demo.models.CouponDiscount;
import com.example.demo.models.Orders;
import com.example.demo.models.OrdersItem;
import com.example.demo.repositories.CouponDiscountRepository;
import com.example.demo.repositories.OrdersItemRepository;
import com.example.demo.repositories.OrdersRepository;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdersItemService {
    private final OrdersItemRepository ordersItemRepository;
    private final OrdersRepository ordersRepository;
    private final ProductRepository productRepository;
    private final CouponDiscountService couponDiscountService;
    private final CustomerService customerService;
    private final CouponDiscountRepository couponDiscountRepository;

    @Autowired
    public OrdersItemService(OrdersItemRepository ordersItemRepository, OrdersRepository ordersRepository, ProductRepository productRepository, CouponDiscountService couponDiscountService, CustomerService customerService, CouponDiscountRepository couponDiscountRepository) {
        this.ordersItemRepository = ordersItemRepository;
        this.ordersRepository = ordersRepository;
        this.productRepository = productRepository;
        this.couponDiscountService = couponDiscountService;
        this.customerService = customerService;
        this.couponDiscountRepository = couponDiscountRepository;
    }
    public List<OrdersItem> getAllOrdersItems() {
        return ordersItemRepository.findAll();
    }
    public Double calculateTotal (OrdersItem ordersItem) {
        int quantity = ordersItem.getQuantity();
        Double price = productRepository.findById(ordersItem.getProduct().getId()).get().getPrice();
        if(ordersItem.getOrder().getCoupon() == null) {
            return quantity * price;
        }
        else {
            Double discount = couponDiscountService.findByCouponCode(ordersItem.getOrder().getCoupon().getCouponCode()).getDiscountPercent();
            return quantity * price * (1 - discount*0.01);
        }
    }
    public Double calculateSubtotal (OrdersItem ordersItem) {
        int quantity = ordersItem.getQuantity();
        Double price = productRepository.findById(ordersItem.getProduct().getId()).get().getPrice();
        return quantity * price;
    }
    public boolean isValidOrdersId (OrdersItem ordersItem) {
        Optional<Orders> order = ordersRepository.findById(ordersItem.getOrder().getOrder_id());
        return order.isPresent();
    }
    public boolean isValidCoupon (OrdersItem ordersItem) {
        CouponDiscount coupon = ordersItem.getCoupon();
        if(couponDiscountService.isCouponCodeExist(coupon)){
            return couponDiscountService.isValidCoupon(coupon);
        }else{
            return false;
        }
    }

    public List<OrdersItem> findItemsByOrderId(Integer order_id) {
        return ordersItemRepository.findItemsByOrderId(order_id);
    }
    public OrdersItem insertOrdersItem(OrdersItem newOrdersItem) {
        // Check if the order ID is valid and exists
        if (!isValidOrdersId(newOrdersItem)) {
            throw new IllegalArgumentException("Invalid order ID: " + newOrdersItem.getOrder().getOrder_id());
        }

        // If this OrdersItem has a coupon, then set the existing coupon for this OrdersItem
        if (newOrdersItem.getCoupon() != null) {
            Optional<CouponDiscount> existingCoupon = couponDiscountRepository.findByCouponCode(newOrdersItem.getCoupon().getCouponCode());
            if (existingCoupon.isPresent()) {
                // Associate the existing CouponDiscount with the OrdersItem
                newOrdersItem.setCoupon(existingCoupon.get());
            } else {
                // If the coupon does not exist, set the coupon to null
                newOrdersItem.setCoupon(null);
            }
        }

        // Set customer and re-calculate subtotal and total for this OrdersItem
        newOrdersItem.getOrder().setCustomer(customerService.findByPhoneNumber(newOrdersItem.getOrder().getCustomer().getPhoneNumber()));
        newOrdersItem.setSubtotal(calculateSubtotal(newOrdersItem));
        newOrdersItem.setTotal(calculateTotal(newOrdersItem));

        // Save the OrdersItem
        return ordersItemRepository.save(newOrdersItem);
    }
}
