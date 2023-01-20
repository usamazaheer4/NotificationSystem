package com.notification.service;

import com.notification.dto.NotificationDto;

public interface UserNotificationService {

    /**
     * Creates notification by the user into the database.
     *
     * @param notificationDto the notification dto contains the notification detail
     * @return the notification dto
     */
    NotificationDto createNotification(NotificationDto notificationDto);

    /**
     * Publishes the notification on a queue.
     *
     * @param notificationDto the notification dto contains the notification detail
     */
    void publishNotification(NotificationDto notificationDto);
}
