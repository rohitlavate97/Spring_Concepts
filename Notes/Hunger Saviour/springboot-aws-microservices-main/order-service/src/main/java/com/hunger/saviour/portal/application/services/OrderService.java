package com.hunger.saviour.portal.application.services;

import com.hunger.saviour.portal.application.dtos.OrderDTO;

public interface OrderService {
    void createOrder(OrderDTO orderDTO);
}
