package com.fhx.gdms.studentScoreAllInfo.data;

import com.fhx.gdms.materialStatus.model.MaterialStatusModel;
import com.fhx.gdms.projections.model.ProjectionModel;
import com.fhx.gdms.studentScoreRecord.model.StudentScoreRecordModel;
import com.fhx.gdms.user.model.UserModel;
import lombok.Data;

import java.util.List;

@Data
public class StudentScoreData {
    private UserModel student;

    private ProjectionModel projectionModel;

    private MaterialStatusModel materialStatusModel;

    private List<StudentScoreRecordModel> scoreRecordModels;

}
