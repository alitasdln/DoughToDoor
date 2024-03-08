package com.doughtodoor.notification.controller;

import com.doughtodoor.notification.model.Notification;
import com.doughtodoor.notification.model.NotificationRequest;
import com.doughtodoor.notification.model.NotificationResponse;
import com.doughtodoor.notification.service.NotificationService;
import org.springframework.web.bind.annotation.*;

@RestController
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/notifications")
    public NotificationResponse sendNotification(@RequestBody NotificationRequest request) {
        return notificationService.sendNotification(request);
    }

    @GetMapping("/notifications/{id}")
    public Notification getNotificationById(@PathVariable Long id) {
        return notificationService.getNotificationById(id);
    }
}
