package com.hunger.saviour.portal.services;

import com.hunger.saviour.portal.dtos.OrderDTO;

public interface PaymentService {

    void processPayment(OrderDTO orderDTO);
}
