package com.fhx.gdms.controller.admin.studentScoreAllInfo.data;

import com.fhx.gdms.controller.student.materialStatus.model.MaterialStatusModel;
import com.fhx.gdms.controller.student.projections.model.ProjectionModel;
import com.fhx.gdms.controller.student.studentScoreRecord.model.StudentScoreRecordModel;
import com.fhx.gdms.controller.student.user.model.UserModel;
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
