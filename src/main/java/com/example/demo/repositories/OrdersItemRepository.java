package com.example.demo.repositories;

import com.example.demo.models.OrdersItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrdersItemRepository extends JpaRepository<OrdersItem, Integer> {
    @Query("SELECT oi FROM OrdersItem oi WHERE oi.order.order_id = :orderId")
    List<OrdersItem> findItemsByOrderId(Integer orderId);

}
