package com.doughtodoor.ordermanagement.service;

import com.doughtodoor.ordermanagement.exception.OrderNotFoundException;
import com.doughtodoor.ordermanagement.model.Order;
import com.doughtodoor.ordermanagement.model.OrderItem;
import com.doughtodoor.ordermanagement.model.OrderStatus;
import com.doughtodoor.ordermanagement.repository.OrderRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;


    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {
        return orderRepository.persist(order);
    }

    //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public Order getOrderById(Long orderId) {
        return orderRepository.getReferenceById(orderId);
    }

    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order orderToUpdate = orderRepository.getReferenceById(orderId);

        if (orderToUpdate != null) {
            orderToUpdate.setStatus(status);
            return orderRepository.update(orderToUpdate);
        } else {
            throw new OrderNotFoundException(orderId);
        }
    }

    public Order addItemToOrder(Long orderId, OrderItem item) {

        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        Order orderToAdd = getOrderById(orderId);
        if (orderToAdd != null) {
            orderToAdd.getItems().add(item);
            return orderRepository.update(orderToAdd);
        } else {
            throw new OrderNotFoundException(orderId);
        }
    }

    public Order removeItemFromOrder(Long orderId, Long itemId) {

        Order orderToRemoveItem = getOrderById(orderId);
        if (orderToRemoveItem != null) {
            orderToRemoveItem.getItems().removeIf(orderItem -> orderItem.getItemId().equals(itemId));
            return orderRepository.update(orderToRemoveItem);
        }
        else {
            throw new OrderNotFoundException(orderId);
        }

    }

}