package com.doughtodoor.notification.model;

import java.time.LocalDateTime;

public class Notification {

    private Long id;
    private String recipient;
    private String message;
    private LocalDateTime timestamp;
    private NotificationStatus status;
    private NotificationType type;

    public Notification(String recipient, String message, NotificationType type) {
        this.recipient = recipient;
        this.message = message;
        this.type = type;
        this.timestamp = LocalDateTime.now();
        this.status = NotificationStatus.PENDING;
    }

}