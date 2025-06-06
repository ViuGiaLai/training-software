package com.viusoftware.training.training_system.repository;

import com.viusoftware.training.training_system.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
