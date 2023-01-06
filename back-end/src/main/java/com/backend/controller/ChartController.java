package com.backend.controller;

import com.backend.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/charts")
public class ChartController {

    @Autowired
    private ChartService chartService;

    @GetMapping("/users")
    public ResponseEntity<?> getUsersCount(){
        return chartService.getUsersCount();
    }

    @GetMapping("/logins")
    public ResponseEntity<?> getLoginPerDay(){
        return chartService.getLoginPerDay();
    }

    @GetMapping("/new-users")
    public ResponseEntity<?> getNewUserNumber(){
        return chartService.getNewUserPerDay();
    }

    @GetMapping("/questions")
    public ResponseEntity<?> getQuestionsCount(){
        return chartService.getQuestionsCategorieCount();
    }

    @GetMapping("/responses")
    public ResponseEntity<?> getResponseCount(){
        return chartService.getResponseCount();
    }

    @GetMapping("/questions-per-day")
    public ResponseEntity<?> getQuestionPerDay(){
        return chartService.getResponseOnQuestionPerDay();
    }

}
