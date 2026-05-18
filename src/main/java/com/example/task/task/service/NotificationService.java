package com.example.task.task.service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.task.task.entity.NotificationStatus;
import com.example.task.task.entity.Recipient;
import com.example.task.task.repository.RecipientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final RecipientRepository recipientRepository;

    // Runs every 5 seconds
    @Scheduled(fixedDelay = 5000)
    @Async
    public void processPendingNotifications() {

        List<Recipient> pendingRecipients =
                recipientRepository.findByStatus(NotificationStatus.PENDING);

        for (Recipient recipient : pendingRecipients) {

            try {

                // Step 1: PROCESSING
                recipient.setStatus(
                        NotificationStatus.PROCESSING
                );
                recipientRepository.save(recipient);

                // Step 2: simulate latency (50–200ms)
                Thread.sleep(
                        ThreadLocalRandom.current()
                                .nextInt(50, 201)
                );

                // Step 3: simulate failure rate (20%)
                if (ThreadLocalRandom.current()
                        .nextDouble() < 0.2) {

                    throw new RuntimeException(
                            "Provider failed"
                    );
                }

                // Step 4: SENT
                recipient.setStatus(
                        NotificationStatus.SENT
                );

            } catch (Exception e) {

                // FAILED
                recipient.setStatus(
                        NotificationStatus.FAILED
                );
            }

            recipientRepository.save(recipient);
        }
    }
}