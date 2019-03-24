package com.fhx.gdms.studentScoreAllInfo.data;

import com.fhx.gdms.materialStatus.model.MaterialStatusModel;
import com.fhx.gdms.projections.model.ProjectionModel;
import com.fhx.gdms.studentScoreRecord.model.StudentScoreRecordModel;
import com.fhx.gdms.user.model.UserModel;
import lombok.Data;

import java.util.List;
import java.util.Map;

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
