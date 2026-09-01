package com.food.food_adda.controller;

import com.food.food_adda.dto.CreateOrderRequest;
import com.food.food_adda.dto.OrderResponse;
import com.food.food_adda.dto.ApiResponse;
import com.food.food_adda.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/users/{userId}")
    public ResponseEntity<?> createOrder(@PathVariable Long userId, @Valid @RequestBody CreateOrderRequest request) {
        try {
            OrderResponse order = orderService.createOrder(userId, request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Order created successfully", order));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable Long orderId) {
        try {
            OrderResponse order = orderService.getOrderById(orderId);
            return ResponseEntity.ok(new ApiResponse(true, "Order retrieved successfully", order));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/users/{userId}/all")
    public ResponseEntity<?> getUserOrders(@PathVariable Long userId) {
        try {
            List<OrderResponse> orders = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse(true, "Orders retrieved successfully", orders));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getOrdersByStatus(@PathVariable String status) {
        try {
            List<OrderResponse> orders = orderService.getOrdersByStatus(status);
            return ResponseEntity.ok(new ApiResponse(true, "Orders retrieved successfully", orders));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @PutMapping("/{orderId}/status/{status}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @PathVariable String status) {
        try {
            OrderResponse order = orderService.updateOrderStatus(orderId, status);
            return ResponseEntity.ok(new ApiResponse(true, "Order status updated successfully", order));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @PutMapping("/{orderId}/payment/{paymentStatus}")
    public ResponseEntity<?> updatePaymentStatus(@PathVariable Long orderId, @PathVariable String paymentStatus) {
        try {
            OrderResponse order = orderService.updatePaymentStatus(orderId, paymentStatus);
            return ResponseEntity.ok(new ApiResponse(true, "Payment status updated successfully", order));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {
        try {
            orderService.cancelOrder(orderId);
            return ResponseEntity.ok(new ApiResponse(true, "Order cancelled successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }
}
