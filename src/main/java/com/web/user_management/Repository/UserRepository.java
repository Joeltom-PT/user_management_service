package com.web.user_management.Repository;

import com.web.user_management.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT(:keyword, '%')) AND LOWER(u.role) LIKE LOWER(CONCAT('%', :role, '%'))")
    List<User> findByUsernameContaining(String keyword,String role);


    List<User> findByRole(String role);

}
