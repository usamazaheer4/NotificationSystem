package com.notification.service;

import com.notification.dto.NotificationDto;

public interface SystemNotificationService {

    /**
     * Creates notification by system.
     *
     * @param notificationDto the notification dto contains the notification details
     */
    void createNotification(NotificationDto notificationDto);
}
