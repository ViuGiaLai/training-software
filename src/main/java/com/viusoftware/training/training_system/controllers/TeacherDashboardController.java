package com.viusoftware.training.training_system.controllers;

import com.viusoftware.training.training_system.entity.UsersTeachers;
import com.viusoftware.training.training_system.entity.ClassRoom;
import com.viusoftware.training.training_system.entity.UsersStudents;
import com.viusoftware.training.training_system.repository.UsersTeachersRepository;
import com.viusoftware.training.training_system.repository.ClassRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Collections;

@Controller
public class TeacherDashboardController {

    @Autowired
    private UsersTeachersRepository usersTeachersRepository;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Transactional(readOnly = true)
    @GetMapping("/gv/teacher")
    public String teacherDashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : null;
        UsersTeachers teacher = usersTeachersRepository.findByUsername(username);

        ClassRoom classRoom = null;
        List<UsersStudents> students = Collections.emptyList();
        if (teacher != null) {
            classRoom = classRoomRepository.findAll().stream()
                .filter(c -> c.getHomeroomTeacher() != null && c.getHomeroomTeacher().getId().equals(teacher.getId()))
                .findFirst().orElse(null);
            if (classRoom != null && classRoom.getStudents() != null) {
                students = classRoom.getStudents();
            }
        }
        model.addAttribute("classRoom", classRoom);
        model.addAttribute("students", students);
        model.addAttribute("contentFragment", "gv/teacher-home");
        return "gv/teacher";
    }

    @Transactional(readOnly = true)
    @GetMapping("/gv/teacher/fragment")
    public String teacherHomeFragment(Model model) {
        // Lấy lại dữ liệu như trên nếu cần
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : null;
        UsersTeachers teacher = usersTeachersRepository.findByUsername(username);

        ClassRoom classRoom = null;
        List<UsersStudents> students = Collections.emptyList();
        if (teacher != null) {
            classRoom = classRoomRepository.findAll().stream()
                .filter(c -> c.getHomeroomTeacher() != null && c.getHomeroomTeacher().getId().equals(teacher.getId()))
                .findFirst().orElse(null);
            if (classRoom != null && classRoom.getStudents() != null) {
                students = classRoom.getStudents();
            }
        }
        model.addAttribute("classRoom", classRoom);
        model.addAttribute("students", students);
        return "gv/teacher-home :: content";
    }
}
