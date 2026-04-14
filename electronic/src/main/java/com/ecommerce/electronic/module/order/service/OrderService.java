package com.ecommerce.electronic.module.order.service;

import com.ecommerce.electronic.module.order.dto.OrderRequestDto;
import com.ecommerce.electronic.module.order.dto.OrderItemDto;
import com.ecommerce.electronic.module.order.entity.Order;
import com.ecommerce.electronic.module.order.entity.OrderItem;
import com.ecommerce.electronic.module.order.entity.Warranty;
import com.ecommerce.electronic.module.order.repository.OrderRepository;
import com.ecommerce.electronic.module.order.repository.OrderItemRepository;
import com.ecommerce.electronic.module.order.repository.WarrantyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private WarrantyRepository warrantyRepository;

    public Order placeOrder(OrderRequestDto dto) {

        Order order = new Order();
        order.setUserId(dto.getUserId());
        order.setStatus("PENDING");

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0.0;

        for (OrderItemDto itemDto : dto.getItems()) {

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProductId(itemDto.getProductId());
            item.setQuantity(itemDto.getQuantity());
            item.setPrice(itemDto.getPrice());

            totalAmount += itemDto.getPrice() * itemDto.getQuantity();
            orderItems.add(item);
        }

        order.setTotalAmount(totalAmount);
        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        for (OrderItem item : savedOrder.getOrderItems()) {

            Warranty warranty = new Warranty();
            warranty.setOrderItem(item);
            warranty.setStartDate(LocalDate.now());
            warranty.setExpiryDate(LocalDate.now().plusYears(1));
            warranty.setStatus("ACTIVE");

            warrantyRepository.save(warranty);
        }

        return savedOrder;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            return null;
        }

        if (order.getStatus().equals("SHIPPED") || order.getStatus().equals("DELIVERED")) {
            throw new RuntimeException("Order cannot be cancelled at this stage");
        }

        order.setStatus("CANCELLED");

        return orderRepository.save(order);
    }
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    public Order updateOrderStatus(Long orderId, String status) {

        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            return null;
        }

        order.setStatus(status);

        return orderRepository.save(order);
    }
}