package com.example.task.task.mappers;
import com.example.task.task.dtos.CampaignDto;
import com.example.task.task.entity.Campaign;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CampaignMapper {
    CampaignDto toDto(Campaign campaign);
}
