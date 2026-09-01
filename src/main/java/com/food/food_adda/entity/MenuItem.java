package com.food.food_adda.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "menu_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(nullable = false)
    private String itemName;

    @Column(length = 500)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private Double price;

    @Column(length = 1000)
    private String imageUrl;

    @Column(length = 10000)
    private String qrCode; // QR Code encoded data (base64-encoded image)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DishType dishType; // VEG, NON_VEG

    @Column(nullable = false)
    private Boolean isAvailable = true;

    @Column(nullable = false)
    private Integer preparationTime; // in minutes

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column
    private LocalDateTime updatedAt;

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public enum DishType {
        VEG, NON_VEG
    }
}
