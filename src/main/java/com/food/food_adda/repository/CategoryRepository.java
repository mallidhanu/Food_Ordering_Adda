package com.food.food_adda.repository;

import com.food.food_adda.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryName(String categoryName);
    List<Category> findByIsActiveTrue();
    List<Category> findByDishType(Category.DishType dishType);
}
