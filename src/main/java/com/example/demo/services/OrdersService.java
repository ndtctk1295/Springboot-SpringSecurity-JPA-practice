package com.example.demo.services;

import com.example.demo.models.Orders;
import com.example.demo.models.OrdersItem;
import com.example.demo.repositories.OrdersItemRepository;
import com.example.demo.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final OrdersItemRepository ordersItemRepository;


    @Autowired
    public OrdersService(OrdersRepository ordersRepository, OrdersItemRepository ordersItemRepository) {
        this.ordersRepository = ordersRepository;
        this.ordersItemRepository = ordersItemRepository;
    }
    public List<Orders> getAllOrders() {

        return ordersRepository.findAll();
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
    public Orders insertOrders (Orders newOrder){
//        getAllOrders();
        return ordersRepository.save(newOrder);
    }

}
