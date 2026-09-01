package com.food.food_adda.service;

import com.food.food_adda.dto.CategoryRequest;
import com.food.food_adda.entity.Category;
import com.food.food_adda.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(CategoryRequest request) {
        if (categoryRepository.findByCategoryName(request.getCategoryName()).isPresent()) {
            throw new RuntimeException("Category already exists");
        }

        Category category = new Category();
        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());
        category.setDishType(Category.DishType.valueOf(request.getDishType()));
        category.setIsActive(true);

        return categoryRepository.save(category);
    }

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public List<Category> getAllActiveCategories() {
        return categoryRepository.findByIsActiveTrue();
    }

    public List<Category> getCategoriesByDishType(String dishType) {
        return categoryRepository.findByDishType(Category.DishType.valueOf(dishType));
    }

    public Category updateCategory(Long categoryId, CategoryRequest request) {
        Category category = getCategoryById(categoryId);
        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());
        category.setDishType(Category.DishType.valueOf(request.getDishType()));
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long categoryId) {
        Category category = getCategoryById(categoryId);
        category.setIsActive(false);
        categoryRepository.save(category);
    }
}
