package com.doughtodoor.ordermanagement.model;

import java.util.List;

public class Order {

    private String orderId;
    private List<OrderItem> items;
    private OrderStatus status;
}
