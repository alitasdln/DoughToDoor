package com.doughtodoor.notification.exception;

public class NotificationDoesntExistException extends RuntimeException {
    public NotificationDoesntExistException(String message) {
        super(message);
    }
}
