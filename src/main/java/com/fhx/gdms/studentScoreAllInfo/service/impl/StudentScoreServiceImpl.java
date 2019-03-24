package com.fhx.gdms.studentScoreAllInfo.service.impl;

import com.fhx.gdms.materialStatus.model.MaterialStatusModel;
import com.fhx.gdms.materialStatus.service.MaterialStatusService;
import com.fhx.gdms.projections.model.ProjectionModel;
import com.fhx.gdms.projections.service.ProjectionService;
import com.fhx.gdms.studentScoreAllInfo.api.SearchDetailApiGet;
import com.fhx.gdms.studentScoreAllInfo.data.StudentScoreData;
import com.fhx.gdms.studentScoreAllInfo.data.TotalScoreData;
import com.fhx.gdms.studentScoreAllInfo.service.StudentScoreService;
import com.fhx.gdms.studentScoreRecord.model.StudentScoreRecordModel;
import com.fhx.gdms.studentScoreRecord.repository.StudentScoreRecordRepository;
import com.fhx.gdms.studentScoreRecord.service.StudentScoreRecordService;
import com.fhx.gdms.user.model.UserModel;
import com.fhx.gdms.user.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StudentScoreServiceImpl implements StudentScoreService {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ProjectionService projectionService;

    @Autowired
    private MaterialStatusService materialStatusService;

    @Autowired
    private StudentScoreRecordService studentScoreRecordService;

    @Override
    public StudentScoreData findRecord(SearchDetailApiGet receiveData) {
        UserModel student = studentService.findById(receiveData.getStudentId());
        ProjectionModel projection = projectionService.findByUserIdAndTeacherId(student.getId(), student.getTeacherId());

        MaterialStatusModel materialStatus = new MaterialStatusModel();
        materialStatus.setStudentId(student.getId());
        materialStatus.setTeacherId(student.getTeacherId());
        materialStatus.setProjectionId(projection.getId());

        materialStatus = materialStatusService.findOne(materialStatus);

        List<StudentScoreRecordModel> scoreRecordList = studentScoreRecordService.ListByStudentId(student.getId(), receiveData.getType());

        StudentScoreData result = new StudentScoreData();
        result.setStudent(student);
        result.setProjectionModel(projection);
        result.setMaterialStatusModel(materialStatus);
        result.setScoreRecordModels(scoreRecordList);

        return result;
    }

    @Override
    public void updateNum(StudentScoreRecordModel model) {
        studentScoreRecordService.update(model);
    }

    @Override
    public StudentScoreData findScoreToStudent(UserModel student) {
        UserModel studentModel = studentService.findById(student.getId());

        ProjectionModel projection = projectionService.findByUserIdAndTeacherId(student.getId(), student.getTeacherId());

        MaterialStatusModel materialStatus = new MaterialStatusModel();
        materialStatus.setStudentId(student.getId());
        materialStatus.setTeacherId(student.getTeacherId());
        materialStatus.setProjectionId(projection.getId());

        materialStatus = materialStatusService.findOne(materialStatus);

        List<StudentScoreRecordModel> scoreRecordListOfOrdinary = studentScoreRecordService.ListByStudentId(student.getId(), 1);
        List<StudentScoreRecordModel> scoreRecordListOfReview = studentScoreRecordService.ListByStudentId(student.getId(), 2);
        List<StudentScoreRecordModel> scoreRecordListOfResponse = studentScoreRecordService.ListByStudentId(student.getId(), 3);

        TotalScoreData totalScoreData = new TotalScoreData();
        totalScoreData.setTotalScorenNum(new BigDecimal(0));
        scoreRecordListOfOrdinary.stream().forEach(data -> {
            totalScoreData.setTotalScorenNum(
                    totalScoreData.getTotalScorenNum().add(
                            new BigDecimal(data.getScoreNum())
                                    .multiply(
                                            new BigDecimal(data.getScoreItemModel().getScoreRate()).divide(new BigDecimal(100))))
            );
        });
        scoreRecordListOfReview.stream().forEach(data -> {
            totalScoreData.setTotalScorenNum(
                    totalScoreData.getTotalScorenNum().add(
                            new BigDecimal(data.getScoreNum())
                                    .multiply(
                                            new BigDecimal(data.getScoreItemModel().getScoreRate()).divide(new BigDecimal(100))))
            );
        });
        scoreRecordListOfResponse.stream().forEach(data -> {
            totalScoreData.setTotalScorenNum(
                    totalScoreData.getTotalScorenNum().add(
                            new BigDecimal(data.getScoreNum())
                                    .multiply(
                                            new BigDecimal(data.getScoreItemModel().getScoreRate()).divide(new BigDecimal(100))))
            );
        });

        if (totalScoreData.getTotalScorenNum().doubleValue() >= 90){
            totalScoreData.setLevel("优秀");
        }else if (totalScoreData.getTotalScorenNum().doubleValue() >= 80){
            totalScoreData.setLevel("良好");
        }else if (totalScoreData.getTotalScorenNum().doubleValue() >= 70){
            totalScoreData.setLevel("中等");
        }else if (totalScoreData.getTotalScorenNum().doubleValue() >= 60){
            totalScoreData.setLevel("及格");
        }else {
            totalScoreData.setLevel("不及格");
        }

        StudentScoreData result = new StudentScoreData();
        result.setStudent(studentModel);
        result.setProjectionModel(projection);
        result.setMaterialStatusModel(materialStatus);
        result.setScoreRecordListOfOrdinary(scoreRecordListOfOrdinary);
        result.setScoreRecordListOfReview(scoreRecordListOfReview);
        result.setScoreRecordListOfResponse(scoreRecordListOfResponse);
        result.setTotalScore(totalScoreData);

        return result;
    }
}
