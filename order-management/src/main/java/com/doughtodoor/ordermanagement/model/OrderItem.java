package com.doughtodoor.ordermanagement.model;

import jakarta.persistence.*;

@Entity
public class OrderItem {
    @Id
    private Long itemId;
    private String itemName;
    private  int quantity;
    private double price;

    @ManyToOne
    private Order order;

    public OrderItem(Long itemId, String itemName, int quantity, double price) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }
}
