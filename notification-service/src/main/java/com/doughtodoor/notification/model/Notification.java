package com.doughtodoor.notification.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String recipient;
    private String message;
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    public Notification(String recipient, String message, NotificationType type) {
        this.recipient = recipient;
        this.message = message;
        this.type = type;
        this.timestamp = LocalDateTime.now();
        this.status = NotificationStatus.PENDING;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }
}