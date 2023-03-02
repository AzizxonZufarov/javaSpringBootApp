package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    public String home(Model model, String name) {
        model.addAttribute("home", name);
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model, String name) {
        model.addAttribute("name", name);
        return "home";
    }

}