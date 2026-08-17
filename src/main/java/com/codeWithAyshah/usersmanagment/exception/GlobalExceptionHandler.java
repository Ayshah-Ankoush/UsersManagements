package com.codeWithAyshah.usersmanagment.exception;

import com.codeWithAyshah.usersmanagment.notification.SlackNotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    private final SlackNotificationService slackNotificationService;

    public GlobalExceptionHandler(SlackNotificationService slackNotificationService) {
        this.slackNotificationService = slackNotificationService;
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<String> handleException(ResourceNotFoundException e) {
        slackNotificationService.sendMessage(
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

    }
}
