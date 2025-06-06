package com.viusoftware.training.training_system.controllers;

import com.viusoftware.training.training_system.entity.UsersStudents;
import com.viusoftware.training.training_system.repository.UsersStudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Controller
@RequestMapping("/sv")
public class StudentProfileController {

    @Autowired
    private UsersStudentsRepository usersStudentsRepository;

    @GetMapping("/thong-tin-ca-nhan")
    public String viewProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : null;
        UsersStudents student = usersStudentsRepository.findByUsername(username);
        model.addAttribute("student", student);
        return "sv/thong-tin-ca-nhan";
    }

    @PostMapping("/thong-tin-ca-nhan/update")
    public String updateProfile(
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String dateOfBirth,
            @RequestParam(required = false) String avatarUrl,
            @RequestParam String studentCode,
            @RequestParam String username,
            // Bổ sung các trường cá nhân và gia đình
            @RequestParam(required = false) String birthPlace,
            @RequestParam(required = false) String identityNumber,
            @RequestParam(required = false) String identityIssueDate,
            @RequestParam(required = false) String identityIssuePlace,
            @RequestParam(required = false) String ethnicity,
            @RequestParam(required = false) String religion,
            @RequestParam(required = false) String nationality,
            @RequestParam(required = false) String fatherName,
            @RequestParam(required = false) String fatherJob,
            @RequestParam(required = false) String fatherPhone,
            @RequestParam(required = false) String motherName,
            @RequestParam(required = false) String motherJob,
            @RequestParam(required = false) String motherPhone
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth != null ? auth.getName() : null;
        UsersStudents student = usersStudentsRepository.findByUsername(currentUsername);
        if (student != null) {
            student.setFullName(fullName);
            student.setEmail(email);
            student.setPhone(phone);
            student.setGender(gender);
            student.setAddress(address);
            if (dateOfBirth != null && !dateOfBirth.isBlank()) {
                student.setDateOfBirth(LocalDate.parse(dateOfBirth));
            }
            student.setAvatarUrl(avatarUrl);
            // Bổ sung cập nhật các trường cá nhân và gia đình
            student.setBirthPlace(birthPlace);
            student.setIdentityNumber(identityNumber);
            if (identityIssueDate != null && !identityIssueDate.isBlank()) {
                student.setIdentityIssueDate(LocalDate.parse(identityIssueDate));
            }
            student.setIdentityIssuePlace(identityIssuePlace);
            student.setEthnicity(ethnicity);
            student.setReligion(religion);
            student.setNationality(nationality);
            student.setFatherName(fatherName);
            student.setFatherJob(fatherJob);
            student.setFatherPhone(fatherPhone);
            student.setMotherName(motherName);
            student.setMotherJob(motherJob);
            student.setMotherPhone(motherPhone);
            usersStudentsRepository.save(student);
        }
        return "redirect:/sv/thong-tin-ca-nhan";
    }
}
