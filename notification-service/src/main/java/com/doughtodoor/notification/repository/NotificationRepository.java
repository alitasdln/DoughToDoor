package com.doughtodoor.notification.repository;

import com.doughtodoor.notification.model.Notification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {
    Optional<Notification> findById(Long id);
}
