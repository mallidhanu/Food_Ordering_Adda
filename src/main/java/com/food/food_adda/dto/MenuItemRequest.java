package com.food.food_adda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemRequest {
    @NotBlank(message = "Item name is required")
    private String itemName;

    private String description;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Price is required")
    private Double price;

    private String imageUrl;

    @NotNull(message = "Dish type is required")
    private String dishType; // VEG or NON_VEG

    @NotNull(message = "Preparation time is required")
    private Integer preparationTime;

    private Boolean isAvailable = true;
}
