package com.hunger.saviour.portal.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private String id;
    private UUID paymentId;
    private String username;
    private List<String> menuItems;
    private Double totalPrice;
    private Instant paymentDateAndTime;
}
