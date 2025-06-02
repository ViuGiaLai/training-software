package com.viusoftware.training.training_system.repository;

import com.viusoftware.training.training_system.entity.UsersTeachers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersTeachersRepository extends JpaRepository<UsersTeachers, Long> {
    UsersTeachers findByUsername(String username);
    
    @Query("SELECT u FROM UsersTeachers u WHERE u.username = :input OR u.email = :input OR u.phone = :input")
    UsersTeachers findByUsernameOrEmailOrPhone(@Param("input") String input);
    // Có thể thêm các phương thức tìm kiếm tùy ý nếu cần
}
