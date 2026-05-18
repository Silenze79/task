package com.example.task.task.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.task.task.mappers.CampaignMapper;
import com.example.task.task.dtos.CampaignDto;
import com.example.task.task.entity.Campaign;
import com.example.task.task.repository.CampaignRepository;
import lombok.AllArgsConstructor;
@RestController
@AllArgsConstructor
@RequestMapping("/campaigns")
public class CampaignController {

    private final CampaignRepository campaignRepository;
    private final CampaignMapper campaignMapper;
    

    @GetMapping
    public Iterable<CampaignDto>
    getCampaigns() {

        return campaignRepository.findAll()
                .stream()
                .map(campaignMapper::toDto)
                .toList();
    }

    @PostMapping
    public CampaignDto postCampaign(
            @RequestBody
            CampaignDto campaignDto
    ) {

        Campaign campaign =
                new Campaign();

        campaign.setName(
                campaignDto.getName()
        );

        campaign.setStatus(
                campaignDto.getStatus()
        );

        Campaign savedCampaign =
                campaignRepository
                        .save(campaign);

        return new CampaignDto(
                savedCampaign
                        .getCampaignId(),
                savedCampaign
                        .getName(),
                savedCampaign
                        .getStatus()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampaignDto>
    getCampaign(
            @PathVariable UUID id
    ) {

        var campaign = campaignRepository.findById(id).orElse(null);

        if (campaign == null) {

            return ResponseEntity.notFound().build();
        }

       

        return ResponseEntity.ok(campaignMapper.toDto(campaign));
    }
}