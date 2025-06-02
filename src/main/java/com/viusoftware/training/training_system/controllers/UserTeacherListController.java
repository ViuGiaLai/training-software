package com.viusoftware.training.training_system.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.viusoftware.training.training_system.entity.UsersTeachers;
import com.viusoftware.training.training_system.repository.UsersTeachersRepository;

import java.time.LocalDate;
import java.util.List;
import java.io.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Controller
public class UserTeacherListController {

    @Autowired
    private UsersTeachersRepository usersTeachersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/admin/dashboard/users/user-teacher-list")
    public String userTeacherList(Model model) {
        model.addAttribute("teachers", usersTeachersRepository.findAll());
        // Dùng danh sách cố định cho THPT
        model.addAttribute("departments", java.util.List.of(
            "Tổ Toán", "Tổ Lý", "Tổ Hóa", "Tổ Văn", "Tổ Anh", "Tổ Sinh", "Tổ Sử", "Tổ Địa", "Tổ Tin học"
        ));
        model.addAttribute("positions", java.util.List.of(
            "Giáo viên", "Tổ trưởng", "Tổ phó", "Hiệu trưởng", "Phó hiệu trưởng"
        ));
        model.addAttribute("degrees", java.util.List.of(
            "Cử nhân", "Thạc sĩ", "Tiến sĩ"
        ));
        model.addAttribute("statuses", java.util.List.of(
            "Đang công tác", "Đã nghỉ", "Tạm nghỉ"
        ));
        return "dashboard/users/user-teacher-list";
    }

    @PostMapping("/admin/dashboard/users/user-teacher-list/add")
    public String addTeacher(
            @RequestParam String fullName,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String department,
            @RequestParam String position,
            @RequestParam String degree,
            @RequestParam String status,
            @RequestParam(required = false) String avatar,
            @RequestParam String password
    ) {
        // Không cho phép tạo tài khoản admin ở đây
        if ("admin".equalsIgnoreCase(username.trim())) {
            return "redirect:/admin/dashboard/users/user-teacher-list?error=admin";
        }
        try {
            UsersTeachers teacher = new UsersTeachers();
            teacher.setFullName(fullName);
            teacher.setUsername(username.trim());
            teacher.setEmail(email);
            teacher.setDepartment(department);
            teacher.setPosition(position);
            teacher.setDegree(degree);
            teacher.setStatus(status);
            teacher.setAvatar(avatar);
            teacher.setCreatedAt(LocalDate.now());
            teacher.setPassword(passwordEncoder.encode(password));
            teacher.setRole("TEACHER");
            usersTeachersRepository.save(teacher);
            return "redirect:/admin/dashboard/users/user-teacher-list";
        } catch (Exception ex) {
            return "redirect:/admin/dashboard/users/user-teacher-list?error=add";
        }
    }

    @PostMapping("/admin/dashboard/users/user-teacher-list/edit")
    public String editTeacher(
            @RequestParam Long id,
            @RequestParam String fullName,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String department,
            @RequestParam String position,
            @RequestParam String degree,
            @RequestParam String status,
            @RequestParam(required = false) String avatar,
            @RequestParam String password
    ) {
        UsersTeachers teacher = usersTeachersRepository.findById(id).orElse(null);
        if (teacher != null) {
            // Không cho phép sửa tài khoản admin
            if ("admin".equalsIgnoreCase(teacher.getUsername())) {
                return "redirect:/admin/dashboard/users/user-teacher-list?error=admin";
            }
            teacher.setFullName(fullName);
            teacher.setUsername(username.trim());
            teacher.setEmail(email);
            teacher.setDepartment(department);
            teacher.setPosition(position);
            teacher.setDegree(degree);
            teacher.setStatus(status);
            teacher.setAvatar(avatar);
            teacher.setPassword(passwordEncoder.encode(password));
            teacher.setRole("TEACHER");
            usersTeachersRepository.save(teacher);
        }
        return "redirect:/admin/dashboard/users/user-teacher-list";
    }

    @PostMapping("/admin/dashboard/users/user-teacher-list/delete")
    public String deleteTeacher(@RequestParam Long id) {
        UsersTeachers teacher = usersTeachersRepository.findById(id).orElse(null);
        if (teacher != null && !"admin".equalsIgnoreCase(teacher.getUsername())) {
            usersTeachersRepository.deleteById(id);
        }
        return "redirect:/admin/dashboard/users/user-teacher-list";
    }

    @GetMapping("/admin/dashboard/users/user-teacher-list/export-excel")
    @ResponseBody
    public ResponseEntity<byte[]> exportTeachersExcel() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Danh sách giảng viên");
            Row header = sheet.createRow(0);
            String[] columns = {"Họ và tên", "Tên đăng nhập", "Email", "Khoa/Bộ môn", "Chức danh", "Học vị", "Trạng thái", "Ngày tạo"};
            for (int i = 0; i < columns.length; i++) {
                header.createCell(i).setCellValue(columns[i]);
            }
            List<UsersTeachers> teachers = usersTeachersRepository.findAll();
            int rowIdx = 1;
            for (UsersTeachers t : teachers) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(t.getFullName());
                row.createCell(1).setCellValue(t.getUsername());
                row.createCell(2).setCellValue(t.getEmail());
                row.createCell(3).setCellValue(t.getDepartment());
                row.createCell(4).setCellValue(t.getPosition());
                row.createCell(5).setCellValue(t.getDegree());
                row.createCell(6).setCellValue(t.getStatus());
                row.createCell(7).setCellValue(t.getCreatedAt() != null ? t.getCreatedAt().toString() : "");
            }
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=teachers.xlsx");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(out.toByteArray());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
