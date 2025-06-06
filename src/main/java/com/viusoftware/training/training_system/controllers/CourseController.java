package com.viusoftware.training.training_system.controllers;

import com.viusoftware.training.training_system.entity.Course;
import com.viusoftware.training.training_system.entity.ClassRoom;
import com.viusoftware.training.training_system.entity.UsersTeachers;
import com.viusoftware.training.training_system.repository.CourseRepository;
import com.viusoftware.training.training_system.repository.ClassRoomRepository;
import com.viusoftware.training.training_system.repository.UsersTeachersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ClassRoomRepository classRoomRepository;
    @Autowired
    private UsersTeachersRepository usersTeachersRepository;

    @GetMapping("/admin/dashboard/course-management")
    public String courseList(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        model.addAttribute("classRooms", classRoomRepository.findAll());
        model.addAttribute("teachers", usersTeachersRepository.findAll());
        return "dashboard/course-management";
    }

    @PostMapping("/admin/dashboard/course-management/add")
    public String addCourse(
            @RequestParam String name,
            @RequestParam Long classRoomId,
            @RequestParam Long teacherId,
            @RequestParam(required = false) String description
    ) {
        Course course = new Course();
        course.setName(name);
        course.setClassRoom(classRoomRepository.findById(classRoomId).orElse(null));
        course.setTeacher(usersTeachersRepository.findById(teacherId).orElse(null));
        course.setDescription(description);
        courseRepository.save(course);
        return "redirect:/admin/dashboard/course-management";
    }

    @PostMapping("/admin/dashboard/course-management/edit")
    public String editCourse(
            @RequestParam Long id,
            @RequestParam String name,
            @RequestParam Long classRoomId,
            @RequestParam Long teacherId,
            @RequestParam(required = false) String description
    ) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course != null) {
            course.setName(name);
            course.setClassRoom(classRoomRepository.findById(classRoomId).orElse(null));
            course.setTeacher(usersTeachersRepository.findById(teacherId).orElse(null));
            course.setDescription(description);
            courseRepository.save(course);
        }
        return "redirect:/admin/dashboard/course-management";
    }

    @PostMapping("/admin/dashboard/course-management/delete")
    public String deleteCourse(@RequestParam Long id) {
        courseRepository.deleteById(id);
        return "redirect:/admin/dashboard/course-management";
    }

    @GetMapping("/admin/dashboard/course-management/detail")
    public String courseDetail(@RequestParam Long id, Model model) {
        model.addAttribute("course", courseRepository.findById(id).orElse(null));
        return "dashboard/course-detail";
    }
}
