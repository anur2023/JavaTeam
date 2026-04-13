package com.example.OnlineStationary.module.order.service;

import com.example.OnlineStationary.module.cart.entity.Cart;
import com.example.OnlineStationary.module.cart.entity.CartItem;
import com.example.OnlineStationary.module.cart.repository.CartRepository;
import com.example.OnlineStationary.module.cart.service.CartService;
import com.example.OnlineStationary.module.discount.service.DiscountService;
import com.example.OnlineStationary.module.order.dto.OrderItemResponse;
import com.example.OnlineStationary.module.order.dto.OrderResponse;
import com.example.OnlineStationary.module.order.entity.Order;
import com.example.OnlineStationary.module.order.entity.OrderItem;
import com.example.OnlineStationary.module.order.repository.OrderItemRepository;
import com.example.OnlineStationary.module.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private OrderItemRepository orderItemRepository;
    @Autowired private CartRepository cartRepository;
    @Autowired private CartService cartService;
    @Autowired private DiscountService discountService;

    @Transactional
    public OrderResponse placeOrder(Long userId, String couponCode) {
        Cart cart = cartRepository.findByUserUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart is empty"));

        if (cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            throw new RuntimeException("No items in cart");
        }

        BigDecimal total = BigDecimal.ZERO;

        Order order = new Order();
        order.setUser(cart.getUser());

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()) {
            BigDecimal itemTotal = cartItem.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            total = total.add(itemTotal);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItems.add(orderItem);
        }

        // Apply coupon if provided
        if (couponCode != null && !couponCode.isBlank()) {
            total = discountService.applyCoupon(couponCode, total);
        }

        order.setTotalAmount(total);
        order.setOrderItems(orderItems);
        orderRepository.save(order);

        // Clear cart after order
        cartService.clearCart(userId);

        return buildOrderResponse(order);
    }

    public List<OrderResponse> getOrdersByUser(Long userId) {
        List<Order> orders = orderRepository.findByUserUserIdOrderByCreatedAtDesc(userId);
        List<OrderResponse> responses = new ArrayList<>();
        for (Order order : orders) {
            responses.add(buildOrderResponse(order));
        }
        return responses;
    }

    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return buildOrderResponse(order);
    }

    public OrderResponse updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(Order.OrderStatus.valueOf(status.toUpperCase()));
        orderRepository.save(order);
        return buildOrderResponse(order);
    }

    private OrderResponse buildOrderResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getOrderId());
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus().name());
        response.setCreatedAt(order.getCreatedAt());

        List<OrderItemResponse> itemResponses = new ArrayList<>();
        for (OrderItem item : order.getOrderItems()) {
            OrderItemResponse ir = new OrderItemResponse();
            ir.setProductId(item.getProduct().getProductId());
            ir.setProductName(item.getProduct().getName());
            ir.setQuantity(item.getQuantity());
            ir.setPrice(item.getPrice());
            itemResponses.add(ir);
        }
        response.setItems(itemResponses);
        return response;
    }
}