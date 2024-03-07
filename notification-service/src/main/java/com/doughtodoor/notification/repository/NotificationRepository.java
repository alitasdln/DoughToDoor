
package com.doughtodoor.notification.repository;

import com.doughtodoor.notification.model.Notification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {
}
