package com.food.food_adda.service;

import com.food.food_adda.dto.MenuItemRequest;
import com.food.food_adda.dto.MenuItemResponse;
import com.food.food_adda.entity.Category;
import com.food.food_adda.entity.MenuItem;
import com.food.food_adda.repository.CategoryRepository;
import com.food.food_adda.repository.MenuItemRepository;
import com.food.food_adda.utility.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private QRCodeGenerator qrCodeGenerator;

    public MenuItemResponse createMenuItem(MenuItemRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        MenuItem menuItem = new MenuItem();
        menuItem.setItemName(request.getItemName());
        menuItem.setDescription(request.getDescription());
        menuItem.setCategory(category);
        menuItem.setPrice(request.getPrice());
        menuItem.setImageUrl(request.getImageUrl());
        menuItem.setDishType(MenuItem.DishType.valueOf(request.getDishType()));
        menuItem.setIsAvailable(request.getIsAvailable() != null ? request.getIsAvailable() : true);
        menuItem.setPreparationTime(request.getPreparationTime());

        // Generate QR code for the menu item
        String qrCodeData = "MENU_ITEM:" + menuItem.getItemName() + "|PRICE:" + menuItem.getPrice();
        String qrCode = qrCodeGenerator.generateQRCode(qrCodeData, 200, 200);
        menuItem.setQrCode(qrCode);

        MenuItem savedItem = menuItemRepository.save(menuItem);
        return convertToResponse(savedItem);
    }

    public MenuItemResponse getMenuItemById(Long itemId) {
        MenuItem menuItem = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));
        return convertToResponse(menuItem);
    }

    public List<MenuItemResponse> getAllAvailableMenuItems() {
        return menuItemRepository.findByIsAvailableTrue()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<MenuItemResponse> getMenuItemsByCategory(Long categoryId) {
        return menuItemRepository.findByCategoryCategoryIdAndIsAvailableTrue(categoryId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<MenuItemResponse> getVegetarianItems() {
        return menuItemRepository.findByIsAvailableTrueAndDishType(MenuItem.DishType.VEG)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<MenuItemResponse> getNonVegetarianItems() {
        return menuItemRepository.findByIsAvailableTrueAndDishType(MenuItem.DishType.NON_VEG)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public MenuItemResponse updateMenuItem(Long itemId, MenuItemRequest request) {
        MenuItem menuItem = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        menuItem.setItemName(request.getItemName());
        menuItem.setDescription(request.getDescription());
        menuItem.setCategory(category);
        menuItem.setPrice(request.getPrice());
        menuItem.setImageUrl(request.getImageUrl());
        menuItem.setDishType(MenuItem.DishType.valueOf(request.getDishType()));
        menuItem.setIsAvailable(request.getIsAvailable());
        menuItem.setPreparationTime(request.getPreparationTime());

        MenuItem updatedItem = menuItemRepository.save(menuItem);
        return convertToResponse(updatedItem);
    }

    public void deleteMenuItem(Long itemId) {
        MenuItem menuItem = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));
        menuItemRepository.delete(menuItem);
    }

    private MenuItemResponse convertToResponse(MenuItem menuItem) {
        MenuItemResponse response = new MenuItemResponse();
        response.setItemId(menuItem.getItemId());
        response.setItemName(menuItem.getItemName());
        response.setDescription(menuItem.getDescription());
        response.setCategoryId(menuItem.getCategory().getCategoryId());
        response.setCategoryName(menuItem.getCategory().getCategoryName());
        response.setPrice(menuItem.getPrice());
        response.setImageUrl(menuItem.getImageUrl());
        response.setQrCode(menuItem.getQrCode());
        response.setDishType(menuItem.getDishType().toString());
        response.setIsAvailable(menuItem.getIsAvailable());
        response.setPreparationTime(menuItem.getPreparationTime());
        return response;
    }
}
