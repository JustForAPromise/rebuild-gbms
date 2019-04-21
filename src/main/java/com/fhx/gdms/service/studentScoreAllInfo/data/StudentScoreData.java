package com.fhx.gdms.service.studentScoreAllInfo.data;

import com.fhx.gdms.service.materialStatus.model.MaterialStatusModel;
import com.fhx.gdms.service.projections.model.ProjectionModel;
import com.fhx.gdms.service.itemScore.model.StudentItemScoreModel;
import com.fhx.gdms.service.user.model.UserModel;
import lombok.Data;

import java.util.List;

@Data
public class StudentScoreData {
    private UserModel student;

    private ProjectionModel projectionModel;

    private MaterialStatusModel materialStatusModel;

    private List<StudentItemScoreModel> scoreRecordModels;

    private List<StudentItemScoreModel> scoreRecordListOfOrdinary;

    private List<StudentItemScoreModel> scoreRecordListOfReview;

    private List<StudentItemScoreModel> scoreRecordListOfResponse;

    private TotalScoreData totalScore;


}
