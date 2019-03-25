package com.fhx.gdms.service.studentScoreAllInfo.data;

import com.fhx.gdms.service.materialStatus.model.MaterialStatusModel;
import com.fhx.gdms.service.projections.model.ProjectionModel;
import com.fhx.gdms.service.studentScoreRecord.model.StudentScoreRecordModel;
import com.fhx.gdms.service.user.model.UserModel;
import lombok.Data;

import java.util.List;

@Data
public class StudentScoreData {
    private UserModel student;

    private ProjectionModel projectionModel;

    private MaterialStatusModel materialStatusModel;

    private List<StudentScoreRecordModel> scoreRecordModels;

    private List<StudentScoreRecordModel> scoreRecordListOfOrdinary;

    private List<StudentScoreRecordModel> scoreRecordListOfReview;

    private List<StudentScoreRecordModel> scoreRecordListOfResponse;

    private TotalScoreData totalScore;


}
