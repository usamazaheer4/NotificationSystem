package com.notification.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class NotificationException {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationError onConstraintValidationException(ConstraintViolationException e) {
        ValidationError validationError = new ValidationError();
        e.getConstraintViolations().forEach(error -> validationError.getErrorResponses()
            .add(new ErrorResponse(error.getPropertyPath().toString(), error.getMessage())));
        return validationError;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationError onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ValidationError validationError = new ValidationError();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> validationError.getErrorResponses()
            .add(new ErrorResponse(fieldError.getField(), fieldError.getDefaultMessage())));
        return validationError;
    }

}
