package com.doughtodoor.notification.service;

import com.doughtodoor.notification.model.NotificationRequest;
import com.doughtodoor.notification.model.NotificationResponse;
import com.doughtodoor.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public NotificationResponse sendNotification(NotificationRequest request) {
        return null;
    }
}
