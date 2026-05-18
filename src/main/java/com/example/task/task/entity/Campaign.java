package com.example.task.task.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "campaigns")
public class Campaign {

    @Id
    @GeneratedValue
    @Column(name = "campaign_id")
    private UUID campaignId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Channel channel;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public enum Channel {
        EMAIL,
        SMS,
        PUSH_NOTIFICATION
    }

    public enum Status {
        PENDING,
        PROCESSING,
        COMPLETED,
        FAILED
    }

    @PrePersist
    public void prePersist() {

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }

        if (status == null) {
            status = Status.PENDING;
        }
    }
}