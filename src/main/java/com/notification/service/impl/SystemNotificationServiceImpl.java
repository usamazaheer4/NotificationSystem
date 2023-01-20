package com.notification.service.impl;

import com.notification.dto.NotificationDto;
import com.notification.entity.UserNotificationEntity;
import com.notification.repository.UserNotificationRepository;
import com.notification.service.SystemNotificationService;
import com.notification.util.NotificationUtil;
import com.notification.util.UserSystem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SystemNotificationServiceImpl implements SystemNotificationService {

    @Autowired
    private UserNotificationRepository userNotificationRepository;

    /**
     * {@inheritDoc}
     */
    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${kafka.consumer.group.id}")
    @Override
    public void createNotification(NotificationDto notificationDto) {
        try {
            log.info(
                "SystemNotificationService::createNotification() start persisting the notification into the database");
            if(StringUtils.isBlank(notificationDto.getCreatedBy())){
                notificationDto.setCreatedBy(UserSystem.SYSTEM.name());
            }
            UserNotificationEntity notificationEntity = NotificationUtil.mapUserNotificationEntity(notificationDto);
            userNotificationRepository.save(notificationEntity);
            log.info(
                "SystemNotificationService::createNotification() successfully the notification has been persisted");
        } catch (Exception e) {
            log.error("Error occurred while processing the notification:{}", e.getMessage());
        }
    }
}
