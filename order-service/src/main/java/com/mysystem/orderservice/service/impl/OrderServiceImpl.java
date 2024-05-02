package com.mysystem.orderservice.service.impl;

import com.mysystem.orderservice.dto.InventoryResponse;
import com.mysystem.orderservice.dto.OrderLineItemsDto;
import com.mysystem.orderservice.dto.OrderRequest;
import com.mysystem.orderservice.model.Order;
import com.mysystem.orderservice.model.OrderLineItems;
import com.mysystem.orderservice.repository.OrderRepository;
import com.mysystem.orderservice.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private WebClient webClient;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, WebClient webClient) {
        this.orderRepository = orderRepository;
        this.webClient = webClient;
    }

    @Override
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();
        // call inventory service and place order if product is in stock
//        webClient.get().uri("http://localhost:/api/inventory").exchangeToMono()
        InventoryResponse[] inventoryResponseArray = webClient.get()
                .uri("http://localhost:8083/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductIsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);

        if (allProductIsInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in stock, try again");
        }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
