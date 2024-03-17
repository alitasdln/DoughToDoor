package com.doughtodoor.ordermanagement.model;

public class OrderEvent {
    private Long orderId;
    private String customerEmail;
    private OrderStatus orderStatus;

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
