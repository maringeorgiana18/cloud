package com.backend.dao.question;

import com.backend.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findAllByAuthor(User author);
    void deleteById(Integer id);

    @Query(value = "SELECT *\n" +
            "FROM Question\n" +
            "WHERE (:completed = true OR question.id not in (SELECT attempt.question_id FROM attempt WHERE attempt.author_id = :userId AND attempt.question_id IS NOT NULL AND attempt.history_status != \"SKIPPED\"))\n" +
            "AND question.type = :questionType\n" +
            "ORDER BY RAND()\n" +
            "LIMIT 1;", nativeQuery = true)
    Optional<Question> startQuestion(@Param("questionType") String questionType, @Param("userId") Integer userId, @Param("completed") boolean completed);

    @Query(value = "SELECT count(*)\n" +
            "FROM Question\n" +
            "WHERE (:completed = true OR question.id not in (SELECT attempt.question_id FROM attempt WHERE attempt.author_id = :userId AND attempt.question_id IS NOT NULL AND attempt.history_status != \"SKIPPED\"))\n" +
            "AND question.type = :questionType\n", nativeQuery = true)
    Integer numberOfAvailableQuestion(@Param("questionType") String questionType, @Param("userId") Integer userId, @Param("completed") boolean completed);

    @Query(value = "SELECT *\n" +
            "FROM Question\n" +
            "WHERE question.type = :questionType\n" +
            "ORDER BY RAND()\n" +
            "LIMIT 15;", nativeQuery = true)
    List<Question> getQuestionForExam(@Param("questionType") String questionType);
}
