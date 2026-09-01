package com.food.food_adda.controller;

import com.food.food_adda.dto.MenuItemRequest;
import com.food.food_adda.dto.MenuItemResponse;
import com.food.food_adda.dto.ApiResponse;
import com.food.food_adda.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/menu")
@CrossOrigin(origins = "*")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping("/items")
    public ResponseEntity<?> createMenuItem(@Valid @RequestBody MenuItemRequest request) {
        try {
            MenuItemResponse menuItem = menuItemService.createMenuItem(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Menu item created successfully", menuItem));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/items/{itemId}")
    public ResponseEntity<?> getMenuItem(@PathVariable Long itemId) {
        try {
            MenuItemResponse menuItem = menuItemService.getMenuItemById(itemId);
            return ResponseEntity.ok(new ApiResponse(true, "Menu item retrieved successfully", menuItem));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/items")
    public ResponseEntity<?> getAllMenuItems() {
        try {
            List<MenuItemResponse> items = menuItemService.getAllAvailableMenuItems();
            return ResponseEntity.ok(new ApiResponse(true, "Menu items retrieved successfully", items));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/items/category/{categoryId}")
    public ResponseEntity<?> getMenuItemsByCategory(@PathVariable Long categoryId) {
        try {
            List<MenuItemResponse> items = menuItemService.getMenuItemsByCategory(categoryId);
            return ResponseEntity.ok(new ApiResponse(true, "Menu items retrieved successfully", items));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/vegetarian")
    public ResponseEntity<?> getVegetarianItems() {
        try {
            List<MenuItemResponse> items = menuItemService.getVegetarianItems();
            return ResponseEntity.ok(new ApiResponse(true, "Vegetarian items retrieved successfully", items));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/non-vegetarian")
    public ResponseEntity<?> getNonVegetarianItems() {
        try {
            List<MenuItemResponse> items = menuItemService.getNonVegetarianItems();
            return ResponseEntity.ok(new ApiResponse(true, "Non-vegetarian items retrieved successfully", items));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<?> updateMenuItem(@PathVariable Long itemId, @Valid @RequestBody MenuItemRequest request) {
        try {
            MenuItemResponse menuItem = menuItemService.updateMenuItem(itemId, request);
            return ResponseEntity.ok(new ApiResponse(true, "Menu item updated successfully", menuItem));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable Long itemId) {
        try {
            menuItemService.deleteMenuItem(itemId);
            return ResponseEntity.ok(new ApiResponse(true, "Menu item deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }
}
