package com.notification.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class UserNotificationDto {
    private Long id;

    @Min(value=1, message="must be equal or greater than 1")
    private Long fromUser;

    @Min(value=1, message="must be equal or greater than 1")
    private Long toUser;

}
