package com.notification.repository;

import com.notification.entity.UserNotificationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotificationRepository extends CrudRepository<UserNotificationEntity, Long> {
}
