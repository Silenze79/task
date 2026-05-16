package com.example.task.task.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.task.task.model.Campaign;


@RestController
public class CampaignController {
    @RequestMapping("/campaign")
    public Campaign campaign() {
        return new Campaign("1");
    }
    
}
