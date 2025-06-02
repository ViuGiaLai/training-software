package com.viusoftware.training.training_system.service;

import com.viusoftware.training.training_system.entity.UsersAdmin;
import com.viusoftware.training.training_system.entity.UsersStudents;
import com.viusoftware.training.training_system.entity.UsersTeachers;
import com.viusoftware.training.training_system.repository.UsersAdminRepository;
import com.viusoftware.training.training_system.repository.UsersStudentsRepository;
import com.viusoftware.training.training_system.repository.UsersTeachersRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final UsersAdminRepository usersAdminRepository;
    private final UsersTeachersRepository usersTeachersRepository;
    private final UsersStudentsRepository usersStudentsRepository;

    public CustomUserDetailsService(
        UsersAdminRepository usersAdminRepository,
        UsersTeachersRepository usersTeachersRepository,
        UsersStudentsRepository usersStudentsRepository
    ) {
        this.usersAdminRepository = usersAdminRepository;
        this.usersTeachersRepository = usersTeachersRepository;
        this.usersStudentsRepository = usersStudentsRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {
        logger.info("Attempting to load user by username/email/phone: {}", input);

        // Check Admin
        UsersAdmin admin = usersAdminRepository.findByUsernameOrEmailOrPhone(input);
        if (admin != null) {
            logger.info("Admin user found: {}", admin.getUsername());
            return new User(
                admin.getUsername(),
                admin.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }

        // Check Teacher
        UsersTeachers teacher = usersTeachersRepository.findByUsernameOrEmailOrPhone(input);
        if (teacher != null) {
            logger.info("Teacher user found: {}", teacher.getUsername());
            return new User(
                teacher.getUsername(),
                teacher.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_TEACHER"))
            );
        }

        // Check Student
        UsersStudents student = usersStudentsRepository.findByUsernameOrEmailOrPhone(input);
        if (student != null) {
            logger.info("Student user found: {}", student.getUsername());
            return new User(
                student.getUsername(),
                student.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_STUDENT"))
            );
        }

        logger.warn("User not found with input: {}", input);
        throw new UsernameNotFoundException("Khong tim thay tai khoan voi username/email/phone: " + input);
    }
}
