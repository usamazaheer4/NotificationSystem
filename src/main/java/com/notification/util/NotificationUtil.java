package com.notification.util;

import com.notification.dto.NotificationDto;
import com.notification.dto.UserNotificationDto;
import com.notification.entity.NotificationEntity;
import com.notification.entity.UserNotificationEntity;
import org.apache.commons.lang3.StringUtils;

public final class NotificationUtil {

    private NotificationUtil() {
        throw new IllegalArgumentException("Util class");
    }

    /**
     * Maps notification dto into the user notification entity for the database.
     *
     * @param notificationDto the notification contains notification details
     * @return the user notification entity
     */
    public static UserNotificationEntity mapUserNotificationEntity(NotificationDto notificationDto) {
        NotificationEntity entity = new NotificationEntity();
        UserNotificationEntity userNotification = new UserNotificationEntity();
        entity.setCreatedBy(StringUtils.isNotBlank(notificationDto.getCreatedBy()) ? notificationDto.getCreatedBy() :
            UserSystem.USER.name());
        entity.setContent(notificationDto.getContent());
        entity.setTitle(notificationDto.getTitle());
        entity.setHidden(notificationDto.isHidden());
        entity.setRead(notificationDto.isRead());
        entity.setContent(notificationDto.getContent());
        userNotification.setToUser(notificationDto.getUserNotification().getToUser());
        userNotification.setFromUser(notificationDto.getUserNotification().getFromUser());
        userNotification.setId(notificationDto.getUserNotification().getId());
        entity.setId(notificationDto.getId());
        userNotification.setNotificationEntity(entity);
        return userNotification;
    }

    /**
     * Map notification entity into the notification dto.
     *
     * @param notificationEntity the notification entity
     * @return the notification dto
     */
    public static NotificationDto mapNotificationDto(NotificationEntity notificationEntity) {
        NotificationDto notificationDto = new NotificationDto();
        UserNotificationDto userNotification = new UserNotificationDto();
        notificationDto.setId(notificationEntity.getId());
        notificationDto.setCreateDateTime(notificationEntity.getCreateDateTime());
        notificationDto.setCreatedBy(notificationEntity.getCreatedBy());
        notificationDto.setContent(notificationEntity.getContent());
        notificationDto.setTitle(notificationEntity.getTitle());
        notificationDto.setHidden(notificationEntity.isHidden());
        notificationDto.setCreateDateTime(notificationDto.getCreateDateTime());
        notificationDto.setRead(notificationEntity.isRead());
        notificationDto.setContent(notificationEntity.getContent());
        userNotification.setToUser(notificationEntity.getUserNotification().getToUser());
        userNotification.setFromUser(notificationEntity.getUserNotification().getFromUser());
        userNotification.setId(notificationEntity.getUserNotification().getId());
        notificationDto.setUserNotification(userNotification);
        return notificationDto;
    }

    /**
     * Maps user notification entity to notification entity.
     *
     * @param userNotificationEntity the user notification entity
     * @return the notification dto
     */
    public static NotificationDto mapNotificationDto(UserNotificationEntity userNotificationEntity) {
        NotificationDto notificationDto = new NotificationDto();
        UserNotificationDto userNotification = new UserNotificationDto();
        notificationDto.setId(userNotificationEntity.getNotificationEntity().getId());
        notificationDto.setContent(userNotificationEntity.getNotificationEntity().getContent());
        notificationDto.setTitle(userNotificationEntity.getNotificationEntity().getTitle());
        notificationDto.setHidden(userNotificationEntity.getNotificationEntity().isHidden());
        notificationDto.setCreateDateTime(userNotificationEntity.getNotificationEntity().getCreateDateTime());
        notificationDto.setCreatedBy(userNotificationEntity.getNotificationEntity().getCreatedBy());
        notificationDto.setRead(userNotificationEntity.getNotificationEntity().isRead());
        notificationDto.setContent(userNotificationEntity.getNotificationEntity().getContent());
        userNotification.setToUser(userNotificationEntity.getToUser());
        userNotification.setFromUser(userNotificationEntity.getFromUser());
        userNotification.setId(userNotificationEntity.getId());
        notificationDto.setUserNotification(userNotification);
        return notificationDto;
    }
}
