package com.hunger.saviour.portal.services;

import com.hunger.saviour.portal.entities.OrderEntity;

public interface OrderService {
    void processOrder(OrderEntity orderEntity);
}
