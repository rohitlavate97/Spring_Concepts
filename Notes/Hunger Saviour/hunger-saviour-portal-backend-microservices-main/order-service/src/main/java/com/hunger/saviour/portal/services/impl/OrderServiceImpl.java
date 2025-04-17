package com.hunger.saviour.portal.services.impl;

import com.hunger.saviour.portal.entities.OrderEntity;
import com.hunger.saviour.portal.repositories.OrderRepository;
import com.hunger.saviour.portal.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public void processOrder(OrderEntity orderEntity) {
        this.orderRepository.save(orderEntity);
    }
}
