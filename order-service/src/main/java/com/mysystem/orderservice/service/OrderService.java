package com.mysystem.orderservice.service;

import com.mysystem.orderservice.dto.OrderRequest;

public interface OrderService {
    public String placeOrder(OrderRequest orderRequest);
}
