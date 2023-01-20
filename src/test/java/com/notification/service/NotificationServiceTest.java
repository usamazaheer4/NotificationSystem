package com.notification.service;

import com.notification.dto.NotificationDto;
import com.notification.entity.NotificationEntity;
import com.notification.entity.UserNotificationEntity;
import com.notification.repository.NotificationRepository;
import com.notification.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @InjectMocks
    private NotificationServiceImpl service;

    @Mock
    private NotificationRepository notificationRepository;

    @Test
    void testGetAllNotificationsWhenAllRequiredParamsArePassedExpectNotifications() {
        Pageable paging = PageRequest.of(1, 1, Sort.by("id"));
        Page<NotificationEntity> page = new PageImpl<>(Collections.singletonList(getUserNotificationEntity()), paging,
            1);
        // given
        Mockito.when(notificationRepository.findAll(paging)).thenReturn(page);
        // when
        List<NotificationDto> notificationDtos = service.getAllNotification(1, 1, "id");
        // then
        Assertions.assertFalse(notificationDtos.isEmpty());
        Assertions.assertNotNull(notificationDtos.get(0).getUserNotification());
        Assertions.assertEquals("Get new credit card", notificationDtos.get(0).getTitle());
        Assertions.assertEquals("Get new credit card", notificationDtos.get(0).getTitle());
        Assertions.assertEquals("Amazon offers new credit card for the customers",
            notificationDtos.get(0).getContent());
        Assertions.assertEquals(1L, notificationDtos.get(0).getUserNotification().getToUser().longValue());
        Assertions.assertEquals(2L, notificationDtos.get(0).getUserNotification().getFromUser().longValue());
        Assertions.assertTrue(notificationDtos.get(0).isHidden());
        Assertions.assertFalse(notificationDtos.get(0).isRead());
        Mockito.verify(notificationRepository, Mockito.times(1)).findAll(paging);
    }

    @Test
    void testGetAllNotificationsWhenAllRequiredParamsArePassedExpectEmptyNotificationList() {
        Pageable paging = PageRequest.of(1, 1, Sort.by("id"));
        Page<NotificationEntity> page = new PageImpl<>(Collections.singletonList(getUserNotificationEntity()), paging,
            1);
        // given
        Mockito.when(notificationRepository.findAll(paging)).thenReturn(Page.empty());
        // when
        List<NotificationDto> notificationDtos = service.getAllNotification(1, 1, "id");
        // then
        Assertions.assertTrue(notificationDtos.isEmpty());
        Mockito.verify(notificationRepository, Mockito.times(1)).findAll(paging);
    }

    private NotificationEntity getUserNotificationEntity() {
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
        notificationEntity.setUserNotification(userNotificationEntity);
        return notificationEntity;
    }

}
