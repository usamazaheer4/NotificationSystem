package com.notification.service;

import com.notification.dto.NotificationDto;

import java.util.List;

public interface NotificationService {

    /**
     * Gets list of  notifications from the database.
     *
     * @param pageNo the page no
     * @param pageSize the page size
     * @param sortId the sort id like id
     * @return the list of notifications dto
     */
    List<NotificationDto> getAllNotification(Integer pageNo, Integer pageSize, String sortId);
}
