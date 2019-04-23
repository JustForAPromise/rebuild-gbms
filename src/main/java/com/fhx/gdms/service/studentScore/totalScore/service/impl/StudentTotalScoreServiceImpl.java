package com.fhx.gdms.service.studentScore.totalScore.service.impl;

import com.fhx.gdms.service.studentScore.totalScore.model.StudentTotalScoreModel;
import com.fhx.gdms.service.studentScore.totalScore.repository.StudentTotalScoreRepository;
import com.fhx.gdms.service.studentScore.totalScore.service.StudentTotalScoreService;
import com.fhx.gdms.service.user.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StudentTotalScoreServiceImpl implements StudentTotalScoreService {
    @Autowired
    private StudentTotalScoreRepository studentTotalScoreRepository;


    @Override
    public StudentTotalScoreModel save(StudentTotalScoreModel model) {

        studentTotalScoreRepository.save(model);

        return this.findById(model.getId());
    }

    @Override
    public StudentTotalScoreModel update(StudentTotalScoreModel model) {
        model.setLevel(getLevel(model.getScoreNum()));

        studentTotalScoreRepository.updateModel(model);

        return this.findById(model.getId());
    }

    @Override
    public StudentTotalScoreModel findById(Integer id) {
        return studentTotalScoreRepository.findById(id);
    }

    @Override
    public StudentTotalScoreModel findByStudentMolel(UserModel studentModel) {
        StudentTotalScoreModel model = new StudentTotalScoreModel();
        model.setStudentId(studentModel.getId());
        model.setDepartmentId(studentModel.getDepartmentId());
        model.setMajorId(studentModel.getMajorId());

        StudentTotalScoreModel existModel  = studentTotalScoreRepository.findOne(model);
        if (existModel == null){
            model.setScoreNum(new BigDecimal(0));
            model.setStatus("---");

            existModel = this.save(model);
        }

        return existModel;
    }

    /********** 辅助程序 **************/
    private String getLevel(BigDecimal studentNum){

        String level = null;

        if (studentNum.doubleValue() >= 90) {
            level= "优秀";
        } else if (studentNum.doubleValue() >= 80) {
            level= "良好";
        } else if (studentNum.doubleValue() >= 70) {
            level="中等";
        } else if (studentNum.doubleValue() >= 60) {
            level="及格";
        } else {
            level="不及格";
        }

        return level;
    }
}