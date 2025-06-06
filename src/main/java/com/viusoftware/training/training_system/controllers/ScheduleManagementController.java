package com.viusoftware.training.training_system.controllers;


import com.viusoftware.training.training_system.entity.*;
import com.viusoftware.training.training_system.repository.ClassRoomRepository;
import com.viusoftware.training.training_system.repository.SubjectRepository;
import com.viusoftware.training.training_system.repository.UsersTeachersRepository;
import com.viusoftware.training.training_system.repository.RoomRepository;
import com.viusoftware.training.training_system.repository.PeriodRepository;
import com.viusoftware.training.training_system.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.nio.charset.StandardCharsets;

@Controller
public class ScheduleManagementController {

    @Autowired
    private ClassRoomRepository classRoomRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private UsersTeachersRepository usersTeachersRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private PeriodRepository periodRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    @GetMapping("/admin/dashboard/schedule-management")
    public String scheduleManagementPage(
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) String weekStart, // yyyy-MM-dd
            Model model
    ) {
        List<ClassRoom> classes = classRoomRepository.findAll();
        List<Subject> subjects = subjectRepository.findAll();
        List<UsersTeachers> teachers = usersTeachersRepository.findAll();
        List<Room> rooms = roomRepository.findAll();
        List<Period> periods = periodRepository.findAll();

        // Đảm bảo không có biến nào bị null
        model.addAttribute("classes", classes != null ? classes : java.util.Collections.emptyList());
        model.addAttribute("subjects", subjects != null ? subjects : java.util.Collections.emptyList());
        model.addAttribute("teachers", teachers != null ? teachers : java.util.Collections.emptyList());
        model.addAttribute("rooms", rooms != null ? rooms : java.util.Collections.emptyList());
        model.addAttribute("periods", periods != null ? periods : java.util.Collections.emptyList());

        // ✅ Thêm dòng này:
        model.addAttribute("selectedSubjectId", null);  // hoặc gán giá trị thực nếu có
        // Xác định tuần hiện tại
        LocalDate startDate;
        if (weekStart != null && !weekStart.isBlank()) {
            startDate = LocalDate.parse(weekStart);
        } else {
            LocalDate today = LocalDate.now();
            startDate = today.with(DayOfWeek.MONDAY);
        }
        LocalDate endDate = startDate.plusDays(6);
        model.addAttribute("weekStart", startDate);
        model.addAttribute("weekEnd", endDate);

        List<Schedule> schedules = (classId != null)
                ? scheduleRepository.findByClassRoomIdAndDateBetween(classId, startDate, endDate)
                : java.util.Collections.emptyList();
        // Debug log
        System.out.println("Schedules found: " + schedules.size() + " for classId=" + classId + ", weekStart=" + startDate + ", weekEnd=" + endDate);
        model.addAttribute("selectedClassId", classId);
        model.addAttribute("schedules", schedules);

        return "dashboard/schedule-management";
    }

    @PostMapping("/admin/dashboard/schedule/create")
    public String createSchedule(
            @RequestParam Long classId,
            @RequestParam Long subjectId,
            @RequestParam Long teacherId,
            @RequestParam Long roomId,
            @RequestParam Long periodId,
            @RequestParam String date,
            @RequestParam(required = false) String weekStart // nhận lại tuần để redirect đúng
    ) {
        // Log dữ liệu đầu vào
        System.out.println("Create schedule: classId=" + classId + ", subjectId=" + subjectId + ", teacherId=" + teacherId + ", roomId=" + roomId + ", periodId=" + periodId + ", date=" + date);

        Schedule schedule = new Schedule();
        schedule.setClassRoom(classRoomRepository.findById(classId).orElse(null));
        schedule.setSubject(subjectRepository.findById(subjectId).orElse(null));
        schedule.setTeacher(usersTeachersRepository.findById(teacherId).orElse(null));
        schedule.setRoom(roomRepository.findById(roomId).orElse(null));
        schedule.setPeriod(periodRepository.findById(periodId).orElse(null));
        try {
            schedule.setDate(java.time.LocalDate.parse(date));
        } catch (Exception e) {
            schedule.setDate(null);
        }
        if (schedule.getClassRoom() == null || schedule.getSubject() == null ||
            schedule.getTeacher() == null || schedule.getRoom() == null || schedule.getPeriod() == null || schedule.getDate() == null) {
            System.out.println("Invalid schedule data: " +
                "classRoom=" + schedule.getClassRoom() +
                ", subject=" + schedule.getSubject() +
                ", teacher=" + schedule.getTeacher() +
                ", room=" + schedule.getRoom() +
                ", period=" + schedule.getPeriod() +
                ", date=" + schedule.getDate());
            return "redirect:/admin/dashboard/schedule-management?classId=" + classId + "&error=invalid";
        }
        // Kiểm tra trùng lịch (cùng lớp, cùng ngày, cùng tiết)
        boolean exists = scheduleRepository.existsByClassRoomIdAndDateAndPeriodId(classId, schedule.getDate(), periodId);
        if (exists) {
            System.out.println("Duplicate schedule for classId=" + classId + ", date=" + schedule.getDate() + ", periodId=" + periodId);
            return "redirect:/admin/dashboard/schedule-management?classId=" + classId + "&weekStart=" + (weekStart != null ? weekStart : "") + "&error=duplicate";
        }
        try {
            scheduleRepository.save(schedule);
            System.out.println("Schedule saved successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error saving schedule: " + ex.getMessage());
            return "redirect:/admin/dashboard/schedule-management?classId=" + classId + "&error=save";
        }
        // Redirect về đúng tuần đang xem
        return "redirect:/admin/dashboard/schedule-management?classId=" + classId + (weekStart != null ? "&weekStart=" + weekStart : "");
    }

    @PostMapping("/admin/dashboard/schedule/edit")
    public String editSchedule(
            @RequestParam Long id,
            @RequestParam Long subjectId,
            @RequestParam Long teacherId,
            @RequestParam Long roomId,
            @RequestParam Long periodId,
            @RequestParam String date,
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) String weekStart
    ) {
        Schedule schedule = scheduleRepository.findById(id).orElse(null);
        if (schedule == null) {
            // Không tìm thấy lịch, quay lại
            return "redirect:/admin/dashboard/schedule-management?classId=" + (classId != null ? classId : "") + "&weekStart=" + (weekStart != null ? weekStart : "");
        }
        schedule.setSubject(subjectRepository.findById(subjectId).orElse(null));
        schedule.setTeacher(usersTeachersRepository.findById(teacherId).orElse(null));
        schedule.setRoom(roomRepository.findById(roomId).orElse(null));
        schedule.setPeriod(periodRepository.findById(periodId).orElse(null));
        try {
            schedule.setDate(java.time.LocalDate.parse(date));
        } catch (Exception e) {
            // Nếu lỗi ngày thì giữ nguyên ngày cũ
        }
        scheduleRepository.save(schedule);
        // Lấy lại classId nếu chưa có
        Long redirectClassId = classId != null ? classId : (schedule.getClassRoom() != null ? schedule.getClassRoom().getId() : null);
        return "redirect:/admin/dashboard/schedule-management?classId=" + (redirectClassId != null ? redirectClassId : "") + (weekStart != null ? "&weekStart=" + weekStart : "");
    }

    @GetMapping("/admin/dashboard/schedule/export")
    public ResponseEntity<byte[]> exportSchedule(
            @RequestParam Long classId,
            @RequestParam String weekStart
    ) {
        LocalDate startDate = LocalDate.parse(weekStart);
        LocalDate endDate = startDate.plusDays(6);
        List<Schedule> schedules = scheduleRepository.findByClassRoomIdAndDateBetween(classId, startDate, endDate);

        StringBuilder sb = new StringBuilder();
        sb.append("Ngày,Tiết,Môn học,Giáo viên,Phòng học\n");
        for (Schedule s : schedules) {
            sb.append(s.getDate()).append(",");
            sb.append(s.getPeriod() != null ? s.getPeriod().getName() : "").append(",");
            sb.append(s.getSubject() != null ? s.getSubject().getName() : "").append(",");
            sb.append(s.getTeacher() != null ? s.getTeacher().getFullName() : "").append(",");
            sb.append(s.getRoom() != null ? s.getRoom().getName() : "").append("\n");
        }
        byte[] csvBytes = sb.toString().getBytes(StandardCharsets.UTF_8);

        String filename = "schedule_" + classId + "_" + weekStart + ".csv";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csvBytes);
    }
}
