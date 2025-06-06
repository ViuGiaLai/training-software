package com.viusoftware.training.training_system.controllers;

import com.viusoftware.training.training_system.entity.UsersTeachers;
import com.viusoftware.training.training_system.entity.ClassRoom;
import com.viusoftware.training.training_system.entity.ClassAssignment;
import com.viusoftware.training.training_system.repository.UsersTeachersRepository;
import com.viusoftware.training.training_system.repository.ClassRoomRepository;
import com.viusoftware.training.training_system.repository.ClassAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ClassAssignmentController {

    @Autowired
    private UsersTeachersRepository usersTeachersRepository;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private ClassAssignmentRepository classAssignmentRepository;

    @GetMapping("/admin/dashboard/class-assignment")
    public String classAssignmentPage(Model model) {
        List<UsersTeachers> teachers = usersTeachersRepository.findAll();
        List<ClassRoom> classRooms = classRoomRepository.findAll();
        List<ClassAssignment> assignments = classAssignmentRepository.findAll();
        model.addAttribute("teachers", teachers);
        model.addAttribute("classRooms", classRooms);
        model.addAttribute("assignments", assignments);
        return "dashboard/class-assignment";
    }
}
