package com.mysystem.orderservice.service;

import com.mysystem.orderservice.dto.OrderRequest;

public interface OrderService {
    public void placeOrder(OrderRequest orderRequest);
}
