package com.food.food_adda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    @NotBlank(message = "Category name is required")
    private String categoryName;

    private String description;

    @NotNull(message = "Dish type is required")
    private String dishType; // VEG, NON_VEG, VEGAN, VEGETARIAN_NON_VEG_MIXED
}
