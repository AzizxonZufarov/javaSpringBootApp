package com.example.demo.controllers;

import com.example.demo.models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

@Controller
public class MainController {
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("name", "index");
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("name", "about");
        return "about";
    }
}