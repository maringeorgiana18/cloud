package com.backend.controller;

import com.backend.model.question.add.AddQuestionModel;
import com.backend.model.question.update.UpdateQuestionModel;
import com.backend.validator.question.add.ValidateAddQuestionModel;
import com.backend.validator.question.delete.ValidateDeleteQuestion;
import com.backend.validator.question.update.ValidateUpdateQuestionModel;
import com.backend.service.QuestionService;
import com.backend.validator.question.updateAuthor.ValidateUpdateAuthor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("")
    public ResponseEntity<?> add(@RequestBody @Valid @ValidateAddQuestionModel AddQuestionModel addQuestionModel) {
        return questionService.add(addQuestionModel);
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return questionService.getAll();
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody @Valid @ValidateUpdateQuestionModel UpdateQuestionModel updateQuestionModel) {
        return questionService.update(updateQuestionModel);
    }

    @PutMapping("/author/{questionId}/{authorId}")
    @ValidateUpdateAuthor
    public ResponseEntity<?> updateAuthor(@PathVariable Integer questionId, @PathVariable Integer authorId){
        return questionService.updateAuthor(questionId, authorId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable @ValidateDeleteQuestion Integer id) {
        return questionService.delete(id);
    }

}
