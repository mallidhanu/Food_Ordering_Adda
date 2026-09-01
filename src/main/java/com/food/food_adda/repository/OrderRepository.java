package com.food.food_adda.repository;

import com.food.food_adda.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // List<Order> findByUserId(Long userId);
    List<Order> findByUserUserId(Long userId);
    List<Order> findByStatus(Order.OrderStatus status);
    List<Order> findByStatusAndCreatedAtBetween(Order.OrderStatus status, LocalDateTime startDate, LocalDateTime endDate);
    List<Order> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Order> findByPaymentStatus(Order.PaymentStatus paymentStatus);
}
