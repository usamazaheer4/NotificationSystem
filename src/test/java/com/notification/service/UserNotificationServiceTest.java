package com.notification.service;

import com.notification.dto.NotificationDto;
import com.notification.dto.UserNotificationDto;
import com.notification.entity.NotificationEntity;
import com.notification.entity.UserNotificationEntity;
import com.notification.repository.UserNotificationRepository;
import com.notification.service.impl.UserNotificationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.concurrent.SettableListenableFuture;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith({MockitoExtension.class})
class UserNotificationServiceTest {

    @InjectMocks
    UserNotificationServiceImpl service;

    @Mock
    KafkaTemplate<String, NotificationDto> kafkaTemplate;

    @Mock
    private UserNotificationRepository userNotificationRepository;

    @Test
    void testCreateNotificationWhenAllRequiredParamArePassedExpectNotificationDto() {
        // given
        Mockito.when(userNotificationRepository.save(Mockito.any(UserNotificationEntity.class))).thenReturn(
            getUserNotificationEntity());
        // when
        NotificationDto notificationDto = service.createNotification(getNotificationDto());
        // then
        Assertions.assertNotNull(notificationDto.getUserNotification());
        Assertions.assertEquals(1L, notificationDto.getId().longValue());
        Assertions.assertEquals("Get new credit card", notificationDto.getTitle());
        Assertions.assertEquals("Get new credit card", notificationDto.getTitle());
        Assertions.assertEquals("Amazon offers new credit card for the customers", notificationDto.getContent());
        Assertions.assertEquals(1L, notificationDto.getUserNotification().getToUser().longValue());
        Assertions.assertEquals(2L, notificationDto.getUserNotification().getFromUser().longValue());
        Assertions.assertEquals("USER", notificationDto.getCreatedBy());
        Assertions.assertTrue(notificationDto.isHidden());
        Assertions.assertFalse(notificationDto.isRead());
        Mockito.verify(userNotificationRepository, Mockito.times(1)).save(Mockito.any(UserNotificationEntity.class));
    }

    @Test
    void testPublishNotificationWhenAllRequiredParamsArePassed() {
        // given
        ReflectionTestUtils.setField(service, "topicName", "user.notification");
        SettableListenableFuture<SendResult<String, NotificationDto>> future = new SettableListenableFuture<>();
        Mockito.when(kafkaTemplate.send(eq("user.notification"), eq(getNotificationDto()))).thenReturn(future);
        // when
        service.publishNotification(getNotificationDto());
        // then
        Mockito.verify(kafkaTemplate, Mockito.times(1)).send(eq("user.notification"), eq(getNotificationDto()));

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
