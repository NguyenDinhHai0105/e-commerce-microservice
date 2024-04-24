package com.mysystem.orderservice.controller;

import com.mysystem.orderservice.dto.OrderRequest;
import com.mysystem.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private OrderService orderServiceImpl;

    @Autowired
    public OrderController(OrderService orderServiceImpl) {
        this.orderServiceImpl = orderServiceImpl;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        orderServiceImpl.placeOrder(orderRequest);
        return "Order request successfully";
    }
}
