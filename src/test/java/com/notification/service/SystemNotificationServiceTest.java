package com.notification.service;

import com.notification.dto.NotificationDto;
import com.notification.dto.UserNotificationDto;
import com.notification.entity.NotificationEntity;
import com.notification.entity.UserNotificationEntity;
import com.notification.repository.UserNotificationRepository;
import com.notification.service.impl.SystemNotificationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SystemNotificationServiceTest {

    @InjectMocks
    private SystemNotificationServiceImpl service;

    @Mock
    private UserNotificationRepository userNotificationRepository;

    @Test
    void testCreateNotificationWhenAllRequiredParamArePassedExpectNotificationDto() {
        // given
        Mockito.when(userNotificationRepository.save(Mockito.any(UserNotificationEntity.class))).thenReturn(
            getUserNotificationEntity());
        // when
        service.createNotification(getNotificationDto());
        // then
        Mockito.verify(userNotificationRepository, Mockito.times(1)).save(Mockito.any(UserNotificationEntity.class));
    }

    private NotificationDto getNotificationDto() {
        UserNotificationDto userNotificationDto = new UserNotificationDto();
        NotificationDto notificationDto = new NotificationDto();
        userNotificationDto.setToUser(1L);
        userNotificationDto.setFromUser(2L);
        notificationDto.setUserNotification(userNotificationDto);
        notificationDto.setTitle("Get new credit card");
        notificationDto.setContent("Amazon offers new credit card for the customers");
        notificationDto.setHidden(true);
        notificationDto.setRead(false);
        notificationDto.setId(1L);
        notificationDto.setCreatedBy("USER");
        return notificationDto;
    }

    private UserNotificationEntity getUserNotificationEntity() {
        UserNotificationEntity userNotificationEntity = new UserNotificationEntity();
        NotificationEntity notificationEntity = new NotificationEntity();
        userNotificationEntity.setToUser(1L);
        userNotificationEntity.setFromUser(2L);
        notificationEntity.setTitle("Get new credit card");
        notificationEntity.setContent("Amazon offers new credit card for the customers");
        notificationEntity.setHidden(true);
        notificationEntity.setRead(false);
        notificationEntity.setId(1L);
        notificationEntity.setCreatedBy("USER");
        userNotificationEntity.setNotificationEntity(notificationEntity);
        return userNotificationEntity;
    }
}
