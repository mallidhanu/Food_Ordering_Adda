package com.food.food_adda.repository;

import com.food.food_adda.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByIsAvailableTrue();
    List<MenuItem> findByCategoryCategoryIdAndIsAvailableTrue(Long categoryId);
    List<MenuItem> findByDishType(MenuItem.DishType dishType);
    Optional<MenuItem> findByItemNameIgnoreCase(String itemName);
    List<MenuItem> findByIsAvailableTrueAndDishType(MenuItem.DishType dishType);
}
