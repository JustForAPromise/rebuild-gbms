package com.fhx.gdms.service.studentScoreAllInfo.service.impl;

import com.fhx.gdms.service.materialStatus.model.MaterialStatusModel;
import com.fhx.gdms.service.materialStatus.service.MaterialStatusService;
import com.fhx.gdms.service.projections.model.ProjectionModel;
import com.fhx.gdms.service.projections.service.ProjectionService;
import com.fhx.gdms.service.scoreItem.model.ScoreItemModel;
import com.fhx.gdms.service.scoreItem.service.ScoreItemService;
import com.fhx.gdms.service.studentScore.totalScore.model.StudentTotalScoreModel;
import com.fhx.gdms.service.studentScoreAllInfo.api.SearchDetailApiGet;
import com.fhx.gdms.service.studentScoreAllInfo.data.StudentScoreData;
import com.fhx.gdms.service.studentScoreAllInfo.data.TotalScoreData;
import com.fhx.gdms.service.studentScoreAllInfo.service.StudentScoreService;
import com.fhx.gdms.service.studentScore.itemScore.model.StudentItemScoreModel;
import com.fhx.gdms.service.studentScore.itemScore.service.StudentItemScoreService;
import com.fhx.gdms.service.studentScore.totalScore.service.StudentTotalScoreService;
import com.fhx.gdms.service.user.model.UserModel;
import com.fhx.gdms.service.user.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentScoreServiceImpl implements StudentScoreService {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ProjectionService projectionService;

    @Autowired
    private MaterialStatusService materialStatusService;

    @Autowired
    private ScoreItemService scoreItemService;

    @Autowired
    private StudentItemScoreService studentItemScoreService;

    @Autowired
    private StudentTotalScoreService studentTotalScoreService;

    @Override
    public StudentScoreData findRecord(SearchDetailApiGet receiveData) {
        UserModel student = studentService.findById(receiveData.getStudentId());
        ProjectionModel projection = projectionService.findByUserIdAndTeacherId(student.getId(), student.getTeacherId());

        MaterialStatusModel materialStatus = new MaterialStatusModel();
        materialStatus.setStudentId(student.getId());
        materialStatus.setTeacherId(student.getTeacherId());
        materialStatus.setProjectionId(projection.getId());

        materialStatus = materialStatusService.findOne(materialStatus);

        List<StudentItemScoreModel> scoreRecordList = studentItemScoreService.ListByStudentId(student.getId(), receiveData.getType());

        StudentScoreData result = new StudentScoreData();
        result.setStudent(student);
        result.setProjectionModel(projection);
        result.setMaterialStatusModel(materialStatus);
        result.setScoreRecordModels(scoreRecordList);

        return result;
    }

    @Override
    public void updateNum(StudentItemScoreModel model) {
        studentItemScoreService.update(model);
    }

    @Override
    public StudentScoreData findScoreToStudent(UserModel student) {
        StudentScoreData result = new StudentScoreData();

        UserModel studentModel = studentService.findById(student.getId());
        ProjectionModel projection = projectionService.findByUserIdAndTeacherId(student.getId(), student.getTeacherId());
        if (projection != null) {
            MaterialStatusModel materialStatus = new MaterialStatusModel();
            materialStatus.setStudentId(student.getId());
            materialStatus.setTeacherId(student.getTeacherId());
            materialStatus.setProjectionId(projection.getId());

            materialStatus = materialStatusService.findOne(materialStatus);

            //指导老师得分
            List<StudentItemScoreModel> scoreRecordListOfOrdinary = studentItemScoreService.ListByStudentId(student.getId(), 1);

            //评阅老师得分
            List<StudentItemScoreModel> scoreRecordListOfReview = studentItemScoreService.ListByStudentId(student.getId(), 2);

            //答辩教师得分
            List<StudentItemScoreModel> scoreRecordListOfResponse = studentItemScoreService.ListByStudentId(student.getId(), 3);

            //总成绩
            StudentTotalScoreModel scoreTotal = studentTotalScoreService.findByStudentMolel(studentModel);

            //计算
            TotalScoreData totalScoreData = new TotalScoreData();
            totalScoreData.setTotalScorenNum(new BigDecimal(0));
            scoreRecordListOfOrdinary.stream().forEach(data -> {
                data.setRealScore(new BigDecimal(data.getScoreNum())
                        .multiply(
                                new BigDecimal(data.getScoreItemModel().getScoreRate()).divide(new BigDecimal(100)))
                );
                totalScoreData.setTotalScorenNum(
                        totalScoreData.getTotalScorenNum().add(
                                new BigDecimal(data.getScoreNum())
                                        .multiply(
                                                new BigDecimal(data.getScoreItemModel().getScoreRate()).divide(new BigDecimal(100))
                                        )
                        )
                );
            });
            scoreRecordListOfReview.stream().forEach(data -> {
                data.setRealScore(new BigDecimal(data.getScoreNum())
                        .multiply(
                                new BigDecimal(data.getScoreItemModel().getScoreRate()).divide(new BigDecimal(100))
                        )
                );
                totalScoreData.setTotalScorenNum(
                        totalScoreData.getTotalScorenNum().add(
                                new BigDecimal(data.getScoreNum())
                                        .multiply(
                                                new BigDecimal(data.getScoreItemModel().getScoreRate()).divide(new BigDecimal(100))
                                        )
                        )
                );
            });
            scoreRecordListOfResponse.stream().forEach(data -> {
                data.setRealScore(new BigDecimal(data.getScoreNum())
                        .multiply(
                                new BigDecimal(data.getScoreItemModel().getScoreRate()).divide(new BigDecimal(100))
                        )
                );

                totalScoreData.setTotalScorenNum(
                        totalScoreData.getTotalScorenNum().add(
                                new BigDecimal(data.getScoreNum())
                                        .multiply(
                                                new BigDecimal(data.getScoreItemModel().getScoreRate()).divide(new BigDecimal(100))
                                        )
                        )
                );
            });

            if (totalScoreData.getTotalScorenNum().doubleValue() >= 90) {
                totalScoreData.setLevel("优秀");
            } else if (totalScoreData.getTotalScorenNum().doubleValue() >= 80) {
                totalScoreData.setLevel("良好");
            } else if (totalScoreData.getTotalScorenNum().doubleValue() >= 70) {
                totalScoreData.setLevel("中等");
            } else if (totalScoreData.getTotalScorenNum().doubleValue() >= 60) {
                totalScoreData.setLevel("及格");
            } else {
                totalScoreData.setLevel("不及格");
            }

            result.setProjectionModel(projection);
            result.setMaterialStatusModel(materialStatus);
            result.setScoreRecordListOfOrdinary(scoreRecordListOfOrdinary);
            result.setScoreRecordListOfReview(scoreRecordListOfReview);
            result.setScoreRecordListOfResponse(scoreRecordListOfResponse);

            result.setScoreTotalModel(scoreTotal);

            result.setTotalScore(totalScoreData);
        }

        result.setStudent(studentModel);
        return result;
    }

    @Override
    public List<StudentScoreData> findBaseInfoList(UserModel student) {
        List<StudentScoreData> sortFirst = new ArrayList<>();
        List<StudentScoreData> sortSecond = new ArrayList<>();

        List<UserModel> studentList = studentService.findStudent(student);

        studentList.stream().forEach(data -> {
            StudentScoreData studentScoreData = this.findScoreToStudent(data);
            if (studentScoreData.getProjectionModel() != null) {
                sortFirst.add(studentScoreData);
            } else {
                sortSecond.add(studentScoreData);
            }
        });

        List<StudentScoreData> results = new ArrayList<>();
        results.addAll(sortFirst);
        results.addAll(sortSecond);

        return results;
    }

    @Override
    public void updateStudentScore(Integer[] ids, Integer[] scoreNums) {

    }

//    //待优化
//    @Override
//    public void updateStudentScore(Integer[] ids, Integer[] scoreNums) {
//
//        //总分数差
//        BigDecimal scoreDifference = new BigDecimal(0);
//
//        for (int i=0, j= ids.length; i < j; i++){
//            StudentItemScoreModel itemScoreModel = studentItemScoreService.findById(ids[i]);
//            ScoreItemModel scoreItemModel = scoreItemService.findById(itemScoreModel.getScoreItemId());
//
//            scoreDifference  = scoreDifference.add(
//                    new BigDecimal(scoreNums[i]).multiply(
//                            new BigDecimal(scoreItemModel.getScoreRate()).divide(new BigDecimal(100))
//                    ).subtract(
//                            new BigDecimal(itemScoreModel.getScoreNum())
//                                    .multiply(new BigDecimal(scoreItemModel.getScoreRate()).divide(new BigDecimal(100)))
//                    )
//            );
//
//            itemScoreModel.setScoreNum(scoreNums[i]);
//
//            studentItemScoreService.update(itemScoreModel);
//        }
//
//        StudentTotalScoreModel totalScoreModel = studentTotalScoreService.findByStudentMolel()
//    }
}
