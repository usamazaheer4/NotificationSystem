package com.notification.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Data
public class NotificationDto {
    private Long id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Content is mandatory")
    private String content;

    private Timestamp createDateTime;

    private boolean isRead;

    private boolean isHidden;

    private UserNotificationDto userNotification;

    private String createdBy;
}
