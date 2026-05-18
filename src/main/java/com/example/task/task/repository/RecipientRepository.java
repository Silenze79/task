package com.example.task.task.repository;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.task.task.entity.NotificationStatus;
import com.example.task.task.entity.Recipient;
@Repository
public interface RecipientRepository
        extends JpaRepository<Recipient, Long> {

    List<Recipient>findByCampaign_CampaignIdAndStatus(UUID campaignId, NotificationStatus status);

    List<Recipient> findByStatus(NotificationStatus status);

    List<Recipient> findTop100ByStatus(NotificationStatus status);
}
