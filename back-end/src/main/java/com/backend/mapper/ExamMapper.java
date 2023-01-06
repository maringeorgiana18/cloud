package com.backend.mapper;

import com.backend.dao.exam.Exam;
import com.backend.model.exam.GetExamModel;
import com.backend.model.exam.StartExamModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AttemptMapper.class})
@Component
public interface ExamMapper {
    StartExamModel examToStartExamModel(Exam exam);
    GetExamModel examToGetExamModel(Exam exam);
    List<GetExamModel> examListToGetExamModelList(List<Exam> exam);
}
