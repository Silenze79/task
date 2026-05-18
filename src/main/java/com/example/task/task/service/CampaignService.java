package com.example.task.task.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.task.task.entity.Campaign;
import com.example.task.task.entity.NotificationStatus;
import com.example.task.task.entity.Tenant;
import com.example.task.task.repository.CampaignRepository;
import com.example.task.task.repository.TenantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CampaignService {

    private final CampaignRepository campaignRepo;
    private final TenantRepository tenantRepo;
    private final CsvService csvService;

    public Campaign createCampaign(
            String name,
            Campaign.Channel channel,
            MultipartFile file
    ) throws Exception {

        Campaign campaign = new Campaign();

        campaign.setName(name);
        campaign.setChannel(channel);
        campaign.setStatus(Campaign.Status.PENDING);
        campaign.setCreatedAt(LocalDateTime.now());

        Campaign savedCampaign =
                campaignRepo.save(campaign);

       csvService.parseCsv(file, tenant -> {tenant.setCampaign(savedCampaign);tenantRepo.save(tenant);});

        return savedCampaign;
    }

    public List<Campaign> getAll() {
        return campaignRepo.findAll();
    }

    public Campaign getById(UUID campaignId) {

        return campaignRepo.findById(campaignId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Campaign not found"
                        ));
    }

    public void retryFailures(UUID campaignId) {

        Campaign campaign =
                campaignRepo.findById(campaignId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Campaign not found"
                                ));

        List<Tenant> failedtenants =
                tenantRepo.findByCampaign_CampaignIdAndStatus(campaignId, NotificationStatus.FAILED);

        // reset FAILED → PENDING
        failedtenants.forEach(tenant ->
                tenant.setStatus(
                        NotificationStatus.PENDING
                ));

        tenantRepo.saveAll(failedtenants);

        campaign.setStatus(Campaign.Status.PENDING);

        campaignRepo.save(campaign);
    }
}