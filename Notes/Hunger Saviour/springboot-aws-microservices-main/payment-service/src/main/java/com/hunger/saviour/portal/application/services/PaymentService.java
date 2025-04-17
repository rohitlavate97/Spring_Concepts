package com.hunger.saviour.portal.application.services;

import com.hunger.saviour.portal.application.dtos.PaymentDTO;

public interface PaymentService {
    PaymentDTO triggerPayment(PaymentDTO paymentDTO);
}
