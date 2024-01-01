package com.example.demo.controllers;

import com.example.demo.models.OrdersItem;
import com.example.demo.models.ResponseObject;
import com.example.demo.services.OrdersItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ordersItem")
public class OrdersItemController {
    private final OrdersItemService ordersItemService;

    @Autowired
    public OrdersItemController(OrdersItemService ordersItemService) {
        this.ordersItemService = ordersItemService;
    }


    @CrossOrigin()
    @GetMapping("/get-all")
    public ResponseEntity<ResponseObject> getAllOrdersItems() {
        List<OrdersItem> ordersItemList = ordersItemService.getAllOrdersItems();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "Query ordersItem successfully", ordersItemList)
        );
    }

    @CrossOrigin()
    @PostMapping("/insert")
    public  ResponseEntity<ResponseObject> insertOrdersItem(@RequestBody OrdersItem newOrdersItem){
        OrdersItem ordersItem = ordersItemService.insertOrdersItem(newOrdersItem);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "Insert ordersItem successfully", ordersItem)
        );
    }
    @CrossOrigin()
    @GetMapping("/by-order-id/{order_id}")
    public ResponseEntity<ResponseObject> findByOrderId(@PathVariable Integer order_id) {
        List<OrdersItem> foundOrdersItem = ordersItemService.findItemsByOrderId(order_id);
        if (!foundOrdersItem.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Query ordersItem successfully", foundOrdersItem)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find ordersItem with order_id: " + order_id, null)
            );
        }
    }
}
