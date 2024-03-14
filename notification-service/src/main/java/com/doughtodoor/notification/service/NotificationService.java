package com.doughtodoor.notification.service;

import com.doughtodoor.notification.exception.NotificationDoesntExistException;
import com.doughtodoor.notification.model.Notification;
import com.doughtodoor.notification.model.NotificationRequest;
import com.doughtodoor.notification.model.NotificationResponse;
import com.doughtodoor.notification.model.NotificationStatus;
import com.doughtodoor.notification.repository.NotificationRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationService {

    private static final String NOTIFICATION_TOPIC = "notification_topic";
    private final NotificationRepository notificationRepository;
    private final KafkaTemplate<String, NotificationRequest> kafkaTemplate;
    private final EmailService emailService;
    
    public NotificationService(NotificationRepository notificationRepository, KafkaTemplate<String, NotificationRequest> kafkaTemplate, EmailService emailService) {
        this.notificationRepository = notificationRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.emailService = emailService;
    }
    
    public NotificationResponse sendNotification(NotificationRequest request) {
        Notification notification = new Notification(request.getRecipient(), request.getMessage(), request.getType());

        // Persist the notification in the database
        notification = notificationRepository.save(notification);

        // Send the notification based on the type
        try {
            switch (request.getType()) {
                case EMAIL:
                    sendEmailNotification(request);
                    break;
                case SMS:
                    sendSMSNotification(request);
                    break;
                case PUSH_NOTIFICATION:
                    sendPushNotification(request);
                    break;
            }
            notification.setStatus(NotificationStatus.SENT);
        // Add more specific error handling later
        } catch (Exception e) {
            notification.setStatus(NotificationStatus.FAILED);
            // Log the error and handle it appropriately
        }

        // Update the notification status in the database
        notificationRepository.save(notification);

        // Send the notification to Kafka
        kafkaTemplate.send(NOTIFICATION_TOPIC, request);

        return new NotificationResponse(true, "Notification sent successfully");
    }

    private void sendPushNotification(NotificationRequest request) {
    }

    private void sendSMSNotification(NotificationRequest request) {
    }

    private void sendEmailNotification(NotificationRequest request) {
        emailService.sendEmail(request, "Notification", true);
    }

    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

}
