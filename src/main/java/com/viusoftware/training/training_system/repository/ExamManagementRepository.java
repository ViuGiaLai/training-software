package com.viusoftware.training.training_system.repository;

import com.viusoftware.training.training_system.entity.ExamSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExamManagementRepository extends JpaRepository<ExamSchedule, Long> {
    List<ExamSchedule> findBySubjectContaining(String subject);
    List<ExamSchedule> findByClassesContaining(String classes);
}