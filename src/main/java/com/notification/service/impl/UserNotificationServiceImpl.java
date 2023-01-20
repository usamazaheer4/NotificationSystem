package com.notification.service.impl;

import com.notification.dto.NotificationDto;
import com.notification.entity.UserNotificationEntity;
import com.notification.repository.UserNotificationRepository;
import com.notification.service.UserNotificationService;
import com.notification.util.NotificationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
public class UserNotificationServiceImpl implements UserNotificationService {

    @Autowired
    private UserNotificationRepository userNotificationRepository;

    @Value("${kafka.topic.name}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, NotificationDto> kafkaTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public NotificationDto createNotification(NotificationDto notificationDto) {
        log.info("UserNotificationService::createNotification() start persisting the notification into the database");
        UserNotificationEntity notificationEntity = NotificationUtil.mapUserNotificationEntity(notificationDto);
        notificationEntity = userNotificationRepository.save(notificationEntity);
        log.info(
            "UserNotificationService::createNotification() successfully the notification has been persisted into the " +
                "database");
        return NotificationUtil.mapNotificationDto(notificationEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void publishNotification(NotificationDto notificationDto) {
        log.info("start publishing the notification on a kafka");
        ListenableFuture<SendResult<String, NotificationDto>> future = kafkaTemplate.send(topicName, notificationDto);
        future.addCallback(new ListenableFutureCallback<SendResult<String, NotificationDto>>() {
            @Override
            public void onSuccess(SendResult<String, NotificationDto> result) {
                log.debug("Successfully the notification has been published");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Failed to publish the notification error:{}", ex.getMessage());
            }
        });
    }
}
