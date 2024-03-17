package com.doughtodoor.ordermanagement.service;

import com.doughtodoor.ordermanagement.client.UserClient;
import com.doughtodoor.ordermanagement.dto.UserDTO;
import com.doughtodoor.ordermanagement.exception.OrderNotFoundException;
import com.doughtodoor.ordermanagement.model.Order;
import com.doughtodoor.ordermanagement.model.OrderEvent;
import com.doughtodoor.ordermanagement.model.OrderItem;
import com.doughtodoor.ordermanagement.model.OrderStatus;
import com.doughtodoor.ordermanagement.repository.OrderRepository;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final UserClient userClient;

    public OrderService(OrderRepository orderRepository,
                        KafkaTemplate<String,String> kafkaTemplate,
                        UserClient userClient) {
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.userClient = userClient;
    }

    @Transactional
    public Order createOrder(Order newOrder) {

        List<OrderItem> items = newOrder.getItems();
        newOrder.setItems(null); // Reset items temporarily

        Order savedOrder = orderRepository.merge(newOrder);

        List<OrderItem> newItems = new ArrayList<>();

        for (OrderItem item : items) {
            OrderItem newItem = new OrderItem(
                    item.getItemId(),
                    item.getItemName(),
                    item.getQuantity(),
                    item.getPrice()
            );
            newItem.setOrder(savedOrder); // Set the reference to the new order
            newItems.add(newItem);
        }

        savedOrder.setItems(newItems); // Set the new items in the order

        UserDTO user = userClient.getUserDetails(newOrder.getUserId());
        String customerEmail = user.getEmail();

        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrderId(newOrder.getOrderId());
        orderEvent.setCustomerEmail(customerEmail);
        orderEvent.setOrderStatus(OrderStatus.PENDING);
        kafkaTemplate.send("order-status-updates", orderEvent.toString());

        // Update the order in the repository with new items
        return orderRepository.update(savedOrder);
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
            item.setOrder(orderToAdd);
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
