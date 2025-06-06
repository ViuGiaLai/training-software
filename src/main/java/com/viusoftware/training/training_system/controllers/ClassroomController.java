package com.viusoftware.training.training_system.controllers;

import com.viusoftware.training.training_system.entity.UsersTeachers;
import com.viusoftware.training.training_system.entity.ClassRoom;
import com.viusoftware.training.training_system.entity.UsersStudents;
import com.viusoftware.training.training_system.repository.UsersTeachersRepository;
import com.viusoftware.training.training_system.repository.ClassRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

@Controller
public class ClassroomController {

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private UsersTeachersRepository usersTeachersRepository;

    @GetMapping("/admin/dashboard/classroom")
    public String classroomPage(
            Model model,
            @RequestParam(required = false) String grade,
            @RequestParam(required = false) String academicYear,
            @RequestParam(required = false) Long teacherId
    ) {
        List<ClassRoom> classRooms = classRoomRepository.findAllWithStudents();
        if (grade != null && !grade.isBlank()) {
            classRooms = classRooms.stream()
                .filter(c -> grade.equals(c.getGrade() != null ? c.getGrade() : ""))
                .collect(Collectors.toList());
        }
        if (academicYear != null && !academicYear.isBlank()) {
            classRooms = classRooms.stream()
                .filter(c -> academicYear.equals(c.getAcademicYear() != null ? c.getAcademicYear() : ""))
                .collect(Collectors.toList());
        }
        if (teacherId != null) {
            classRooms = classRooms.stream()
                .filter(c -> c.getHomeroomTeacher() != null && teacherId.equals(c.getHomeroomTeacher().getId()))
                .collect(Collectors.toList());
        }
        // Chuẩn bị map classId -> sorted students
        Map<Long, List<UsersStudents>> sortedStudentsMap = new HashMap<>();
        for (ClassRoom cl : classRooms) {
            List<UsersStudents> sortedStudents = classRoomRepository.findStudentsByClassRoomOrderByFullName(cl.getId());
            sortedStudentsMap.put(cl.getId(), sortedStudents);
        }

        List<String> grades = classRoomRepository.findDistinctGrades();
        List<String> academicYears = classRoomRepository.findDistinctAcademicYears();
        List<UsersTeachers> teachers = usersTeachersRepository.findAll();

        model.addAttribute("classRooms", classRooms);
        model.addAttribute("sortedStudentsMap", sortedStudentsMap);
        model.addAttribute("grades", grades);
        model.addAttribute("academicYears", academicYears);
        model.addAttribute("teachers", teachers);
        model.addAttribute("selectedGrade", grade);
        model.addAttribute("selectedYear", academicYear);
        model.addAttribute("selectedTeacherId", teacherId);

        return "dashboard/classroom";
    }

    @PostMapping("/admin/dashboard/classroom/add")
    public String addClassRoom(
            @RequestParam String code,
            @RequestParam String name,
            @RequestParam String grade,
            @RequestParam String academicYear,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Long homeroomTeacherId,
            RedirectAttributes redirectAttributes
    ) {
        try {
            ClassRoom classRoom = new ClassRoom();
            classRoom.setCode(code);
            classRoom.setName(name);
            classRoom.setGrade(grade);
            classRoom.setAcademicYear(academicYear);
            classRoom.setDescription(description);
            if (homeroomTeacherId != null) {
                UsersTeachers teacher = usersTeachersRepository.findById(homeroomTeacherId).orElse(null);
                classRoom.setHomeroomTeacher(teacher);
            } else {
                classRoom.setHomeroomTeacher(null);
            }
            classRoomRepository.save(classRoom);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm lớp thành công!");
            return "redirect:/admin/dashboard/classroom";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/admin/dashboard/classroom";
        }
    }

    @PostMapping("/admin/dashboard/classroom/edit")
    public String editClassRoom(
            @RequestParam Long id,
            @RequestParam String code,
            @RequestParam String name,
            @RequestParam String grade,
            @RequestParam String academicYear,
            @RequestParam(required = false) Long homeroomTeacherId,
            @RequestParam(required = false) String description
    ) {
        ClassRoom classRoom = classRoomRepository.findById(id).orElse(null);
        if (classRoom != null) {
            classRoom.setCode(code);
            classRoom.setName(name);
            classRoom.setGrade(grade);
            classRoom.setAcademicYear(academicYear);
            classRoom.setDescription(description);
            if (homeroomTeacherId != null) {
                UsersTeachers teacher = usersTeachersRepository.findById(homeroomTeacherId).orElse(null);
                classRoom.setHomeroomTeacher(teacher);
            } else {
                classRoom.setHomeroomTeacher(null);
            }
            classRoomRepository.save(classRoom);
        }
        return "redirect:/admin/dashboard/classroom";
    }
}
