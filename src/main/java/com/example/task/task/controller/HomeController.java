package com.example.task.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {
    @RequestMapping("/")
    public String index(Model model) {
        return "index";
    }
    // @RequestMapping("/campaign")
    // public String campaign(Model model) {
    //     model.addAttribute("id", "Welcome to the Campaign Page!");
    //     return "campaign";
    // }
    
}
