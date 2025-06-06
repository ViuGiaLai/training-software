package com.viusoftware.training.training_system.controllers;

import com.viusoftware.training.training_system.service.UserService;
import com.viusoftware.training.training_system.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import com.viusoftware.training.training_system.entity.UsersAdmin;
import java.security.Principal;
import org.springframework.security.core.Authentication;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final FileStorageService fileStorageService;

    @Autowired
    public AdminController(UserService userService, FileStorageService fileStorageService) {
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/dashboard")
    public String showAdminDashboard(Model model, Principal principal) {
        String username = principal.getName();
        UsersAdmin currentUser = userService.findAdminByUsername(username);
        model.addAttribute("currentUser", currentUser);
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

    @PostMapping(value = "/create-user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("role") String role,
            @RequestParam("email") String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            Model model) {

        String imageUrl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            // TODO: Implement image file saving logic here
            // For now, let's assume a placeholder URL
            // imageUrl = "/images/" + imageFile.getOriginalFilename(); // Example URL
            try {
                imageUrl = fileStorageService.storeFile(imageFile); // Use FileStorageService to store file
            } catch (RuntimeException e) {
                 model.addAttribute("errorMessage", "Failed to upload image: " + e.getMessage());
                 // Keep the entered values in case of error
                 model.addAttribute("username", username);
                 model.addAttribute("email", email);
                 model.addAttribute("phone", phone);
                 // Optionally, add back other model attributes needed for the form
                 return "dashboard/create-user";
            }
        }

        try {
            userService.createUser(username, password, role, email, phone, imageUrl);
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

    @GetMapping("/users/{username}")
    @ResponseBody
    public UsersAdmin getUserByUsername(@PathVariable("username") String username) {
        // TODO: Implement logic in UserService to find user by username
        return userService.findAdminByUsername(username); // Assuming this method exists in UserService
    }

    // New endpoint to update admin user profile
    @PostMapping(value = "/update-profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody // Indicate that the return value should be the response body
    public String updateAdminProfile(
            @RequestParam("id") Long id,
            @RequestParam("email") String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            Principal principal) { // To verify the user is updating their own profile

        String username = principal.getName();
        // Optional: Add verification to ensure the user ID being updated matches the authenticated user
        UsersAdmin currentUser = userService.findAdminByUsername(username);
        if (currentUser == null || !currentUser.getId().equals(id)) {
             // Or throw an exception, depending on desired error handling
             return "Error: Unauthorized to update this profile.";
        }

        String imageUrl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                imageUrl = fileStorageService.storeFile(imageFile); // Store the new image
            } catch (RuntimeException e) {
                 return "Error uploading image: " + e.getMessage();
            }
        }

        try {
            // TODO: Implement logic in UserService to update admin user
            userService.updateAdminUser(id, email, phone, imageUrl); // Assuming this method exists in UserService
            return "Profile updated successfully!";
        } catch (Exception e) {
            // Log the error properly in a real application
            return "Error updating profile: " + e.getMessage();
        }
    }

    // You might want to add more admin related endpoints here (e.g., view users, edit users, delete users)
} 