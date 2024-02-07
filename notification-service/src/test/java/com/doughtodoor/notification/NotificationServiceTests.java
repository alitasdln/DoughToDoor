package com.doughtodoor.notification;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.doughtodoor.notification.model.Notification;
import com.doughtodoor.notification.model.NotificationRequest;
import com.doughtodoor.notification.model.NotificationResponse;
import com.doughtodoor.notification.model.NotificationType;
import com.doughtodoor.notification.repository.NotificationRepository;
import com.doughtodoor.notification.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NotificationServiceTests {

    private NotificationService notificationService;
    private NotificationRepository notificationRepositoryMock;

    @BeforeEach
    public void setUp() {
        // Create mock object for dependency
        notificationRepositoryMock = mock(NotificationRepository.class);

        notificationService = new NotificationService(notificationRepositoryMock);
    }

    @Test
    public void testSendNotification_Success() {
        // Given
        NotificationRequest request = new NotificationRequest("test@example.com", "Test message", NotificationType.EMAIL);
        when(notificationRepositoryMock.save(any(Notification.class)))
                .thenReturn(new Notification("test@example.com","Test message", NotificationType.EMAIL));

        // When
        NotificationResponse response = notificationService.sendNotification(request);

        // Then
        assertTrue(response.isSuccess());
        assertNull(response.getErrorMessage());
        verify(notificationRepositoryMock, times(1)).save(any(Notification.class));
    }

    // Add more test cases as needed
}
