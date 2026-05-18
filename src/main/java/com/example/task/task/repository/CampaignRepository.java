package com.example.task.task.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.task.task.entity.Campaign;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, UUID> {

}

