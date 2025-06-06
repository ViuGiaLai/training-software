package com.viusoftware.training.training_system.repository;

import com.viusoftware.training.training_system.entity.UsersStudents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersStudentsRepository extends JpaRepository<UsersStudents, Long> {
    UsersStudents findByUsername(String username);

    @Query("SELECT u FROM UsersStudents u WHERE u.username = :input OR u.email = :input OR u.phone = :input")
    UsersStudents findByUsernameOrEmailOrPhone(@Param("input") String input);

    // Lấy danh sách học sinh theo classRoom id
    List<UsersStudents> findByClassRoom_Id(Long classRoomId);

    // Sắp xếp theo tên (không dùng COLLATE, chỉ dùng cho PostgreSQL)
    @Query("SELECT u FROM UsersStudents u WHERE u.classRoom.id = :classRoomId ORDER BY u.fullName ASC")
    List<UsersStudents> findStudentsByClassRoomOrderByFullName(@Param("classRoomId") Long classRoomId);
}
