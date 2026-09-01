package com.food.food_adda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    @NotEmpty(message = "Order items cannot be empty")
    private List<OrderItemRequest> orderItems;

    private Double discountAmount = 0.0;
    private String orderNotes;
}
