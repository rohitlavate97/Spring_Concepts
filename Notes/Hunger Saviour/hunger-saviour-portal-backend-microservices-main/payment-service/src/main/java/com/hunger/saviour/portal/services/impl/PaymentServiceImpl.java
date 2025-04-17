package com.hunger.saviour.portal.services.impl;

import com.hunger.saviour.portal.dtos.OrderDTO;
import com.hunger.saviour.portal.entities.PaymentEntity;
import com.hunger.saviour.portal.entities.PaymentStatus;
import com.hunger.saviour.portal.kafka.PaymentServiceKafkaPublisher;
import com.hunger.saviour.portal.repositories.PaymentRepository;
import com.hunger.saviour.portal.services.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentServiceKafkaPublisher paymentServiceKafkaPublisher;

    @Override
    public void processPayment(OrderDTO orderDTO) {
        PaymentEntity paymentEntity = PaymentEntity.builder()
                .paymentStatus(PaymentStatus.PAYMENT_SUCCESS)
                .transactionId(orderDTO.getTransactionId())
                .username(orderDTO.getUsername())
                .txnDateAndTime(LocalDateTime.now())
                .build();
        this.paymentRepository.save(paymentEntity);
        this.paymentServiceKafkaPublisher.publishOrderDetailsToOrdersTopic(orderDTO);
    }
}
