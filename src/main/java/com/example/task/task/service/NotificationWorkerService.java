package com.example.task.task.service;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.task.task.entity.NotificationStatus;
import com.example.task.task.entity.Recipient;
import com.example.task.task.repository.RecipientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationWorkerService {

    private final RecipientRepository recipientRepo;
    private final NotificationService notificationService;

    @Scheduled(fixedDelay = 1000)
    public void processPendingNotifications() {

        List<Recipient> pending = recipientRepo.findTop100ByStatus(
                NotificationStatus.PENDING);

        int sent = 0;

        for (Recipient recipient : pending) {

            if (sent >= 100) {
                break;
            }

            notificationService.sendNotification(recipient);

            sent++;
        }
    }
}