package com.viusoftware.training.training_system.repository;

import com.viusoftware.training.training_system.entity.UsersStudents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersStudentsRepository extends JpaRepository<UsersStudents, Long> {
    UsersStudents findByUsername(String username);
    
    @Query("SELECT u FROM UsersStudents u WHERE u.username = :input OR u.email = :input OR u.phone = :input")
    UsersStudents findByUsernameOrEmailOrPhone(@Param("input") String input);
    // Có thể thêm các phương thức tìm kiếm tùy ý nếu cần
}
