package com.backend.dao.exam;

import com.backend.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {
    List<Exam> findAllByAuthor(User author);
    Optional<Exam> findByAuthorAndResult(User author, Integer result);
}
