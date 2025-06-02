package com.viusoftware.training.training_system.service;

import com.viusoftware.training.training_system.entity.UsersAdmin;
import com.viusoftware.training.training_system.entity.UsersStudents;
import com.viusoftware.training.training_system.entity.UsersTeachers;
import com.viusoftware.training.training_system.repository.UsersAdminRepository;
import com.viusoftware.training.training_system.repository.UsersStudentsRepository;
import com.viusoftware.training.training_system.repository.UsersTeachersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UsersAdminRepository usersAdminRepository;
    private final UsersTeachersRepository usersTeachersRepository;
    private final UsersStudentsRepository usersStudentsRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UsersAdminRepository usersAdminRepository,
            UsersTeachersRepository usersTeachersRepository,
            UsersStudentsRepository usersStudentsRepository,
            PasswordEncoder passwordEncoder) {
        this.usersAdminRepository = usersAdminRepository;
        this.usersTeachersRepository = usersTeachersRepository;
        this.usersStudentsRepository = usersStudentsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createUser(
            String username,
            String password,
            String role,
            String email,
            String phone) {

        String encodedPassword = passwordEncoder.encode(password);

        if ("ADMIN".equals(role)) {
            UsersAdmin admin = new UsersAdmin();
            admin.setUsername(username);
            admin.setPassword(encodedPassword);
            admin.setRole("ADMIN");
            admin.setEmail(email);
            admin.setPhone(phone);
            usersAdminRepository.save(admin);
        } else if ("TEACHER".equals(role)) {
            UsersTeachers teacher = new UsersTeachers();
            teacher.setUsername(username);
            teacher.setPassword(encodedPassword);
            teacher.setRole("TEACHER");
            teacher.setEmail(email);
            teacher.setPhone(phone);
            // Add other teacher-specific fields if necessary
            usersTeachersRepository.save(teacher);
        } else if ("STUDENT".equals(role)) {
            UsersStudents student = new UsersStudents();
            student.setUsername(username);
            student.setPassword(encodedPassword);
            student.setRole("STUDENT");
            student.setEmail(email);
            student.setPhone(phone);
            // Add other student-specific fields if necessary
            usersStudentsRepository.save(student);
        } else {
            throw new IllegalArgumentException("Invalid role specified: " + role);
        }
    }

    // You might want to add methods here to check for existing users by username, email, or phone
    // before creating a new one to handle the unique constraints.
}