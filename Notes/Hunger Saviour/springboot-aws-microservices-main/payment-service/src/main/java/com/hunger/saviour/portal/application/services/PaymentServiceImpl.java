package com.hunger.saviour.portal.application.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunger.saviour.portal.application.dtos.PaymentDTO;
import com.hunger.saviour.portal.application.entities.PaymentEntity;
import com.hunger.saviour.portal.application.repositories.PaymentRepository;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private SqsTemplate sqsTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public PaymentDTO triggerPayment(PaymentDTO paymentDTO){
        PaymentEntity paymentEntity = PaymentEntity.builder()
                .username(paymentDTO.getUsername())
                .totalPrice(paymentDTO.getTotalPrice())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
        this.paymentRepository.save(paymentEntity);
        PaymentDTO response = PaymentDTO.builder()
                .paymentId(paymentEntity.getPaymentId())
                .username(paymentEntity.getUsername())
                .totalPrice(paymentEntity.getTotalPrice())
                .menuItems(paymentDTO.getMenuItems())
                .build();
        // send data to SQS topic
        sqsTemplate.send(to -> {
            try {
                to.queue("orders")
                        .payload(objectMapper.writeValueAsString(response));
            } catch (JsonProcessingException e) {
                log.error("Error while sending data to order queue");
            }
        });
        return response;
    }
}
