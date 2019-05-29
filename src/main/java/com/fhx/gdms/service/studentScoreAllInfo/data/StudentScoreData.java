package com.fhx.gdms.service.studentScoreAllInfo.data;

import login.com.materialStatus.model.MaterialStatusModel;
import login.com.projections.model.ProjectionModel;
import login.com.studentScore.itemScore.model.StudentItemScoreModel;
import login.com.studentScore.totalScore.model.StudentTotalScoreModel;
import login.com.user.model.UserModel;
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


    private StudentTotalScoreModel totalScoreModel;
}
