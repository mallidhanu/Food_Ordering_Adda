package com.food.food_adda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDetailResponse {
    private Long orderItemId;
    private Long itemId;
    private String itemName;
    private Integer quantity;
    private Double unitPrice;
    private Double itemTotal;
    private String specialInstructions;
}
