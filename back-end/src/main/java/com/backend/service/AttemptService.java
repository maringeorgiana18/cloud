package com.backend.service;

import com.backend.dao.attempt.Attempt;
import com.backend.dao.attempt.AttemptRepository;
import com.backend.dao.attemptAnswer.AttemptAnswer;
import com.backend.dao.question.QuestionRepository;
import com.backend.dao.question.Question;
import com.backend.mapper.AttemptMapper;
import com.backend.model.attempt.FinishAttemptModel;
import com.backend.security.entities.User;
import com.backend.security.repositories.UserRepository;
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
public class AttemptService {

    @Autowired
    private AttemptRepository attemptRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttemptMapper attemptMapper;

    public ResponseEntity<?> start(String type, Boolean option){
        User user = userService.getCurrentUser();
        Optional<Question> question = questionRepository.startQuestion(type, user.getId(), option);
        if (question.isPresent()) {
            Timestamp startDate = new Timestamp(new java.util.Date().getTime());
            Attempt attempt = attemptMapper.questionToAttempt(question.get(), user, startDate, HistoryStatus.STARTED.name());
            attempt.getAnswers().forEach(attemptAnswer -> attemptAnswer.setAttempt(attempt));
            return ResponseEntity.ok(attemptMapper.attemptToStartAttemptModel(attemptRepository.save(attempt)));
        }
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> get() {
        User user = userService.getCurrentUser();
        Optional<Attempt> attempt = attemptRepository.findByAuthorAndHistoryStatus(user, HistoryStatus.STARTED.name());
        if (attempt.isPresent()) {
            return ResponseEntity.ok(attemptMapper.attemptToStartAttemptModel(attempt.get()));
        }
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> getAll() {
        User user = userService.getCurrentUser();
        List<Attempt> attempts = attemptRepository.findAllByAuthor(user)
                .stream()
                .filter(attempt -> !attempt.getHistoryStatus().equals(HistoryStatus.UPDATED.name()) && !attempt.getHistoryStatus().equals(HistoryStatus.EXAM.name()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(attemptMapper.attemptListToGetAttemptModelList(attempts));
    }

    public ResponseEntity<?> getAllUpdated() {
        User user = userService.getCurrentUser();
        List<Attempt> attempts = attemptRepository.findAllByAuthorAndHistoryStatus(user, HistoryStatus.UPDATED.name());
        return ResponseEntity.ok(attemptMapper.attemptListToStartAttemptModelList(attempts));
    }

    public ResponseEntity<?> skip() {
        User user = userService.getCurrentUser();
        Optional<Attempt> attempt = attemptRepository.findByAuthorAndHistoryStatus(user, HistoryStatus.STARTED.name());
        if (attempt.isPresent()) {
            Attempt attemptDTO = attempt.get();
            attemptDTO.setHistoryStatus(HistoryStatus.SKIPPED.name());
            attemptRepository.save(attemptDTO);
        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> finish(FinishAttemptModel finishAttemptModel) {
        Attempt attempt = attemptRepository.findById(finishAttemptModel.getAttemptId()).get();
        List<AttemptAnswer> correctAnswers = attempt.getAnswers().stream().filter(AttemptAnswer::getStatus).collect(Collectors.toList());
        List<Integer> answers = finishAttemptModel.getAnswerList();
        Boolean result = verifyAnswers(correctAnswers, answers);
        modifyAttemptStatus(attempt, result, answers);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<?> getUpdatedNumber() {
        User user = userService.getCurrentUser();
        Integer numberOfUpdated = attemptRepository.countAllByAuthorAndHistoryStatus(user, HistoryStatus.UPDATED.name());
        return ResponseEntity.ok(numberOfUpdated);
    }

    public ResponseEntity<?> numberOfQuestions(String type, boolean option) {
        User user = userService.getCurrentUser();
        Integer numberOfUpdated = questionRepository.numberOfAvailableQuestion(type, user.getId(), option);
        return ResponseEntity.ok(numberOfUpdated);
    }

    public void createUpdatedQuestions(Question question) {
        attemptRepository.deleteAllByQuestionAndHistoryStatus(question, HistoryStatus.UPDATED.name());
        List<User> users = userRepository.findAllByQuestions(question.getId());
        Timestamp startDate = new Timestamp(new java.util.Date().getTime());
        List<Attempt> attempts = new ArrayList<>();
        users.forEach(user -> {
            if (user != null) {
                Attempt attempt = attemptMapper.questionToAttempt(question, user, startDate, HistoryStatus.UPDATED.name());
                attempt.getAnswers().forEach(attemptAnswer -> attemptAnswer.setAttempt(attempt));
                attempts.add(attempt);
            }
        });
        attemptRepository.saveAll(attempts);
    }

    public boolean verifyAnswers(List<AttemptAnswer> correctAnswers, List<Integer> answers) {
        if (answers.size() == correctAnswers.size()) {
            for(AttemptAnswer answer : correctAnswers) {
                answers = answers.stream().filter(answerId -> !answer.getId().equals(answerId)).collect(Collectors.toList());
            }
            return answers.size() == 0;
        }
        return false;
    }

    private void modifyAttemptStatus(Attempt attempt, Boolean result, List<Integer> answers) {
        attempt.setStatus(result);
        attempt.setEndDate(new Timestamp(new java.util.Date().getTime()));
        attempt.setHistoryStatus(HistoryStatus.FINISHED.name());
        attempt.getAnswers().forEach(attemptAnswer -> attemptAnswer.setSelected(answers.contains(attemptAnswer.getId())));
        attemptRepository.save(attempt);
    }

}
