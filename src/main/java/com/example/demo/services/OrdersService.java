package com.example.demo.services;

import com.example.demo.models.CouponDiscount;
import com.example.demo.models.Orders;
import com.example.demo.models.OrdersItem;
import com.example.demo.repositories.CouponDiscountRepository;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.OrdersItemRepository;
import com.example.demo.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final OrdersItemRepository ordersItemRepository;
    private final CustomerRepository customerRepository;

    private final CouponDiscountRepository couponDiscountRepository;

    @Autowired
    public OrdersService(OrdersRepository ordersRepository, OrdersItemRepository ordersItemRepository, CustomerRepository customerRepository, CouponDiscountRepository couponDiscountRepository) {
        this.ordersRepository = ordersRepository;
        this.ordersItemRepository = ordersItemRepository;
        this.customerRepository = customerRepository;
        this.couponDiscountRepository = couponDiscountRepository;
    }
    public List<Orders> getAllOrders() {
        updateAllInfo();
        return ordersRepository.findAll();
    }
    public void updateAllInfo() {
        updateAllOrdersTotal();
        updateAllOrdersSubtotal();
        updateAllCouponId();
        updateAllCouponDiscountPercent();
    }
    public void updateByOrderById(Integer order_id) {
        updateOrderTotal(order_id);
        updateOrderSubtotal(order_id);
        updateCouponId(order_id);
        updateCouponDiscountPercent(order_id);
    }
    public void updateCouponId(Integer order_id){
        Orders order = ordersRepository.findById(order_id).get();
        if(order.getCoupon() != null){
            Optional<CouponDiscount> coupon = couponDiscountRepository.findByCouponCode(order.getCoupon().getCouponCode());
            coupon.ifPresent(order::setCoupon);
        }
    }
    public void updateAllCouponId(){
        List<Orders> orders = ordersRepository.findAll();
        for (Orders order : orders) {
            updateCouponId(order.getOrder_id());
        }
    }


    public void updateCouponDiscountPercent(Integer order_id){
        Orders order = ordersRepository.findById(order_id).get();
        if(order.getCoupon() != null){
            Optional<CouponDiscount> coupon = couponDiscountRepository.findByCouponCode(order.getCoupon().getCouponCode());
            coupon.ifPresent(couponDiscount -> order.setDiscountPercent(couponDiscount.getDiscountPercent()));
        }
    }

    public void updateAllCouponDiscountPercent(){
        List<Orders> orders = ordersRepository.findAll();
        for (Orders order : orders) {
            updateCouponDiscountPercent(order.getOrder_id());
        }
    }
    // Method to update totals for all orders
    public void updateAllOrdersTotal() {
        List<Orders> allOrders = ordersRepository.findAll();
        for (Orders order : allOrders) {
            updateOrderTotal(order.getOrder_id());
        }
    }

    // Method to update total for a single order
    private void updateOrderTotal(Integer order_id) {
        List<OrdersItem> items = ordersItemRepository.findItemsByOrderId(order_id);
        if (items.isEmpty()) {
            throw new RuntimeException("Order has no items");
        }
        Double total = items.stream()
                .map(OrdersItem::getTotal)         // Get total for each item
                .filter(Objects::nonNull)          // Filter out null values
                .mapToDouble(Double::doubleValue)  // Convert to double for summing
                .sum();

        Orders order = ordersRepository.findById(order_id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setTotal(total);
        ordersRepository.save(order);
    }
    public void updateAllOrdersSubtotal() {
        List<Orders> allOrders = ordersRepository.findAll();
        for (Orders order : allOrders) {
            updateOrderSubtotal(order.getOrder_id());
        }
    }

    // Method to update total for a single order
    private void updateOrderSubtotal(Integer order_id) {
        List<OrdersItem> items = ordersItemRepository.findItemsByOrderId(order_id);
        if (items.isEmpty()) {
            throw new RuntimeException("Order has no items");
        }
        Double subtotal = items.stream()
                .map(OrdersItem::getSubtotal)       // Get subtotal for each item
                .filter(Objects::nonNull)           // Filter out null values
                .mapToDouble(Double::doubleValue)   // Convert to double for summing
                .sum();

        Orders order = ordersRepository.findById(order_id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setSubtotal(subtotal);
        ordersRepository.save(order);
    }
    public boolean isValidCoupon(Orders orders) {
        CouponDiscount coupon = orders.getCoupon();
        if(couponDiscountRepository.existsByCouponCode(coupon.getCouponCode())){
            return couponDiscountRepository.findByCouponCode(coupon.getCouponCode()).get().isActive();
        }else{
            return false;
        }
    }
    public Orders insertOrders (Orders newOrder){
        // If this Orders has coupon, then set coupon for this Orders
        if(newOrder.getCoupon() != null){
            Optional<CouponDiscount> coupon = couponDiscountRepository.findByCouponCode(newOrder.getCoupon().getCouponCode());
            if(coupon.isPresent()){
                // Scenario 1: The CouponDiscount already exists in the database
                newOrder.setCoupon(coupon.get());
                newOrder.setDiscountPercent(coupon.get().getDiscountPercent());
            } else {
                // Scenario 2: The CouponDiscount does not exist in the database
                // Handle this scenario as appropriate for your application
                // For example, you might want to throw an exception or set the CouponDiscount to null
                newOrder.setCoupon(null);
                newOrder.setDiscountPercent(null);
            }
        } else {
            // Scenario 3: The CouponDiscount is null
            // No action needed, as the CouponDiscount is already null
        }
        newOrder.setDate(Date.valueOf(LocalDate.now()));
        newOrder.setCustomer(customerRepository.findByPhoneNumber(newOrder.getCustomer().getPhoneNumber()));
        return ordersRepository.save(newOrder);
    }
    public Orders getOrderById(Integer order_id) {
        updateByOrderById(order_id);
        return ordersRepository.findById(order_id).get();
    }

}
