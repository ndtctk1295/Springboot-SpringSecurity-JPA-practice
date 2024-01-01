package com.example.demo.services;

import com.example.demo.models.CouponDiscount;
import com.example.demo.models.Orders;
import com.example.demo.models.OrdersItem;
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

    @Autowired
    public OrdersItemService(OrdersItemRepository ordersItemRepository, OrdersRepository ordersRepository, ProductRepository productRepository, CouponDiscountService couponDiscountService, CustomerService customerService) {
        this.ordersItemRepository = ordersItemRepository;
        this.ordersRepository = ordersRepository;
        this.productRepository = productRepository;
        this.couponDiscountService = couponDiscountService;
        this.customerService = customerService;
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
            return quantity * price * (1 - discount);
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
        CouponDiscount coupon = ordersItem.getOrder().getCoupon();
        if(couponDiscountService.isCouponCodeExist(coupon)){
            return couponDiscountService.isValidCoupon(coupon);
        }
        return false;
    }
//    public boolean isExistCustomer (OrdersItem ordersItem) {
////        String customerName = ordersItem.getOrder().getCustomer().getCustomerName();
//        String phoneNumber = ordersItem.getOrder().getCustomer().getPhoneNumber();
////        return ordersRepository.findByCustomerNameAndPhoneNumber(customerName, phoneNumber).isPresent();
//        return customerService.findByPhoneNumber(phoneNumber) != null;
//    }
    public OrdersItem insertOrdersItem(OrdersItem newOrdersItem) {
        if (!isValidOrdersId(newOrdersItem)) {
            ordersRepository.save(newOrdersItem.getOrder());
        }
        if (isValidCoupon(newOrdersItem)) {
            newOrdersItem.getOrder().setCoupon(couponDiscountService.findByCouponCode(newOrdersItem.getOrder().getCoupon().getCouponCode()));
        }

        newOrdersItem.getOrder().setCustomer(customerService.findByPhoneNumber(newOrdersItem.getOrder().getCustomer().getPhoneNumber()));
        newOrdersItem.setSubtotal(calculateSubtotal(newOrdersItem));
        newOrdersItem.setTotal(calculateTotal(newOrdersItem));

//        return ordersItemRepository.save(newOrdersItem);
        return newOrdersItem;
    }
    public List<OrdersItem> findItemsByOrderId(Integer order_id) {
        return ordersItemRepository.findItemsByOrderId(order_id);
    }
}
