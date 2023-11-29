package com.doughtodoor.ordermanagement.controller;

import com.doughtodoor.ordermanagement.model.Order;
import com.doughtodoor.ordermanagement.model.OrderItem;
import com.doughtodoor.ordermanagement.model.OrderStatus;
import com.doughtodoor.ordermanagement.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public String home(){
        return "Welcome Order Home";
    }

    @PostMapping("/orders")
    public Order createOrder(@RequestBody Order newOrder) {
        return orderService.createOrder(newOrder);
    }

    @GetMapping("/orders/{id}")
    public Order getOrderById(@PathVariable Long orderId){
        return orderService.getOrderById(orderId);
    }

    @PutMapping("/orders/{orderId}/status")
    public Order updateOrderStatus(@PathVariable Long orderId,@RequestBody OrderStatus orderStatus){
        return orderService.updateOrderStatus(orderId, orderStatus);
    }

    @PutMapping("/orders/{orderId}/items")
    public Order addItemToOrder(@PathVariable Long orderId, @RequestBody OrderItem orderItem){
        return orderService.addItemToOrder(orderId, orderItem);
    }

    @DeleteMapping("/orders/{orderId}/items")
    public Order removeItemFromOrder(@PathVariable Long orderId, Long itemId){
        return orderService.removeItemFromOrder(orderId, itemId);
    }

}
