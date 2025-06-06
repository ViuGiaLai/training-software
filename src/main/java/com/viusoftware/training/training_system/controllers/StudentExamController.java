package com.viusoftware.training.training_system.controllers;

import com.viusoftware.training.training_system.entity.UsersStudents;
import com.viusoftware.training.training_system.entity.ExamSchedule;
import com.viusoftware.training.training_system.repository.UsersStudentsRepository;
import com.viusoftware.training.training_system.repository.ExamScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StudentExamController {

    @Autowired
    private UsersStudentsRepository usersStudentsRepository;

    @Autowired
    private ExamScheduleRepository examScheduleRepository;

    @GetMapping("/sv/lich-thi")
    public String studentExamList(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : null;
        UsersStudents student = usersStudentsRepository.findByUsername(username);
        List<ExamSchedule> examSchedules = java.util.Collections.emptyList();
        if (student != null && student.getClassRoom() != null) {
            String className = student.getClassRoom().getName();
            examSchedules = examScheduleRepository.findByClasses(className);
        }
        model.addAttribute("examSchedules", examSchedules);
        return "sv/lich-thi";
    }
}
