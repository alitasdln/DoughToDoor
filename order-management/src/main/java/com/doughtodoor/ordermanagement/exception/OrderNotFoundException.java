package com.doughtodoor.ordermanagement.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(Long orderId) {
        super("Order with id" + orderId + "was not found.");
    }
}
