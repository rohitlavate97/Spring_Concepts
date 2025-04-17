package com.hunger.saviour.portal.application.collections;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.extensions.annotations.DynamoDbAutoGeneratedTimestampAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@DynamoDbBean
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    private String id;
    private UUID paymentId;
    private String username;
    private List<String> menuItems;
    private Double totalPrice;
    private Instant paymentDateAndTime;

    @DynamoDbPartitionKey
    public String getId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        return id;
    }

    @DynamoDbAutoGeneratedTimestampAttribute
    public Instant getDate() {
        return paymentDateAndTime;
    }

    @DynamoDbAttribute("paymentId")
    public UUID getPaymentId() {
        return paymentId;
    }

    @DynamoDbAttribute("username")
    public String getUsername() {
        return username;
    }

    @DynamoDbAttribute("menuItems")
    public List<String> getMenuItems() {
        return menuItems;
    }

    @DynamoDbAttribute("totalPrice")
    public Double getTotalPrice() {
        return totalPrice;
    }
}
