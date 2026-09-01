package com.food.food_adda.service;

import com.food.food_adda.dto.CreateOrderRequest;
import com.food.food_adda.dto.OrderItemRequest;
import com.food.food_adda.dto.OrderResponse;
import com.food.food_adda.dto.OrderItemDetailResponse;
import com.food.food_adda.entity.*;
import com.food.food_adda.repository.MenuItemRepository;
import com.food.food_adda.repository.OrderRepository;
import com.food.food_adda.repository.OrderItemRepository;
import com.food.food_adda.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Double TAX_RATE = 0.05; // 5% tax

    public OrderResponse createOrder(Long userId, CreateOrderRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.OrderStatus.PENDING);
        order.setPaymentStatus(Order.PaymentStatus.PENDING);
        order.setOrderNotes(request.getOrderNotes());
        order.setDiscountAmount(request.getDiscountAmount() != null ? request.getDiscountAmount() : 0.0);

        // Calculate total amount
        Double totalAmount = 0.0;
        for (OrderItemRequest itemRequest : request.getOrderItems()) {
            MenuItem menuItem = menuItemRepository.findById(itemRequest.getItemId())
                    .orElseThrow(() -> new RuntimeException("Menu item not found: " + itemRequest.getItemId()));
            totalAmount += menuItem.getPrice() * itemRequest.getQuantity();
        }

        order.setTotalAmount(totalAmount);
        Double taxAmount = totalAmount * TAX_RATE;
        order.setTaxAmount(taxAmount);
        order.setFinalAmount(totalAmount + taxAmount - order.getDiscountAmount());

        Order savedOrder = orderRepository.save(order);

        // Add order items
        for (OrderItemRequest itemRequest : request.getOrderItems()) {
            MenuItem menuItem = menuItemRepository.findById(itemRequest.getItemId())
                    .orElseThrow(() -> new RuntimeException("Menu item not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setUnitPrice(menuItem.getPrice());
            orderItem.setSpecialInstructions(itemRequest.getSpecialInstructions());

            orderItemRepository.save(orderItem);
        }

        return convertToResponse(savedOrder);
    }

    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return convertToResponse(order);
    }

    public List<OrderResponse> getUserOrders(Long userId) {
        return orderRepository.findByUserUserId(userId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<OrderResponse> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(Order.OrderStatus.valueOf(status))
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        order.setStatus(Order.OrderStatus.valueOf(status));
        
        if (status.equals("DELIVERED")) {
            order.setDeliveredAt(java.time.LocalDateTime.now());
        }

        Order updatedOrder = orderRepository.save(order);
        return convertToResponse(updatedOrder);
    }

    public OrderResponse updatePaymentStatus(Long orderId, String paymentStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        order.setPaymentStatus(Order.PaymentStatus.valueOf(paymentStatus));
        Order updatedOrder = orderRepository.save(order);
        return convertToResponse(updatedOrder);
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(Order.OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    private OrderResponse convertToResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getOrderId());
        response.setUserId(order.getUser().getUserId());
        response.setUserEmail(order.getUser().getEmail());
        response.setTotalAmount(order.getTotalAmount());
        response.setDiscountAmount(order.getDiscountAmount());
        response.setTaxAmount(order.getTaxAmount());
        response.setFinalAmount(order.getFinalAmount());
        response.setStatus(order.getStatus().toString());
        response.setPaymentStatus(order.getPaymentStatus().toString());
        response.setPaymentMethod(order.getPaymentMethod());
        response.setOrderNotes(order.getOrderNotes());
        response.setCreatedAt(order.getCreatedAt());
        response.setDeliveredAt(order.getDeliveredAt());

        List<OrderItemDetailResponse> items = orderItemRepository.findByOrderOrderId(order.getOrderId())
                .stream()
                .map(this::convertOrderItemToResponse)
                .collect(Collectors.toList());
        response.setOrderItems(items);

        return response;
    }

    private OrderItemDetailResponse convertOrderItemToResponse(OrderItem orderItem) {
        OrderItemDetailResponse response = new OrderItemDetailResponse();
        response.setOrderItemId(orderItem.getOrderItemId());
        response.setItemId(orderItem.getMenuItem().getItemId());
        response.setItemName(orderItem.getMenuItem().getItemName());
        response.setQuantity(orderItem.getQuantity());
        response.setUnitPrice(orderItem.getUnitPrice());
        response.setItemTotal(orderItem.getItemTotal());
        response.setSpecialInstructions(orderItem.getSpecialInstructions());
        return response;
    }
}
