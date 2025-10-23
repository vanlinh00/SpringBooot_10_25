package com.example.LearnSpringBooot.controller;

import com.example.LearnSpringBooot.Utils.AppConstants;
import com.example.LearnSpringBooot.model.NotificationMessage;
import com.example.LearnSpringBooot.service.FcmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppConstants.NOTIFICATION_ENDPOINT)
public class NotificationController {

    private final FcmService fcmService;

    public NotificationController(FcmService fcmService) {
        this.fcmService = fcmService;
    }

    @PostMapping
    public ResponseEntity<String> sendNotificationByToken(
            @RequestBody NotificationMessage notificationMessage) {
        try {
            String result = fcmService.sendNotificationByToken(notificationMessage);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error Sending Notification: " + e.getMessage());
        }
    }

    @PostMapping("/topic")
    public ResponseEntity<String> sendNotificationByTopic(
            @RequestBody NotificationMessage notificationMessage) {
        try {
            // Note: notificationMessage.getRecipientToken() will be used as the topic name
            String result = fcmService.sendNotificationByTopic(notificationMessage);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error Sending Topic Notification: " + e.getMessage());
        }
    }


}