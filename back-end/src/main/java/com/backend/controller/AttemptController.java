package com.backend.controller;

import com.backend.model.attempt.FinishAttemptModel;
import com.backend.service.AttemptService;
import com.backend.type.QuestionType;
import com.backend.validator.attempt.finish.ValidateFinishAttemptModel;
import com.backend.validator.attempt.skip.ValidateSkipAttempt;
import com.backend.validator.attempt.start.ValidateStartAttempt;
import com.backend.validator.valueOfEnum.ValueOfEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/attempts")
public class AttemptController {

    @Autowired
    private AttemptService attemptService;

    @PostMapping("/{type}/{option}")
    @ValidateStartAttempt
    public ResponseEntity<?> start(@PathVariable @ValueOfEnum(enumClass = QuestionType.class, message = "Type is invalid") String type, @PathVariable Boolean option){
        return attemptService.start(type, option);
    }

    @GetMapping("")
    public ResponseEntity<?> get(){
        return attemptService.get();
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        return attemptService.getAll();
    }

    @GetMapping("/updated")
    public ResponseEntity<?> getUpdated(){
        return attemptService.getAllUpdated();
    }

    @GetMapping("/updated-number")
    public ResponseEntity<?> getUpdatedNumber(){
        return attemptService.getUpdatedNumber();
    }

    @GetMapping("/{type}/{option}")
    public ResponseEntity<?> getNumberOfQuestion(@PathVariable @ValueOfEnum(enumClass = QuestionType.class, message = "Type is invalid") String type, @PathVariable Boolean option){
        return attemptService.numberOfQuestions(type, option);
    }

    @PutMapping("")
    public ResponseEntity<?> finish(@RequestBody @Valid @ValidateFinishAttemptModel FinishAttemptModel finishAttemptModel) {
        return attemptService.finish(finishAttemptModel);
    }

    @DeleteMapping("")
    @ValidateSkipAttempt
    public ResponseEntity<?> skip(){
        return attemptService.skip();
    }

}
