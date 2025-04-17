package com.hunger.saviour.portal.repositories;

import com.hunger.saviour.portal.entities.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface OrderRepository extends MongoRepository<OrderEntity, UUID> {
}
