package com.example.task.task.service;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.example.task.task.entity.NotificationStatus;
import com.example.task.task.entity.Tenant;
import com.example.task.task.repository.TenantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final TenantRepository tenantRepo;

    private static final int MAX_RETRIES = 3;

    public void sendNotification(
            Tenant tenant
    ) {

        if (tenant.getStatus()
                == NotificationStatus.SENT) {

            return;
        }

        tenant.setStatus(
                NotificationStatus.PROCESSING
        );

        tenantRepo.save(tenant);

        try {

            simulateProvider();

            tenant.setStatus(
                    NotificationStatus.SENT
            );

        } catch (Exception e) {

            Integer retries =
                    tenant.getRetryCount();

            retries = retries == null
                    ? 1
                    : retries + 1;

            tenant.setRetryCount(
                    retries
            );

            if (retries >= MAX_RETRIES) {

                tenant.setStatus(
                        NotificationStatus.FAILED
                );

            } else {

                tenant.setStatus(
                        NotificationStatus.PENDING
                );

                exponentialBackoff(
                        retries
                );
            }
        }

        tenantRepo.save(
                tenant
        );
    }

    private void simulateProvider()
            throws Exception {

        int latency =
                ThreadLocalRandom.current()
                        .nextInt(50, 201);

        Thread.sleep(latency);

        double failure =
                ThreadLocalRandom.current()
                        .nextDouble();

        if (failure < 0.20) {

            throw new Exception(
                    "Provider failed"
            );
        }
    }

    private void exponentialBackoff(
            int retryCount
    ) {

        try {

            long wait =
                    (long) Math.pow(
                            2,
                            retryCount
                    ) * 1000;

            Thread.sleep(wait);

        } catch (
                InterruptedException e
        ) {

            Thread.currentThread()
                    .interrupt();
        }
    }
}