package com.viusoftware.training.training_system.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller 
public class UserlistController {
    @GetMapping("/admin/dashboard/user-list")
    public String userList() {
        return "dashboard/users/user-list";
    }
}
