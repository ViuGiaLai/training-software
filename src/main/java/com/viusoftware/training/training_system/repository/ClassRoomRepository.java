package com.viusoftware.training.training_system.repository;

import com.viusoftware.training.training_system.entity.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
    @Query("SELECT DISTINCT c.grade FROM ClassRoom c ORDER BY c.grade")
    List<String> findDistinctGrades();

    @Query("SELECT DISTINCT c.academicYear FROM ClassRoom c ORDER BY c.academicYear DESC")
    List<String> findDistinctAcademicYears();

    ClassRoom findByName(String name);

    // Lấy danh sách lớp và fetch students (tránh lazy loading lỗi)
    @Query("SELECT c FROM ClassRoom c LEFT JOIN FETCH c.students")
    List<ClassRoom> findAllWithStudents();

    // Lấy danh sách học sinh của một lớp theo tên (JPQL, hỗ trợ Unicode)
    @Query(value = "SELECT * FROM users_students WHERE class_room_id = :classRoomId ORDER BY full_name COLLATE utf8mb4_vietnamese_ci ASC", nativeQuery = true)
    List<com.viusoftware.training.training_system.entity.UsersStudents> findStudentsByClassRoomOrderByFullName(@Param("classRoomId") Long classRoomId);

    @Query("SELECT COUNT(s) FROM UsersStudents s WHERE s.classRoom.name = :className")
    int countStudentsByClassName(@Param("className") String className);
}
