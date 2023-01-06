package com.backend.service;

import com.backend.dao.answer.Answer;
import com.backend.dao.question.QuestionRepository;
import com.backend.model.Message;
import com.backend.model.question.add.AddQuestionModel;
import com.backend.model.question.update.UpdateQuestionModel;
import com.backend.dao.question.Question;
import com.backend.mapper.QuestionMapper;
import com.backend.security.entities.User;
import com.backend.security.repositories.UserRepository;
import com.backend.type.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
@Transactional
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AttemptService attemptService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionMapper questionMapper;

    public ResponseEntity<?> add(AddQuestionModel addQuestionModel) {
        Question question = questionMapper.addQuestionModelToQuestion(addQuestionModel);
        question.setAuthor(userService.getCurrentUser());
        question.setCreationDate(new Date(new java.util.Date().getTime()));
        question.getAnswers().forEach(answer -> answer.setQuestion(question));
        questionRepository.save(question);
        return ResponseEntity.ok(new Message("Question added"));
    }

    public ResponseEntity<?> getAll(){
        User currentUser = userService.getCurrentUser();
        List<Question> questions;
        if (currentUser.getRole().equals(UserType.ADMIN.name())) {
            questions = questionRepository.findAll();
        } else {
            questions = questionRepository.findAllByAuthor(userService.getCurrentUser());
        }
        return ResponseEntity.ok(questionMapper.questionListToGetQuestionModelList(questions));
    }

    public ResponseEntity<?> update(UpdateQuestionModel updateQuestionModel) {
        Question question = questionMapper.updateQuestionModelToQuestion(updateQuestionModel);
        Question questionDto = questionRepository.findById(question.getId()).get();
        updateFields(question, questionDto);
        updateAnswers(question.getAnswers(), questionDto.getAnswers(), questionDto);
        questionRepository.save(questionDto);
        attemptService.createUpdatedQuestions(question);
        return ResponseEntity.ok(new Message("Question updated"));
    }

    public ResponseEntity<?> updateAuthor(Integer questionId, Integer authorId) {
        Question questionDto = questionRepository.findById(questionId).get();
        User user = userRepository.findById(authorId).get();
        questionDto.setAuthor(user);
        questionRepository.save(questionDto);
        return ResponseEntity.ok(new Message("Update author completed"));
    }

    public ResponseEntity<?> delete(Integer questionId) {
        Question question = questionRepository.findById(questionId).get();
        question.getAttempts().forEach(attempt -> attempt.setQuestion(null));
        questionRepository.save(question);
        questionRepository.deleteById(questionId);
        return ResponseEntity.ok(new Message("Question deleted"));
    }

    private void updateFields(Question newQuestion, Question oldQuestion) {
        oldQuestion.setContent(newQuestion.getContent());
        oldQuestion.setDescription(newQuestion.getDescription());
        oldQuestion.setType(newQuestion.getType());
    }

    private void updateAnswers(List<Answer> newAnswers, List<Answer> oldAnswers, Question question) {
        int size = oldAnswers.size();
        for(int i = 0 ; i < size ; i++) {
            Answer answer = oldAnswers.get(0);
            oldAnswers.remove(answer);
            answer.setQuestion(null);
        }
        newAnswers.forEach(answer -> {
            answer.setQuestion(question);
            oldAnswers.add(answer);
        });
    }

}
