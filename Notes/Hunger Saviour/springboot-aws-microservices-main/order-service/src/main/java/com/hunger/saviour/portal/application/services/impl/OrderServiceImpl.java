package com.hunger.saviour.portal.application.services.impl;

import com.hunger.saviour.portal.application.collections.Orders;
import com.hunger.saviour.portal.application.dtos.OrderDTO;
import com.hunger.saviour.portal.application.services.OrderService;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private DynamoDbTemplate dynamoDbTemplate;

    @Override
    public void createOrder(OrderDTO orderDTO) {
        Orders orderCollection = Orders.builder()
                .paymentId(orderDTO.getPaymentId())
                .totalPrice(orderDTO.getTotalPrice())
                .menuItems(orderDTO.getMenuItems())
                .username(orderDTO.getUsername())
                .paymentDateAndTime(Instant.now())
                .build();
        this.dynamoDbTemplate.save(orderCollection);
    }
}