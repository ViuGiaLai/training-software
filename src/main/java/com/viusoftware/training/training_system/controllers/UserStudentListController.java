package com.viusoftware.training.training_system.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.viusoftware.training.training_system.entity.UsersStudents;
import com.viusoftware.training.training_system.repository.UsersStudentsRepository;

import java.time.LocalDate;
import java.util.List;
import java.io.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserStudentListController {

    @Autowired
    private UsersStudentsRepository usersStudentsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/admin/dashboard/users/user-student-list")
    public String userStudentList(
            Model model,
            @RequestParam(required = false) String classroom,
            @RequestParam(required = false) String course,
            @RequestParam(required = false) String major,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String keyword
    ) {
        java.util.List<UsersStudents> students = usersStudentsRepository.findAll();

        // Lọc theo các trường nếu có
        if (classroom != null && !classroom.isBlank()) {
            students = students.stream().filter(s -> classroom.equals(s.getClassroom())).toList();
        }
        if (course != null && !course.isBlank()) {
            students = students.stream().filter(s -> course.equals(s.getCourse())).toList();
        }
        if (major != null && !major.isBlank()) {
            students = students.stream().filter(s -> major.equals(s.getMajor())).toList();
        }
        if (status != null && !status.isBlank()) {
            students = students.stream().filter(s -> status.equals(s.getStatus())).toList();
        }
        if (keyword != null && !keyword.isBlank()) {
            String kw = keyword.trim().toLowerCase();
            students = students.stream().filter(s ->
                (s.getFullName() != null && s.getFullName().toLowerCase().contains(kw)) ||
                (s.getStudentCode() != null && s.getStudentCode().toLowerCase().contains(kw)) ||
                (s.getUsername() != null && s.getUsername().toLowerCase().contains(kw)) ||
                (s.getEmail() != null && s.getEmail().toLowerCase().contains(kw))
            ).toList();
        }

        model.addAttribute("students", students);
        model.addAttribute("classrooms", java.util.List.of(
            "10A1", "10A2", "11A1", "11A2", "12A1", "12A2"
        ));
        model.addAttribute("courses", java.util.List.of(
            "K2022", "K2023", "K2024"
        ));
        model.addAttribute("majors", java.util.List.of(
            "Toán", "Văn", "Anh", "Lý", "Hóa", "Sinh"
        ));
        model.addAttribute("statuses", java.util.List.of(
            "Đang học", "Bảo lưu", "Đã tốt nghiệp", "Bị đình chỉ"
        ));
        // Truyền lại giá trị lọc để giữ trạng thái form
        model.addAttribute("selectedClassroom", classroom);
        model.addAttribute("selectedCourse", course);
        model.addAttribute("selectedMajor", major);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("keyword", keyword);

        return "dashboard/users/user-student-list";
    }

    @PostMapping("/admin/dashboard/users/user-student-list/add")
    public String addStudent(
            @RequestParam String fullName,
            @RequestParam String studentCode,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam(required = false) String phone,
            @RequestParam String gender,
            @RequestParam String major,
            @RequestParam String course,
            @RequestParam String classroom,
            @RequestParam String status,
            @RequestParam String enrollmentDate,
            @RequestParam String password
    ) {
        // Không cho phép tạo tài khoản admin ở đây
        if ("admin".equalsIgnoreCase(username.trim())) {
            return "redirect:/admin/dashboard/users/user-student-list?error=admin";
        }
        UsersStudents student = new UsersStudents();
        student.setFullName(fullName);
        student.setStudentCode(studentCode);
        student.setUsername(username.trim());
        student.setEmail(email);
        student.setPhone(phone);
        student.setGender(gender);
        student.setMajor(major);
        student.setCourse(course);
        student.setClassroom(classroom);
        student.setStatus(status);
        student.setEnrollmentDate(LocalDate.parse(enrollmentDate));
        student.setPassword(passwordEncoder.encode(password));
        student.setRole("STUDENT");
        usersStudentsRepository.save(student);
        return "redirect:/admin/dashboard/users/user-student-list";
    }

    @PostMapping("/admin/dashboard/users/user-student-list/edit")
    public String editStudent(
            @RequestParam Long id,
            @RequestParam String fullName,
            @RequestParam String studentCode,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam(required = false) String phone,
            @RequestParam String gender,
            @RequestParam String major,
            @RequestParam String course,
            @RequestParam String classroom,
            @RequestParam String status,
            @RequestParam String enrollmentDate,
            @RequestParam String password
    ) {
        UsersStudents student = usersStudentsRepository.findById(id).orElse(null);
        if (student != null) {
            // Không cho phép sửa tài khoản admin
            if ("admin".equalsIgnoreCase(student.getUsername())) {
                return "redirect:/admin/dashboard/users/user-student-list?error=admin";
            }
            student.setFullName(fullName);
            student.setStudentCode(studentCode);
            student.setUsername(username.trim());
            student.setEmail(email);
            student.setPhone(phone);
            student.setGender(gender);
            student.setMajor(major);
            student.setCourse(course);
            student.setClassroom(classroom);
            student.setStatus(status);
            student.setEnrollmentDate(LocalDate.parse(enrollmentDate));
            student.setPassword(passwordEncoder.encode(password));
            student.setRole("STUDENT");
            usersStudentsRepository.save(student);
        }
        return "redirect:/admin/dashboard/users/user-student-list";
    }

    @PostMapping("/admin/dashboard/users/user-student-list/delete")
    public String deleteStudent(@RequestParam Long id) {
        UsersStudents student = usersStudentsRepository.findById(id).orElse(null);
        if (student != null && !"admin".equalsIgnoreCase(student.getUsername())) {
            usersStudentsRepository.deleteById(id);
        }
        return "redirect:/admin/dashboard/users/user-student-list";
    }

    @GetMapping("/admin/dashboard/users/user-student-list/export-excel")
    @ResponseBody
    public ResponseEntity<byte[]> exportStudentsExcel(
            @RequestParam(required = false) String classroom,
            @RequestParam(required = false) String course,
            @RequestParam(required = false) String major,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String keyword
    ) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Danh sách học sinh");

            Row header = sheet.createRow(0);
            String[] columns = {"Họ tên", "Mã HC", "Tên đăng nhập", "Email", "Số điện thoại", "Giới tính", "Ngành học", "Khóa học", "Lớp", "Trạng thái", "Ngày nhập học"};
            for (int i = 0; i < columns.length; i++) {
                header.createCell(i).setCellValue(columns[i]);
            }

            java.util.List<UsersStudents> students = usersStudentsRepository.findAll();

            // Áp dụng lại logic lọc tương tự như phương thức hiển thị danh sách
            if (classroom != null && !classroom.isBlank()) {
                students = students.stream().filter(s -> classroom.equals(s.getClassroom())).toList();
            }
            if (course != null && !course.isBlank()) {
                students = students.stream().filter(s -> course.equals(s.getCourse())).toList();
            }
            if (major != null && !major.isBlank()) {
                students = students.stream().filter(s -> major.equals(s.getMajor())).toList();
            }
            if (status != null && !status.isBlank()) {
                students = students.stream().filter(s -> status.equals(s.getStatus())).toList();
            }
            if (keyword != null && !keyword.isBlank()) {
                String kw = keyword.trim().toLowerCase();
                students = students.stream().filter(s ->
                    (s.getFullName() != null && s.getFullName().toLowerCase().contains(kw)) ||
                    (s.getStudentCode() != null && s.getStudentCode().toLowerCase().contains(kw)) ||
                    (s.getUsername() != null && s.getUsername().toLowerCase().contains(kw)) ||
                    (s.getEmail() != null && s.getEmail().toLowerCase().contains(kw))
                ).toList();
            }

            int rowIdx = 1;
            for (UsersStudents s : students) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(s.getFullName());
                row.createCell(1).setCellValue(s.getStudentCode());
                row.createCell(2).setCellValue(s.getUsername());
                row.createCell(3).setCellValue(s.getEmail());
                row.createCell(4).setCellValue(s.getPhone());
                row.createCell(5).setCellValue(s.getGender());
                row.createCell(6).setCellValue(s.getMajor());
                row.createCell(7).setCellValue(s.getCourse());
                row.createCell(8).setCellValue(s.getClassroom());
                row.createCell(9).setCellValue(s.getStatus());
                row.createCell(10).setCellValue(s.getEnrollmentDate() != null ? s.getEnrollmentDate().toString() : "");
                // Không xuất mật khẩu ra Excel vì lý do bảo mật
            }

            // Auto-size columns
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=students.xlsx");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(out.toByteArray());

        } catch (Exception e) {
            e.printStackTrace(); // In log lỗi ra console server
            return ResponseEntity.badRequest().body(null);
        }
    }
}
