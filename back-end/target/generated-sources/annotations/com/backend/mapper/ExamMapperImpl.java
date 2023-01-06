package com.backend.mapper;

import com.backend.dao.exam.Exam;
import com.backend.model.exam.GetExamModel;
import com.backend.model.exam.StartExamModel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-15T20:58:05+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.10 (Amazon.com Inc.)"
)
@Component
public class ExamMapperImpl implements ExamMapper {

    @Autowired
    private AttemptMapper attemptMapper;

    @Override
    public StartExamModel examToStartExamModel(Exam exam) {
        if ( exam == null ) {
            return null;
        }

        StartExamModel startExamModel = new StartExamModel();

        startExamModel.setId( exam.getId() );
        startExamModel.setAttempts( attemptMapper.attemptListToStartAttemptModelList( exam.getAttempts() ) );

        return startExamModel;
    }

    @Override
    public GetExamModel examToGetExamModel(Exam exam) {
        if ( exam == null ) {
            return null;
        }

        GetExamModel getExamModel = new GetExamModel();

        getExamModel.setType( exam.getType() );
        getExamModel.setResult( exam.getResult() );
        getExamModel.setStartDate( exam.getStartDate() );
        getExamModel.setEndDate( exam.getEndDate() );

        return getExamModel;
    }

    @Override
    public List<GetExamModel> examListToGetExamModelList(List<Exam> exam) {
        if ( exam == null ) {
            return null;
        }

        List<GetExamModel> list = new ArrayList<GetExamModel>( exam.size() );
        for ( Exam exam1 : exam ) {
            list.add( examToGetExamModel( exam1 ) );
        }

        return list;
    }
}
