package com.viusoftware.training.training_system.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.util.List;

import com.viusoftware.training.training_system.entity.UsersStudents;
import com.viusoftware.training.training_system.entity.Period;
import com.viusoftware.training.training_system.entity.Schedule;
import com.viusoftware.training.training_system.repository.UsersStudentsRepository;
import com.viusoftware.training.training_system.repository.PeriodRepository;
import com.viusoftware.training.training_system.repository.ScheduleRepository;

@Controller
public class StudentFragmentController {

    @Autowired
    private UsersStudentsRepository studentsRepository;
    @Autowired
    private PeriodRepository periodRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    @GetMapping("/sv/thoi-khoa-bieu/fragment")
    public String tkbFragment(Model model, @AuthenticationPrincipal UserDetails user) {
        UsersStudents student = studentsRepository.findByUsername(user.getUsername());
        LocalDate weekStart = LocalDate.now().with(java.time.DayOfWeek.MONDAY);
        LocalDate weekEnd = weekStart.plusDays(6);

        List<Period> periods = periodRepository.findAll();

        List<Schedule> schedules = List.of();
        if (student != null && student.getClassRoom() != null && student.getClassRoom().getId() != null) {
            // Sử dụng đúng method repository với classRoomId
            schedules = scheduleRepository.findByClassRoomIdAndDateBetween(
                student.getClassRoom().getId(), weekStart, weekEnd
            );
        }

        model.addAttribute("weekStart", weekStart);
        model.addAttribute("weekEnd", weekEnd);
        model.addAttribute("periods", periods);
        model.addAttribute("schedules", schedules);
        if (schedules == null || schedules.isEmpty()) {
            model.addAttribute("noSchedule", "Không có thời khóa biểu cho tuần này.");
        }
        return "sv/thoi-khoa-bieu :: content";
    }

    @GetMapping("/sv/lich-thi/fragment")
    public String lichThiFragment(Model model, @AuthenticationPrincipal UserDetails user) {
        // ...load dữ liệu vào model như bình thường...
        return "sv/lich-thi :: content";
    }

    @GetMapping("/sv/thong-tin-ca-nhan/fragment")
    public String thongTinCaNhanFragment(Model model, @AuthenticationPrincipal UserDetails user) {
        // ...load dữ liệu vào model như bình thường...
        return "sv/thong-tin-ca-nhan :: content";
    }

    // Tương tự cho các fragment khác...
}
