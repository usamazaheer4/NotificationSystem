package com.notification.controller;

import com.notification.dto.NotificationDto;
import com.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    /**
     * Gets list of  notifications from the database.
     *
     * @param pageNo the page no
     * @param pageSize the page size
     * @param sortId the sort id like id
     * @return the list of notifications
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<NotificationDto>> getNotifications(@RequestParam(defaultValue = "0") Integer pageNo,
        @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortId) {
        List<NotificationDto> notificationDtos = notificationService.getAllNotification(pageNo, pageSize, sortId);
        return new ResponseEntity<>(notificationDtos, new HttpHeaders(), HttpStatus.OK);
    }

}
