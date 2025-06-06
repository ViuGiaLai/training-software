package com.viusoftware.training.training_system.controllers;

import com.viusoftware.training.training_system.entity.ExamSchedule;
import com.viusoftware.training.training_system.repository.ExamManagementRepository;
import com.viusoftware.training.training_system.repository.ClassRoomRepository;
import com.viusoftware.training.training_system.repository.RoomRepository;
import com.viusoftware.training.training_system.repository.SubjectRepository;
import com.viusoftware.training.training_system.entity.ClassRoom;
import com.viusoftware.training.training_system.entity.Room;
import com.viusoftware.training.training_system.entity.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
public class ExamManagementController {
    private final ExamManagementRepository examManagementRepository;
    private final ClassRoomRepository classRoomRepository;
    private final RoomRepository roomRepository;
    private final SubjectRepository subjectRepository;

    public ExamManagementController(
        ExamManagementRepository examManagementRepository,
        ClassRoomRepository classRoomRepository,
        RoomRepository roomRepository,
        SubjectRepository subjectRepository
    ) {
        this.examManagementRepository = examManagementRepository;
        this.classRoomRepository = classRoomRepository;
        this.roomRepository = roomRepository;
        this.subjectRepository = subjectRepository;
    }

    @GetMapping("/admin/dashboard/exam-management")
    public String examManagement(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String subjectFilter,
            @RequestParam(required = false) String classFilter,
            @RequestParam(required = false) String roomFilter,
            Model model) {

        List<ExamSchedule> examSchedules;

        if (search != null && !search.isBlank()) {
            String keyword = search.trim().toLowerCase();
            examSchedules = examManagementRepository.findAll().stream()
                .filter(e ->
                    (e.getSubject() != null && e.getSubject().toLowerCase().contains(keyword)) ||
                    (e.getClasses() != null && e.getClasses().toLowerCase().contains(keyword)) ||
                    (e.getRooms() != null && e.getRooms().toLowerCase().contains(keyword))
                )
                .toList();
        } else {
            examSchedules = examManagementRepository.findAll();
            if (subjectFilter != null && !subjectFilter.isEmpty()) {
                examSchedules = examSchedules.stream()
                    .filter(e -> e.getSubject() != null && e.getSubject().equals(subjectFilter))
                    .toList();
            }
            if (classFilter != null && !classFilter.isEmpty()) {
                examSchedules = examSchedules.stream()
                    .filter(e -> e.getClasses() != null && e.getClasses().equals(classFilter))
                    .toList();
            }
            if (roomFilter != null && !roomFilter.isEmpty()) {
                examSchedules = examSchedules.stream()
                    .filter(e -> e.getRooms() != null && e.getRooms().equals(roomFilter))
                    .toList();
            }
        }

        // Lấy danh sách lớp, phòng, môn từ DB
        List<ClassRoom> classRooms = classRoomRepository.findAll();
        List<Room> rooms = roomRepository.findAll();
        List<Subject> subjects = subjectRepository.findAll();

        model.addAttribute("examSchedules", examSchedules);
        model.addAttribute("totalExams", examSchedules.size());
        model.addAttribute("classRooms", classRooms);
        model.addAttribute("rooms", rooms);
        model.addAttribute("subjects", subjects);
        model.addAttribute("subjectFilter", subjectFilter);
        model.addAttribute("classFilter", classFilter);
        model.addAttribute("roomFilter", roomFilter);
        model.addAttribute("search", search);

        return "dashboard/exam-management";
    }

    @PostMapping("/admin/dashboard/exam-management/add")
    public String addExam(
            @RequestParam String subject,
            @RequestParam String classes,
            @RequestParam String rooms,
            @RequestParam String date,
            @RequestParam String startTime,
            @RequestParam String endTime,
            @RequestParam String status
    ) {
        ExamSchedule exam = new ExamSchedule();
        exam.setSubject(subject);
        exam.setClasses(classes);
        exam.setRooms(rooms);

        // Gộp ngày và giờ thành LocalDateTime
        LocalDate examDate = LocalDate.parse(date);
        LocalTime start = LocalTime.parse(startTime);
        LocalTime end = LocalTime.parse(endTime);
        exam.setStartTime(LocalDateTime.of(examDate, start));
        exam.setEndTime(LocalDateTime.of(examDate, end));

        // Lấy số thí sinh theo lớp (tránh lazy loading)
        int studentCount = classRoomRepository.countStudentsByClassName(classes);
        exam.setNumberOfStudents(studentCount);

        exam.setStatus(status);
        examManagementRepository.save(exam);
        return "redirect:/admin/dashboard/exam-management";
    }

    @PostMapping("/admin/dashboard/exam-management/edit")
    public String editExam(
            @RequestParam Long id,
            @RequestParam String subject,
            @RequestParam String classes,
            @RequestParam String rooms,
            @RequestParam String date,
            @RequestParam String startTime,
            @RequestParam String endTime,
            @RequestParam String status
    ) {
        ExamSchedule exam = examManagementRepository.findById(id).orElse(null);
        if (exam != null) {
            exam.setSubject(subject);
            exam.setClasses(classes);
            exam.setRooms(rooms);

            LocalDate examDate = LocalDate.parse(date);
            LocalTime start = LocalTime.parse(startTime);
            LocalTime end = LocalTime.parse(endTime);
            exam.setStartTime(LocalDateTime.of(examDate, start));
            exam.setEndTime(LocalDateTime.of(examDate, end));

            int studentCount = classRoomRepository.countStudentsByClassName(classes);
            exam.setNumberOfStudents(studentCount);

            exam.setStatus(status);
            examManagementRepository.save(exam);
        }
        return "redirect:/admin/dashboard/exam-management";
    }
}
