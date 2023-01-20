package com.notification.controller;

import com.notification.dto.NotificationDto;
import com.notification.service.UserNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/notification")
@Slf4j
public class UserNotificationController {

    @Autowired
    private UserNotificationService userNotificationService;

    @PostMapping("/create")
    public ResponseEntity<NotificationDto> createNotification(@Valid @RequestBody NotificationDto notificationDto) {
        log.info("UserNotificationController::createNotification() start creating the notification");
        notificationDto = userNotificationService.createNotification(notificationDto);
        log.info("UserNotificationController::createNotification() successfully the notification has been created");
        return new ResponseEntity<>(notificationDto, new HttpHeaders(), HttpStatus.CREATED);
    }

    @PostMapping("/publish")
    public void publishNotification(@Valid @RequestBody NotificationDto notificationDto) {
        log.info("UserNotificationController::publishNotification() start publishing the notification on a queue");
        userNotificationService.publishNotification(notificationDto);
        log.info(
            "UserNotificationController::publishNotification() successfully the notification has been published on a " +
                "queue");
    }
}


