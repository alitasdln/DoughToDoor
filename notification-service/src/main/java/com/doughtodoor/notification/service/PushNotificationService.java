package com.doughtodoor.notification.service;

import com.doughtodoor.notification.model.NotificationRequest;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationService {

    private final Logger logger = LoggerFactory.getLogger(PushNotificationService.class);

    @Autowired
    public PushNotificationService() {
    }

    public void sendPushNotification(NotificationRequest request) {
        try {
            Message message = Message.builder()
                    .putData("message", request.getMessage())
                    .setToken(request.getRecipient())
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);

            logger.info("Push notification sent to {}. Response: {}", request.getRecipient(), response);
        } catch (Exception e) {
            logger.error("Failed to send push notification to {}", request.getRecipient(), e);
            throw new RuntimeException("Failed to send push notification to " + request.getRecipient(), e);
        }
    }
}