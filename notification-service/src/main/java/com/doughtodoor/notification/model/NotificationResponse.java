package com.doughtodoor.notification.model;

public class NotificationResponse {
    private boolean success;
    private String errorMessage;

    public NotificationResponse (boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return false;
    }

    public Object getErrorMessage() {
        return null;
    }
}