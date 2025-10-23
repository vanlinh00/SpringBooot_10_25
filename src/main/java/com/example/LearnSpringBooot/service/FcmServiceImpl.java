package com.example.LearnSpringBooot.service;

import com.example.LearnSpringBooot.model.NotificationMessage;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class FcmServiceImpl implements FcmService {

    private static final Logger logger = LoggerFactory.getLogger(FcmServiceImpl.class);

    private final FirebaseMessaging firebaseMessaging;

    public FcmServiceImpl(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    @Override
    public String sendNotificationByToken(NotificationMessage notificationMessage) {

        Notification notification = Notification.builder()
                .setTitle(notificationMessage.getTitle())
                .setBody(notificationMessage.getBody())
                .setImage(notificationMessage.getImage() != null ? notificationMessage.getImage() : null)
                .build();

        Message message = Message.builder()
                .setToken(notificationMessage.getRecipientToken())
                .setNotification(notification)
                .putAllData(notificationMessage.getData() != null ? notificationMessage.getData() : Collections.emptyMap())                .build();

        try {
            String messageId = firebaseMessaging.send(message);
            logger.info("Successfully sent notification. Message ID: {}", messageId);

            return "Success Sending Notification. Message ID: " + messageId;
        } catch (FirebaseMessagingException e) {
            logger.error("Error sending notification to token: {}. Error: {}",
                    notificationMessage.getRecipientToken(), e.getMessage(), e);
            throw new RuntimeException("Failed to send notification via FCM.", e);
        }
    }

    @Override
    public String sendNotificationByTopic(NotificationMessage notificationMessage) {

        Notification notification = Notification.builder()
                .setTitle(notificationMessage.getTitle())
                .setBody(notificationMessage.getBody())
                .setImage(notificationMessage.getImage() != null ? notificationMessage.getImage() : null)
                .build();

        // The key difference: use setTopic instead of setToken
        Message message = Message.builder()
                .setTopic(notificationMessage.getRecipientToken()) // Assuming recipientToken holds the topic name
                .setNotification(notification)
                .putAllData(notificationMessage.getData() != null ? notificationMessage.getData() : Collections.emptyMap())
                .build();

        try {
            String messageId = firebaseMessaging.send(message);
            logger.info("Successfully sent topic notification. Message ID: {}", messageId);

            return "Success Sending Topic Notification. Message ID: " + messageId;
        } catch (FirebaseMessagingException e) {
            logger.error("Error sending topic notification to {}. Error: {}",
                    notificationMessage.getRecipientToken(), e.getMessage(), e);
            throw new RuntimeException("Failed to send topic notification via FCM.", e);
        }
    }
}