package com.viusoftware.training.training_system.repository;

import com.viusoftware.training.training_system.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {}
