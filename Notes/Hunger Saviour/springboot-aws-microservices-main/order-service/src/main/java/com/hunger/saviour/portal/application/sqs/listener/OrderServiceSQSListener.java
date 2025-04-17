package com.hunger.saviour.portal.application.sqs.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunger.saviour.portal.application.dtos.OrderDTO;
import com.hunger.saviour.portal.application.services.OrderService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderServiceSQSListener {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @SqsListener(value = "orders")
    public void pullDataFromOrdersQueue(String orderData) throws Exception {
        log.info("Message {} from Orders queue:", orderData);
        OrderDTO orderDTO = objectMapper.readValue(orderData, OrderDTO.class);
        this.orderService.createOrder(orderDTO);
    }

}
