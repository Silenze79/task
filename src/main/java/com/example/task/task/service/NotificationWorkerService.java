package com.example.task.task.service;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.task.task.entity.NotificationStatus;
import com.example.task.task.entity.Tenant;
import com.example.task.task.repository.TenantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationWorkerService {

    private final TenantRepository tenantRepo;
    private final NotificationService notificationService;

    @Scheduled(fixedDelay = 1000)
    public void processPendingNotifications() {

        List<Tenant> pending = tenantRepo.findTop100ByStatus(
                NotificationStatus.PENDING);

        int sent = 0;

        for (Tenant tenant : pending) {

            if (sent >= 100) {
                break;
            }

            notificationService.sendNotification(tenant);

            sent++;
        }
    }
}