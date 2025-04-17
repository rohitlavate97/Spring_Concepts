package com.hunger.saviour.portal.application.repositories;

import com.hunger.saviour.portal.application.entities.PaymentEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PaymentRepository extends CrudRepository<PaymentEntity, UUID> {
}
