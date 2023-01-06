package com.backend.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class ChartService {

    public ResponseEntity<?> getUsersCount() {
        List<Map<String, Object>> dataList = new ArrayList<>();
        Random r = new Random();
        int low = 10;
        int high = 100;
        int teachers = r.nextInt(high-low) + low;
        Map<String, Object> data = new HashMap<>();
        data.put("y", teachers);
        data.put("label", "teachers");
        dataList.add(data);
        low = 100;
        high = 150;
        int students = r.nextInt(high-low) + low;
        data = new HashMap<>();
        data.put("y", students);
        data.put("label", "students");
        dataList.add(data);
        return ResponseEntity.ok(dataList);
    }

    public ResponseEntity<?> getLoginPerDay() {
        List<Map<String, Object>> dataList = new ArrayList<>();
        for(int i = 0 ; i < 30 ; i++) {
            Map<String, Object> data = new HashMap<>();
            Random r = new Random();
            int low = 10;
            int high = 150;
            int number = r.nextInt(high-low) + low;
            LocalDate date = LocalDate.now();
            data.put("x", java.sql.Date.valueOf(date.minusDays(i)).toString());
            data.put("y", number);
            dataList.add(data);
        }
        return ResponseEntity.ok(dataList);
    }

    public ResponseEntity<?> getNewUserPerDay() {
        List<Map<String, Object>> dataList = new ArrayList<>();
        for(int i = 0 ; i < 30 ; i++) {
            Map<String, Object> data = new HashMap<>();
            Random r = new Random();
            int low = 10;
            int high = 150;
            int number = r.nextInt(high-low) + low;
            LocalDate date = LocalDate.now();
            data.put("x", Date.valueOf(date.minusDays(i)).toString());
            data.put("y", number);
            dataList.add(data);
        }
        return ResponseEntity.ok(dataList);
    }

    public ResponseEntity<?> getQuestionsCategorieCount() {
        List<Map<String, Object>> dataList = new ArrayList<>();
        Random r = new Random();
        int low = 10;
        int high = 150;
        int informatics = r.nextInt(high-low) + low;
        Map<String, Object> data = new HashMap<>();
        data.put("y", informatics);
        data.put("label", "informatics");
        dataList.add(data);
        int physics = r.nextInt(high-low) + low;
        data = new HashMap<>();
        data.put("y", physics);
        data.put("label", "physics");
        dataList.add(data);
        int maths = r.nextInt(high-low) + low;
        data = new HashMap<>();
        data.put("y", maths);
        data.put("label", "maths");
        dataList.add(data);
        return ResponseEntity.ok(dataList);
    }

    public ResponseEntity<?> getResponseCount() {
        List<Map<String, Object>> dataList = new ArrayList<>();
        Random r = new Random();
        int low = 10;
        int high = 100;
        int teachers = r.nextInt(high-low) + low;
        Map<String, Object> data = new HashMap<>();
        data.put("y", teachers);
        data.put("label", "Passed");
        dataList.add(data);
        low = 100;
        high = 150;
        int students = r.nextInt(high-low) + low;
        data = new HashMap<>();
        data.put("y", students);
        data.put("label", "Failed");
        dataList.add(data);
        return ResponseEntity.ok(dataList);
    }

    public ResponseEntity<?> getResponseOnQuestionPerDay() {
        List<Map<String, Object>> dataList = new ArrayList<>();
        for(int i = 0 ; i < 30 ; i++) {
            Map<String, Object> data = new HashMap<>();
            Random r = new Random();
            int low = 1;
            int high = 15;
            int number = r.nextInt(high-low) + low;
            LocalDate date = LocalDate.now();
            data.put("x", Date.valueOf(date.minusDays(i)).toString());
            data.put("y", number);
            dataList.add(data);
        }
        return ResponseEntity.ok(dataList);
    }

}
