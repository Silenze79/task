package com.example.task.task.repository;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.task.task.entity.NotificationStatus;
import com.example.task.task.entity.Tenant;
@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    List<Tenant>findByCampaign_CampaignIdAndStatus(UUID campaignId, NotificationStatus status);

    List<Tenant> findByStatus(NotificationStatus status);

    List<Tenant> findTop100ByStatus(NotificationStatus status);
}
