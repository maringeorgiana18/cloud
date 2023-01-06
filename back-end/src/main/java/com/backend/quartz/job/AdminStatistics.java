package com.backend.quartz.job;

import com.backend.dao.audit.AuditRepository;
import com.backend.dao.question.Question;
import com.backend.dao.question.QuestionRepository;
import com.backend.security.repositories.UserRepository;
import com.backend.type.AuditType;
import com.backend.type.QuestionType;
import com.backend.type.UserType;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminStatistics implements Job {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AuditRepository auditRepository;

    @Override
    public void execute(JobExecutionContext context) {
        Integer teacherCount = userRepository.countAllByRole(UserType.TEACHER.name());
        Integer studentCount = userRepository.countAllByRole(UserType.STUDENT.name());

        Integer currentDayUserCount = auditRepository.countAllByAuditType(AuditType.LOGIN.name());
        Integer currentDayNewUserCount = auditRepository.countAllByAuditType(AuditType.NEW_USER.name());

        Integer mathsCount = 0;
        Integer informaticsCount = 0;
        Integer physicsCount = 0;

        List<Question> questions = questionRepository.findAll();
        for(Question question : questions) {
            if (question.getType().equals(QuestionType.INFORMATICS.name())) {
                informaticsCount++;
            } else if (question.getType().equals(QuestionType.MATHS.name())) {
                mathsCount++;
            } else if (question.getType().equals(QuestionType.PHYSICS.name())) {
                physicsCount++;
            }
        }
    }
}
