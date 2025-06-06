package com.viusoftware.training.training_system.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.viusoftware.training.training_system.repository.UsersStudentsRepository;
import com.viusoftware.training.training_system.entity.UsersStudents;
import java.security.Principal;

@Controller
public class StudentController {

    @Autowired
    private UsersStudentsRepository usersStudentsRepository;

    @GetMapping("/sv/student")
    public String studentDashboard(Model model, Principal principal) {
        // Lấy username đăng nhập hiện tại
        String username = principal != null ? principal.getName() : null;
        UsersStudents student = null;
        if (username != null) {
            student = usersStudentsRepository.findByUsername(username);
        }
        model.addAttribute("student", student);
        return "sv/student";
    }
}
