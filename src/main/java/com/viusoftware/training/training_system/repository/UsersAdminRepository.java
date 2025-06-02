package com.viusoftware.training.training_system.repository;

import com.viusoftware.training.training_system.entity.UsersAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersAdminRepository extends JpaRepository<UsersAdmin, Long> {
    UsersAdmin findByUsername(String username);
    
    @Query("SELECT u FROM UsersAdmin u WHERE u.username = :input OR u.email = :input OR u.phone = :input")
    UsersAdmin findByUsernameOrEmailOrPhone(@Param("input") String input);
}
