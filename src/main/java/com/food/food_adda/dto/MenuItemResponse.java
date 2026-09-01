package com.food.food_adda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemResponse {
    private Long itemId;
    private String itemName;
    private String description;
    private Long categoryId;
    private String categoryName;
    private Double price;
    private String imageUrl;
    private String qrCode;
    private String dishType;
    private Boolean isAvailable;
    private Integer preparationTime;
}
