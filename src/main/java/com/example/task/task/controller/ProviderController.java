package com.example.task.task.controller;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/provider")
public class ProviderController {

    @PostMapping("/email/send")
    public ResponseEntity<String> sendEmail() {
        return simulateProvider("EMAIL");
    }

    @PostMapping("/sms/send")
    public ResponseEntity<String> sendSms() {
        return simulateProvider("SMS");
    }

    @PostMapping("/push/send")
    public ResponseEntity<String> sendPush() {
        return simulateProvider("PUSH");
    }

    private ResponseEntity<String> simulateProvider(
            String provider
    ) {

        try {

            // simulate latency (50–200ms)
            Thread.sleep(ThreadLocalRandom.current().nextInt(50, 201));

            // simulate failure rate (20%)
            if (ThreadLocalRandom.current()
                    .nextDouble() < 0.2) {

                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(provider+ " provider failed");
            }

            return ResponseEntity.ok(provider + " sent successfully");

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(provider+ " interrupted");
        }
    }
}