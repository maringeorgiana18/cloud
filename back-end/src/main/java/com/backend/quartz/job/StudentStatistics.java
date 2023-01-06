package com.backend.quartz.job;

import com.backend.dao.attempt.Attempt;
import com.backend.dao.attempt.AttemptRepository;
import com.backend.dao.exam.Exam;
import com.backend.dao.exam.ExamRepository;
import com.backend.security.entities.User;
import com.backend.security.repositories.UserRepository;
import com.backend.type.HistoryStatus;
import com.backend.type.QuestionType;
import com.backend.type.UserType;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class StudentStatistics implements Job {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttemptRepository attemptRepository;

    @Autowired
    private ExamRepository examRepository;

    @Override
    public void execute(JobExecutionContext context) {
        List<User> users = userRepository.findAllByRole(UserType.STUDENT.name());
        Timestamp currentDay = new Timestamp(System.currentTimeMillis());

        users.forEach(user -> {
            Integer correctCount = 0;
            Integer incorrectCount = 0;
            Integer examAverage = 0;
            Integer mathsCount = 0;
            Integer informaticsCount = 0;
            Integer physicsCount = 0;
            Integer response = 0;

            List<Exam> exams = examRepository.findAllByAuthor(user);
            if (exams.size() != 0) {
                for (Exam exam : exams) {
                    examAverage += exam.getResult();
                }
                examAverage = examAverage / exams.size();
            }

            List<Attempt> attempts = attemptRepository.findAllByAuthorAndHistoryStatus(user, HistoryStatus.FINISHED.name());
            for (Attempt attempt : attempts) {
                if (attempt.getType().equals(QuestionType.INFORMATICS.name())) {
                    informaticsCount++;
                } else if (attempt.getType().equals(QuestionType.MATHS.name())) {
                    mathsCount++;
                } else if (attempt.getType().equals(QuestionType.PHYSICS.name())) {
                    physicsCount++;
                }

                if (attempt.getStatus().equals(Boolean.TRUE)) {
                    correctCount++;
                } else if (attempt.getStatus().equals(Boolean.FALSE)) {
                    incorrectCount++;
                }
            }
            response = attempts.size();
        });
    }
}
