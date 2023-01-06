package com.backend.dao.attempt;

import com.backend.dao.question.Question;
import com.backend.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttemptRepository  extends JpaRepository<Attempt, Integer> {

    Optional<Attempt> findByAuthorAndHistoryStatus(User author, String historyStatus);
    List<Attempt> findAllByAuthor(User author);
    List<Attempt> findAllByAuthorAndHistoryStatus(User author, String historyStatus);
    List<Attempt> findAllByQuestion(Question question);
    Integer countAllByAuthorAndHistoryStatus(User author, String historyStatus);
    void deleteAllByQuestionAndHistoryStatus(Question question, String historyStatus);
}
