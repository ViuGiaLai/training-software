package com.viusoftware.training.training_system.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CourseController {
    @GetMapping("/admin/dashboard/course-management")
    public String courseManagement() {
        return "dashboard/course-management";
    }
}
