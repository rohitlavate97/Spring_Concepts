package com.hunger.saviour.portal.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEntity {
    @Id
    private UUID orderId;
    private String username;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private OrderStatus orderStatus;// Scheduler every 2 mins
    private Double totalPrice;
    private String transactionId;// Kafka
    private String order_json;// Kafka
}
