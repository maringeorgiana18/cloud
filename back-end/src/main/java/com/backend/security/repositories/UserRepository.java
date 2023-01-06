package com.backend.security.repositories;

import com.backend.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByRole(String role);
    List<User> findAllByRole(String role);
    void deleteByEmail(String email);
    Integer countAllByRole(String role);

    @Query(value = "SELECT DISTINCT user.id, user.email, user.is_enable, user.password, user.role, user.user_name\n" +
            "FROM User\n" +
            "LEFT JOIN Attempt ON attempt.question_id = :questionId\n" +
            "WHERE attempt.author_id = user.id\n"
            , nativeQuery = true)
    List<User> findAllByQuestions(@Param("questionId") Integer questionId);

}
