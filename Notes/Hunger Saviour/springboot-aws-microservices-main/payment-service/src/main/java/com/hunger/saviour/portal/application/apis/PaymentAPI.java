package com.hunger.saviour.portal.application.apis;

import com.hunger.saviour.portal.application.dtos.PaymentDTO;
import com.hunger.saviour.portal.application.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payments")
public class PaymentAPI {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentDTO> triggerPayment(@RequestBody PaymentDTO paymentDTO){
        return new ResponseEntity<PaymentDTO>(this.paymentService.triggerPayment(paymentDTO), HttpStatus.CREATED);
    }

}
