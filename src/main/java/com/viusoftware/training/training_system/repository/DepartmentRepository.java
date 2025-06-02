package com.viusoftware.training.training_system.repository;

import com.viusoftware.training.training_system.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {}
