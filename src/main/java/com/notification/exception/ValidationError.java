package com.notification.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ValidationError {
    private List<ErrorResponse> errorResponses = new ArrayList<>();
}
