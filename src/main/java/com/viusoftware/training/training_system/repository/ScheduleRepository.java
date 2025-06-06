package com.viusoftware.training.training_system.repository;

import com.viusoftware.training.training_system.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByClassRoomId(Long classRoomId);
    List<Schedule> findByClassRoomIdAndDateBetween(Long classRoomId, LocalDate start, LocalDate end);
    boolean existsByClassRoomIdAndDateAndPeriodId(Long classRoomId, LocalDate date, Long periodId);
    List<Schedule> findByClassRoomIdAndDate(Long classRoomId, LocalDate date);
}
