package com.viusoftware.training.training_system.repository;

import com.viusoftware.training.training_system.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {}
