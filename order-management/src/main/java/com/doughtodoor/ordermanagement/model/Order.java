package com.doughtodoor.ordermanagement.model;

import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "`order`")
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order(Long orderId, List<OrderItem> items, OrderStatus status) {
        this.orderId = orderId;
        this.items = items;
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
