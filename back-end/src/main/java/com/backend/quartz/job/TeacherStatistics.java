package com.backend.quartz.job;

import com.backend.dao.attempt.Attempt;
import com.backend.dao.attempt.AttemptRepository;
import com.backend.dao.question.Question;
import com.backend.dao.question.QuestionRepository;
import com.backend.security.entities.User;
import com.backend.security.repositories.UserRepository;
import com.backend.type.QuestionType;
import com.backend.type.UserType;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class TeacherStatistics implements Job {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttemptRepository attemptRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public void execute(JobExecutionContext context) {
        List<User> users = userRepository.findAllByRole(UserType.TEACHER.name());
        Timestamp currentDay = new Timestamp(System.currentTimeMillis());

        users.forEach(user -> {
            Integer correctCount = 0;
            Integer incorrectCount = 0;
            Integer mathsCount = 0;
            Integer informaticsCount = 0;
            Integer physicsCount = 0;
            Integer response = 0;

            List<Question> questions = questionRepository.findAllByAuthor(user);
            for(Question question : questions) {
                if (question.getType().equals(QuestionType.INFORMATICS.name())) {
                    informaticsCount++;
                } else if (question.getType().equals(QuestionType.MATHS.name())) {
                    mathsCount++;
                } else if (question.getType().equals(QuestionType.PHYSICS.name())) {
                    physicsCount++;
                }
                List<Attempt> attempts = attemptRepository.findAllByQuestion(question);
                for (Attempt attempt : attempts) {
                    if (attempt.getStatus().equals(Boolean.TRUE)) {
                        correctCount++;
                    } else if (attempt.getStatus().equals(Boolean.FALSE)) {
                        incorrectCount++;
                    }

                    if (attempt.getEndDate().equals(currentDay)) {
                        response++;
                    }
                }
            }
        });
    }

}
