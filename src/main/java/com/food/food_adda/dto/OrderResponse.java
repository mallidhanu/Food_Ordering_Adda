package com.food.food_adda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private String userEmail;
    private Double totalAmount;
    private Double discountAmount;
    private Double taxAmount;
    private Double finalAmount;
    private String status;
    private String paymentStatus;
    private String paymentMethod;
    private String orderNotes;
    private LocalDateTime createdAt;
    private LocalDateTime deliveredAt;
    private List<OrderItemDetailResponse> orderItems;
}
