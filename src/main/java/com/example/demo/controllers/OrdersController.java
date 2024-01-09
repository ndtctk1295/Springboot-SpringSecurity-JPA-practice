package com.example.demo.controllers;

import com.example.demo.models.Orders;
import com.example.demo.models.ResponseObject;
import com.example.demo.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {
    private final OrdersService ordersService;

    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @CrossOrigin()
    @GetMapping("/get-all")
    public ResponseEntity<ResponseObject> getAllOrders() {
//        ordersService.updateAllOrdersTotal();
//        ordersService.updateAllOrdersSubtotal();
        List<Orders> ordersList = ordersService.getAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "Query orders successfully", ordersList)
        );
    }

    @CrossOrigin()
    @PostMapping("/insert")
    public  ResponseEntity<ResponseObject> insertOrders(@RequestBody Orders newOrders){
        Orders orders = ordersService.insertOrders(newOrders);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "Insert orders successfully", orders)
        );
    }
    @CrossOrigin()
    @GetMapping("/by-id/{id}")
    public ResponseEntity<ResponseObject> getOrderById(@PathVariable Integer id) {
        Orders foundOrders = ordersService.getOrderById(id);
        if (foundOrders != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Query orders successfully", foundOrders)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find orders with id: " + id, null)
            );
        }
    }
}
