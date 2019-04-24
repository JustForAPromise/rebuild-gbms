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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

        List<StudentItemScoreModel> scoreRecordList = studentItemScoreService.listByStudentIdAndTeacherType(student.getId(), receiveData.getType());

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
            List<StudentItemScoreModel> scoreRecordListOfOrdinary = studentItemScoreService.listByStudentIdAndTeacherType(student.getId(), 1);

            //评阅老师得分
            List<StudentItemScoreModel> scoreRecordListOfReview = studentItemScoreService.listByStudentIdAndTeacherType(student.getId(), 2);

            //答辩教师得分
            List<StudentItemScoreModel> scoreRecordListOfResponse = studentItemScoreService.listByStudentIdAndTeacherType(student.getId(), 3);

            //总成绩
            StudentTotalScoreModel totalScoreModel = studentTotalScoreService.findByStudentId(student.getId());

            //计算
            TotalScoreData totalScoreData = new TotalScoreData();
            totalScoreData.setTotalScorenNum(new BigDecimal(0));
            scoreRecordListOfOrdinary.stream().forEach(data -> {
                data.setRealScore(new BigDecimal(data.getScoreNum())
                        .multiply(
                                new BigDecimal(data.getScoreItemModel().getScoreRate()).divide(new BigDecimal(100)))
                );
            });
            scoreRecordListOfReview.stream().forEach(data -> {
                data.setRealScore(new BigDecimal(data.getScoreNum())
                        .multiply(
                                new BigDecimal(data.getScoreItemModel().getScoreRate()).divide(new BigDecimal(100))
                        )
                );
            });
            scoreRecordListOfResponse.stream().forEach(data -> {
                data.setRealScore(new BigDecimal(data.getScoreNum())
                        .multiply(
                                new BigDecimal(data.getScoreItemModel().getScoreRate()).divide(new BigDecimal(100))
                        )
                );
            });

            result.setProjectionModel(projection);
            result.setMaterialStatusModel(materialStatus);
            result.setScoreRecordListOfOrdinary(scoreRecordListOfOrdinary);
            result.setScoreRecordListOfReview(scoreRecordListOfReview);
            result.setScoreRecordListOfResponse(scoreRecordListOfResponse);

            result.setTotalScoreModel(totalScoreModel);
        }

        result.setStudent(studentModel);
        return result;
    }

    @Override
    public List<StudentScoreData> findBaseInfoList(UserModel student) {
        //无排序
        List<StudentScoreData> sortInit = new ArrayList<>();

        //未选题
        List<StudentScoreData> sortFirst = new ArrayList<>();

        //未评分
        List<StudentScoreData> sortSecond = new ArrayList<>();

        //评分结果排序
        List<StudentScoreData> sortThird = new ArrayList<>();

        //暂存区
        List<StudentScoreData> temp = new ArrayList<>();

        List<UserModel> studentList = studentService.findStudent(student);

        studentList.stream().forEach(data -> {
            StudentScoreData studentScoreData = this.findScoreToStudent(data);
            sortInit.add(studentScoreData);
        });

        //添加未选题信息
        sortInit.stream().forEach(data->{
            if (data.getProjectionModel() == null){
                sortFirst.add(data);
            }else{
                temp.add(data);
            }
        });

        //已评分、未评分区分
        temp.stream().forEach(data ->{
            if (data.getTotalScoreModel() == null){
                sortSecond.add(data);
            }else {
                sortThird.add(data);
            }
        });

        Comparator<StudentScoreData> comparator = (h1, h2) -> h1.getTotalScoreModel().getScoreNum().compareTo(h2.getTotalScoreModel().getScoreNum());

        sortThird.sort(comparator.reversed());

        List<StudentScoreData> results = new ArrayList<>();
        results.addAll(sortThird);
        results.addAll(sortSecond);
        results.addAll(sortFirst);

        return results;
    }


    @Transactional
    @Override
    public void updateStudentScore(Integer studentId, Integer[] ids, Integer[] scoreNums, Integer type) {
        //总分数差
        BigDecimal scoreDifference = new BigDecimal(0);

        for (int i = 0, j = ids.length; i < j; i++) {
            StudentItemScoreModel itemScoreModel = new StudentItemScoreModel();
            itemScoreModel.setId(ids[i]);
            itemScoreModel.setStudentId(studentId);

            itemScoreModel = studentItemScoreService.findOne(itemScoreModel);

            if (itemScoreModel != null) {
                itemScoreModel.setScoreItemModel(scoreItemService.findById(itemScoreModel.getScoreItemId()));

                scoreDifference = scoreDifference.add(
                        new BigDecimal(scoreNums[i]).multiply(
                                new BigDecimal(itemScoreModel.getScoreItemModel().getScoreRate()).divide(new BigDecimal(100))
                        ).subtract(
                                new BigDecimal(itemScoreModel.getScoreNum())
                                        .multiply(new BigDecimal(itemScoreModel.getScoreItemModel().getScoreRate()).divide(new BigDecimal(100)))
                        )
                );

                itemScoreModel.setScoreNum(scoreNums[i]);
                studentItemScoreService.update(itemScoreModel);
            }
        }

        StudentTotalScoreModel totalScoreModel = studentTotalScoreService.findByStudentId(studentId);

        totalScoreModel.setScoreNum(totalScoreModel.getScoreNum().add(scoreDifference));
        StringBuffer status = new StringBuffer(totalScoreModel.getStatus());

        if (type == 1) {
            status.replace(0, 1, "O");
        } else if (type == 2) {
            status.replace(1, 2, "R");

        } else if (type == 3) {
            status.replace(2, 3, "P");
        }

        totalScoreModel.setStatus(status.toString());

        studentTotalScoreService.updateStudentScore(totalScoreModel);
    }
}
