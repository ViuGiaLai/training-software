package com.viusoftware.training.training_system.controllers;

import com.viusoftware.training.training_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String showAdminDashboard() {
        return "dashboard/admin"; // Corresponds to templates/dashboard/admin.html
    }

    @GetMapping("/dashboard/dashboard")
    public String showDashboardContent() {
        return "dashboard/dashboard"; // Corresponds to templates/dashboard/dashboard.html
    }

    @GetMapping("/create-user")
    public String showCreateUserForm(Model model) {
        // Add any necessary model attributes for the form (e.g., list of roles)
        return "dashboard/create-user"; // This will render templates/dashboard/create-user.html
    }

    @PostMapping("/create-user")
    public String createUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("role") String role,
            @RequestParam("email") String email,
            @RequestParam(value = "phone", required = false) String phone,
            Model model) {

        try {
            userService.createUser(username, password, role, email, phone);
            model.addAttribute("successMessage", "User created successfully!");
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            // Keep the entered values in case of error
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            model.addAttribute("phone", phone);
            // Optionally, add back other model attributes needed for the form
            return "dashboard/create-user";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            // Keep the entered values in case of error
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            model.addAttribute("phone", phone);
            // Optionally, add back other model attributes needed for the form
            return "dashboard/create-user";
        }

        return "redirect:/admin/create-user"; // Redirect back to the form with success message
    }

    // You might want to add more admin related endpoints here (e.g., view users, edit users, delete users)
} 