package com.doughtodoor.ordermanagement.aop;

import com.doughtodoor.ordermanagement.exception.OrderNotFoundException;
import com.doughtodoor.ordermanagement.model.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OrderNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String orderNotFoundHandler(OrderNotFoundException ex) {
        return ex.getMessage();
    }
}
