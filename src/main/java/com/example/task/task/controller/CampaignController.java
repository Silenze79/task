package com.example.task.task.controller;

// import java.util.UUID;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.task.task.entity.Campaign;
import com.example.task.task.repository.CampaignRepository;

@RestController
@RequestMapping("/campaigns")
public class CampaignController {
    private final CampaignRepository campaignRepository;
    public CampaignController(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }
    @GetMapping
    public Iterable<Campaign> getCampaigns() {
        return campaignRepository.findAll();
    }
    @PostMapping
    public Campaign postCampaign(@RequestBody Campaign campaign) {
        return campaignRepository.save(campaign);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Campaign> getCampaign(@PathVariable UUID id) {
        var campaign = campaignRepository.findById(id).orElse(null);
        if (campaign == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(campaign);
    }
    // @PostMapping("/campaigns/{id}/retry-failures")
    // public String postRetryFailures(@PathVariable UUID id, @RequestBody String entity) {
    //     //TODO: process POST request
        
    //     return entity;
    // }
    
}
