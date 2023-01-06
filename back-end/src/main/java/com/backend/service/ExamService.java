package com.backend.service;

import com.backend.dao.attempt.Attempt;
import com.backend.dao.attempt.AttemptRepository;
import com.backend.dao.attemptAnswer.AttemptAnswer;
import com.backend.dao.exam.Exam;
import com.backend.dao.exam.ExamRepository;
import com.backend.dao.question.Question;
import com.backend.dao.question.QuestionRepository;
import com.backend.mapper.AttemptMapper;
import com.backend.mapper.ExamMapper;
import com.backend.model.attempt.FinishAttemptModel;
import com.backend.model.exam.FinishExamModel;
import com.backend.security.entities.User;
import com.backend.type.HistoryStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private AttemptMapper attemptMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private AttemptRepository attemptRepository;

    @Autowired
    private AttemptService attemptService;

    public ResponseEntity<?> start(String questionType) {
        List<Question> questions = questionRepository.getQuestionForExam(questionType);
        Timestamp startDate = new Timestamp(new java.util.Date().getTime());
        User user = userService.getCurrentUser();
        List<Attempt> attempts = new ArrayList<>();
        questions.forEach(question -> {
            Attempt attempt = attemptMapper.questionToAttempt(question, null, null, HistoryStatus.EXAM.name());
            attempt.getAnswers().forEach(attemptAnswer -> attemptAnswer.setAttempt(attempt));
            attempts.add(attempt);
        });
        Exam exam = new Exam();
        exam.setAuthor(user);
        exam.setAttempts(attempts);
        exam.setStartDate(startDate);
        exam.setType(questionType);
        examRepository.save(exam);
        return ResponseEntity.ok(examMapper.examToStartExamModel(exam));
    }

    public ResponseEntity<?> getStarted() {
        User user = userService.getCurrentUser();
        Optional<Exam> exam = examRepository.findByAuthorAndResult(user, null);
        if (exam.isPresent()) {
            return ResponseEntity.ok(examMapper.examToStartExamModel(exam.get()));
        }
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> finish(FinishExamModel finishExamModel) {
        Exam exam = examRepository.findById(finishExamModel.getId()).get();
        List<FinishAttemptModel> attempts = finishExamModel.getAttempts();
        Timestamp endDate = new Timestamp(new java.util.Date().getTime());
        Integer result = 0;
        for ( FinishAttemptModel attempt : attempts ) {
            Attempt examAttempt = attemptRepository.findById(attempt.getAttemptId()).get();
            List<AttemptAnswer> correctAnswers = examAttempt.getAnswers().stream().filter(AttemptAnswer::getStatus).collect(Collectors.toList());
            List<Integer> answers = attempt.getAnswerList();
            if (attemptService.verifyAnswers(correctAnswers, answers)) {
                result++;
            }
        }
        exam.getAttempts().forEach(attempt -> attemptRepository.deleteById(attempt.getId()));
        exam.setAttempts(null);
        exam.setResult(result);
        exam.setEndDate(endDate);
        examRepository.save(exam);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<?> getExams() {
        User user = userService.getCurrentUser();
        List<Exam> exams = examRepository.findAllByAuthor(user)
                                                    .stream()
                                                    .filter(exam -> exam.getResult() != null)
                                                    .collect(Collectors.toList());
        return ResponseEntity.ok(examMapper.examListToGetExamModelList(exams));
    }

}
