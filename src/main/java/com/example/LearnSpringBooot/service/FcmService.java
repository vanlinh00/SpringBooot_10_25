package com.example.LearnSpringBooot.service;


import com.example.LearnSpringBooot.model.NotificationMessage;

public interface FcmService {

    String sendNotificationByToken(NotificationMessage notificationMessage);

    String sendNotificationByTopic(NotificationMessage notificationMessage);
}