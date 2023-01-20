package com.notification.controller;

import com.notification.dto.NotificationDto;
import com.notification.dto.UserNotificationDto;
import com.notification.service.UserNotificationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class UserNotificationControllerTest {

    @InjectMocks
    private UserNotificationController userNotificationController;

    @Mock
    private UserNotificationService userNotificationService;

    @Test
    void testCreateNotification() {
        // given
        Mockito.when(userNotificationService.createNotification(eq(getNotificationDto()))).thenReturn(
            getNotificationDto());
        // when
        ResponseEntity<NotificationDto> response = userNotificationController.createNotification(getNotificationDto());
        // then
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody().getUserNotification());
        Assertions.assertEquals(1L, response.getBody().getId().longValue());
        Assertions.assertEquals("Get new credit card", response.getBody().getTitle());
        Assertions.assertEquals("Get new credit card", response.getBody().getTitle());
        Assertions.assertEquals("Amazon offers new credit card for the customers", response.getBody().getContent());
        Assertions.assertEquals(1L, response.getBody().getUserNotification().getToUser().longValue());
        Assertions.assertEquals(2L, response.getBody().getUserNotification().getFromUser().longValue());
        Assertions.assertEquals("USER", response.getBody().getCreatedBy());
        Assertions.assertTrue(response.getBody().isHidden());
        Assertions.assertFalse(response.getBody().isRead());
        Mockito.verify(userNotificationService, Mockito.times(1)).createNotification(eq(getNotificationDto()));
    }

    @Test
    void testPublishNotification() {
        // given
        Mockito.doNothing().when(userNotificationService).publishNotification(eq(getNotificationDto()));
        // when
        userNotificationController.publishNotification(getNotificationDto());
        // then
        Mockito.verify(userNotificationService, Mockito.times(1)).publishNotification(eq(getNotificationDto()));
    }

    @Test
    void testPublishNotificationWhenRequiredFieldsAreEmptyThrowException() {
        NotificationDto notificationDto = getNotificationDto();
        notificationDto.setTitle("");
        // given
        Mockito.doThrow(new NullPointerException("Title is missing")).when(userNotificationService).publishNotification(
            eq(notificationDto));
        // when, then
        Assertions.assertThrows(Exception.class,
            () -> userNotificationController.publishNotification(getNotificationDto()));

    }

    @Test
    void testCreateNotificationWhenRequiredFieldsAreEmptyThrowException() {
        NotificationDto notificationDto = getNotificationDto();
        notificationDto.setContent("");
        // given
        Mockito.when(userNotificationService.createNotification(eq(notificationDto))).thenThrow(
            new NullPointerException("content is empty"));
        // when, then
        Assertions.assertThrows(NullPointerException.class,
            () -> userNotificationController.createNotification(notificationDto));

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
