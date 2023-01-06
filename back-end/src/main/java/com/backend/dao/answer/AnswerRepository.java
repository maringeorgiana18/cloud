package com.backend.dao.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    //void deleteAllByQuestion(Question question);
  //  List<Answer> findAllByQuestion(Question question);
}
