package com.backend.controller;

import com.backend.model.exam.FinishExamModel;
import com.backend.service.ExamService;
import com.backend.type.QuestionType;
import com.backend.validator.exam.finish.ValidateFinishExamModel;
import com.backend.validator.exam.start.ValidateStartExam;
import com.backend.validator.valueOfEnum.ValueOfEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/exams")
public class ExamController {

    @Autowired
    private ExamService examService;

    @PostMapping("/{questionType}")
    @ValidateStartExam
    public ResponseEntity<?> startExam(@PathVariable @ValueOfEnum(enumClass = QuestionType.class, message = "Type is invalid")  String questionType) {
        return examService.start(questionType);
    }

    @GetMapping("")
    public ResponseEntity<?> getStarted() {
        return examService.getStarted();
    }

    @GetMapping("/all")
    public ResponseEntity<?> getExams() {
        return examService.getExams();
    }

    @PutMapping("")
    public ResponseEntity<?> finishExam(@RequestBody @Valid @ValidateFinishExamModel FinishExamModel finishExamModel) {
        return examService.finish(finishExamModel);
    }

}
