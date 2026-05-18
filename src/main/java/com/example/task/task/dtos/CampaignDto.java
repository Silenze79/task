package com.example.task.task.dtos;

import java.util.UUID;

import com.example.task.task.entity.Campaign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignDto {
    private UUID campaignId;
    private String name;
    private Campaign.Status status;
}