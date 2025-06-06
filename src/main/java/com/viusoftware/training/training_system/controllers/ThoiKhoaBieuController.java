package com.viusoftware.training.training_system.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;

import com.viusoftware.training.training_system.entity.UsersStudents;
import com.viusoftware.training.training_system.entity.Schedule;
import com.viusoftware.training.training_system.entity.Period;
import com.viusoftware.training.training_system.repository.UsersStudentsRepository;
import com.viusoftware.training.training_system.repository.ScheduleRepository;
import com.viusoftware.training.training_system.repository.PeriodRepository;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.*;

@Controller
public class ThoiKhoaBieuController {
    @Autowired
    private UsersStudentsRepository usersStudentsRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private PeriodRepository periodRepository;

    // Học sinh chỉ được xem lịch tuần của lớp mình, không chỉnh sửa
    @GetMapping("/sv/thoi-khoa-bieu")
    public String thoiKhoaBieuPage(
            Model model,
            @RequestParam(required = false) Long classId, // <-- Cho phép truyền classId (admin/staff xem lớp khác)
            @RequestParam(required = false) String weekStart // yyyy-MM-dd, optional
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : null;

        // Nếu là học sinh, lấy classId theo user đăng nhập
        if (classId == null) {
            if (username == null) {
                model.addAttribute("periods", java.util.Collections.emptyList());
                model.addAttribute("tkbMap", new java.util.HashMap<>());
                model.addAttribute("weekStart", java.time.LocalDate.now().with(java.time.DayOfWeek.MONDAY));
                model.addAttribute("weekEnd", java.time.LocalDate.now().with(java.time.DayOfWeek.MONDAY).plusDays(6));
                model.addAttribute("noSchedule", "Bạn chưa đăng nhập hoặc chưa được phân lớp.");
                return "sv/thoi-khoa-bieu";
            }
            UsersStudents student = usersStudentsRepository.findByUsername(username);
            if (student == null || student.getClassRoom() == null) {
                model.addAttribute("periods", java.util.Collections.emptyList());
                model.addAttribute("tkbMap", new java.util.HashMap<>());
                model.addAttribute("weekStart", java.time.LocalDate.now().with(java.time.DayOfWeek.MONDAY));
                model.addAttribute("weekEnd", java.time.LocalDate.now().with(java.time.DayOfWeek.MONDAY).plusDays(6));
                model.addAttribute("noSchedule", "Bạn chưa được phân lớp hoặc dữ liệu lớp chưa đúng.");
                return "sv/thoi-khoa-bieu";
            }
            // Sửa lỗi nếu student.getClassRoom() null hoặc chưa có id
            if (student.getClassRoom() == null || student.getClassRoom().getId() == null) {
                model.addAttribute("periods", java.util.Collections.emptyList());
                model.addAttribute("tkbMap", new java.util.HashMap<>());
                model.addAttribute("weekStart", java.time.LocalDate.now().with(java.time.DayOfWeek.MONDAY));
                model.addAttribute("weekEnd", java.time.LocalDate.now().with(java.time.DayOfWeek.MONDAY).plusDays(6));
                model.addAttribute("noSchedule", "Lớp của bạn chưa được gán mã lớp hoặc dữ liệu lớp chưa đúng.");
                return "sv/thoi-khoa-bieu";
            }
            classId = student.getClassRoom().getId();
        }

        // Xác định tuần hiện tại hoặc theo tham số
        LocalDate startDate;
        if (weekStart != null && !weekStart.isBlank()) {
            startDate = LocalDate.parse(weekStart);
        } else {
            LocalDate today = LocalDate.now();
            startDate = today.with(DayOfWeek.MONDAY);
        }
        LocalDate endDate = startDate.plusDays(6);

        List<Period> periods = periodRepository.findAll();
        List<Schedule> schedules = scheduleRepository.findByClassRoomIdAndDateBetween(classId, startDate, endDate);

        // Debug log
        System.out.println("ClassId: " + classId);
        System.out.println("Schedules found: " + schedules.size());
        for (Schedule s : schedules) {
            System.out.println("Schedule: " + s.getDate() + " - " + s.getPeriod().getId() + " - " + s.getPeriod().getName() + " - " + s.getSubject().getName());
        }

        if (schedules.isEmpty()) {
            model.addAttribute("noSchedule", "Không có thời khóa biểu cho lớp này trong tuần này.");
        } else {
            model.addAttribute("noSchedule", null);
        }

        // Map: periodId -> dayOfWeek (0=Monday) -> schedule
        Map<Long, Map<Integer, Schedule>> tkbMap = new HashMap<>();
        for (Period p : periods) {
            tkbMap.put(p.getId(), new HashMap<>());
        }
        for (Schedule s : schedules) {
            int dow = s.getDate().getDayOfWeek().getValue() - 1; // 0=Monday
            if (tkbMap.containsKey(s.getPeriod().getId())) {
                tkbMap.get(s.getPeriod().getId()).put(dow, s);
            }
        }

        model.addAttribute("periods", periods);
        model.addAttribute("tkbMap", tkbMap);
        model.addAttribute("schedules", schedules); // BỔ SUNG DÒNG NÀY nếu dùng schedules trong view
        model.addAttribute("weekStart", startDate);
        model.addAttribute("weekEnd", endDate);

        return "sv/thoi-khoa-bieu";
    }
}
