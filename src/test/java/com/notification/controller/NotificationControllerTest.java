package com.notification.controller;

import com.notification.dto.NotificationDto;
import com.notification.dto.UserNotificationDto;
import com.notification.service.NotificationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @InjectMocks
    private NotificationController notificationController;

    @Mock
    private NotificationService notificationService;

    @Test
    void testGetNotificationsWhenPassedRequiredParamExpectNotificationList() {
        // given
        Mockito.when(notificationService.getAllNotification(eq(1), eq(1), eq("id"))).thenReturn(
            Collections.singletonList(getNotificationDto()));
        // when
        ResponseEntity<List<NotificationDto>> notificationDtos = notificationController.getNotifications(1, 1, "id");
        // then
        Assertions.assertNotNull(notificationDtos.getBody());
        Assertions.assertEquals(HttpStatus.OK, notificationDtos.getStatusCode());
        Assertions.assertNotNull(notificationDtos.getBody().get(0).getUserNotification());
        Assertions.assertEquals(1L, notificationDtos.getBody().get(0).getId().longValue());
        Assertions.assertEquals("Get new credit card", notificationDtos.getBody().get(0).getTitle());
        Assertions.assertEquals("Get new credit card", notificationDtos.getBody().get(0).getTitle());
        Assertions.assertEquals("Amazon offers new credit card for the customers",
            notificationDtos.getBody().get(0).getContent());
        Assertions.assertEquals(1L, notificationDtos.getBody().get(0).getUserNotification().getToUser().longValue());
        Assertions.assertEquals(2L, notificationDtos.getBody().get(0).getUserNotification().getFromUser().longValue());
        Assertions.assertEquals("USER", notificationDtos.getBody().get(0).getCreatedBy());
        Assertions.assertTrue(notificationDtos.getBody().get(0).isHidden());
        Assertions.assertFalse(notificationDtos.getBody().get(0).isRead());
        Mockito.verify(notificationService, Mockito.times(1)).getAllNotification(eq(1), eq(1), eq("id"));
    }

    @Test
    void testGetNotificationsWhenPassedRequiredParamExpectEmptyList() {
        // given
        Mockito.when(notificationService.getAllNotification(eq(1), eq(1), eq("id"))).thenReturn(
            Collections.emptyList());
        // when
        ResponseEntity<List<NotificationDto>> notificationDtos = notificationController.getNotifications(1, 1, "id");
        // then
        Assertions.assertNotNull(notificationDtos.getBody());
        Assertions.assertEquals(HttpStatus.OK, notificationDtos.getStatusCode());
        Mockito.verify(notificationService, Mockito.times(1)).getAllNotification(eq(1), eq(1), eq("id"));
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

}
