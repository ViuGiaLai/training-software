package com.viusoftware.training.training_system.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {
    @GetMapping("/sv/student")
    public String studentDashboard() {
        return "sv/student";
    }
}
