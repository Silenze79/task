package com.example.task.task.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.task.task.entity.Campaign;
import com.example.task.task.entity.NotificationStatus;
import com.example.task.task.entity.Recipient;
import com.example.task.task.repository.CampaignRepository;
import com.example.task.task.repository.RecipientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CampaignService {

    private final CampaignRepository campaignRepo;
    private final RecipientRepository recipientRepo;
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

       csvService.parseCsv(file,recipient -> {recipient.setCampaign(savedCampaign);recipientRepo.save(recipient);});

        // No processCampaign() call needed

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

        List<Recipient> failedRecipients =
                recipientRepo.findByCampaign_CampaignIdAndStatus(campaignId, NotificationStatus.FAILED);

        // reset FAILED → PENDING
        failedRecipients.forEach(recipient ->
                recipient.setStatus(
                        NotificationStatus.PENDING
                ));

        recipientRepo.saveAll(failedRecipients);

        campaign.setStatus(
                Campaign.Status.PENDING
        );

        campaignRepo.save(campaign);
    }
}