package com.example.task.task.service;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.example.task.task.entity.NotificationStatus;
import com.example.task.task.entity.Recipient;
import com.example.task.task.repository.RecipientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final RecipientRepository recipientRepo;

    private static final int MAX_RETRIES = 3;

    public void sendNotification(
            Recipient recipient
    ) {

        if (recipient.getStatus()
                == NotificationStatus.SENT) {

            return;
        }

        recipient.setStatus(
                NotificationStatus.PROCESSING
        );

        recipientRepo.save(recipient);

        try {

            simulateProvider();

            recipient.setStatus(
                    NotificationStatus.SENT
            );

        } catch (Exception e) {

            Integer retries =
                    recipient.getRetryCount();

            retries = retries == null
                    ? 1
                    : retries + 1;

            recipient.setRetryCount(
                    retries
            );

            if (retries >= MAX_RETRIES) {

                recipient.setStatus(
                        NotificationStatus.FAILED
                );

            } else {

                recipient.setStatus(
                        NotificationStatus.PENDING
                );

                exponentialBackoff(
                        retries
                );
            }
        }

        recipientRepo.save(
                recipient
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