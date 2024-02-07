package com.doughtodoor.notification.model;

public class NotificationRequest {
    private String recipient;
    private String message;
    private NotificationType type;

    public NotificationRequest(String recipient, String message, NotificationType type) {
        this.recipient = recipient;
        this.message = message;
        this.type = type;
    }
}