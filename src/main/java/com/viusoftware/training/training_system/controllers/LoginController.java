package com.viusoftware.training.training_system.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    
    @GetMapping("/training-login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/training-login";
    }
}
