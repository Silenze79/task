package com.example.task.task.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final NotificationService notificationService;

    @Scheduled(fixedDelay = 2000)
    public void run() {
        notificationService.processPendingNotifications();
    }
}
