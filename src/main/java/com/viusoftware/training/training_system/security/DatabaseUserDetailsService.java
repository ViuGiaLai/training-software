package com.viusoftware.training.training_system.security;

import com.viusoftware.training.training_system.entity.UsersAdmin;
import com.viusoftware.training.training_system.entity.UsersStudents;
import com.viusoftware.training.training_system.entity.UsersTeachers;
import com.viusoftware.training.training_system.repository.UsersAdminRepository;
import com.viusoftware.training.training_system.repository.UsersStudentsRepository;
import com.viusoftware.training.training_system.repository.UsersTeachersRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class DatabaseUserDetailsService implements UserDetailsService {

    private final UsersStudentsRepository studentsRepository;
    private final UsersTeachersRepository teachersRepository;
    private final UsersAdminRepository adminRepository;

    public DatabaseUserDetailsService(UsersStudentsRepository studentsRepository,
                                    UsersTeachersRepository teachersRepository,
                                    UsersAdminRepository adminRepository) {
        this.studentsRepository = studentsRepository;
        this.teachersRepository = teachersRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Kiểm tra trong bảng admin trước
        UsersAdmin admin = adminRepository.findByUsername(username);
        if (admin != null) {
            return User.withUsername(admin.getUsername())
                    .password(admin.getPassword())
                    .roles(admin.getRole())
                    .build();
        }

        // Kiểm tra trong bảng giáo viên
        UsersTeachers teacher = teachersRepository.findByUsername(username);
        if (teacher != null) {
            return User.withUsername(teacher.getUsername())
                    .password(teacher.getPassword())
                    .roles(teacher.getRole())
                    .build();
        }

        // Kiểm tra trong bảng học sinh
        UsersStudents student = studentsRepository.findByUsername(username);
        if (student != null) {
            return User.withUsername(student.getUsername())
                    .password(student.getPassword())
                    .roles(student.getRole())
                    .build();
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}