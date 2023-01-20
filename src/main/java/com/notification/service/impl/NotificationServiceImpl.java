package com.notification.service.impl;

import com.notification.dto.NotificationDto;
import com.notification.entity.NotificationEntity;
import com.notification.repository.NotificationRepository;
import com.notification.service.NotificationService;
import com.notification.util.NotificationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<NotificationDto> getAllNotification(Integer pageNo, Integer pageSize, String sortById) {
        log.info("NotificationService::getAllNotification() start getting the notifications from the database");
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortById));
        Page<NotificationEntity> notifications = notificationRepository.findAll(paging);
        log.info("NotificationService::getAllNotification() successfully notification has been got from the database");
        return getNotifications(notifications);
    }

    /**
     * Maps notifications entity into the notification dtos
     *
     * @param notifications the notifications
     * @return the list of notification
     */
    private List<NotificationDto> getNotifications(Page<NotificationEntity> notifications) {
        List<NotificationEntity> entities = notifications.getContent();
        return entities.stream().map(NotificationUtil::mapNotificationDto).collect(Collectors.toList());
    }
}
