package com.fhx.gdms.service.studentScoreAllInfo.service;

import com.fhx.gdms.service.studentScoreAllInfo.api.SearchDetailApiGet;
import com.fhx.gdms.service.studentScoreAllInfo.data.StudentScoreData;
import com.fhx.gdms.service.studentScore.itemScore.model.StudentItemScoreModel;
import com.fhx.gdms.service.user.model.UserModel;

import java.util.List;

public interface StudentScoreService {

    /**
     * 查找学生成绩记录
     * @param receiveData
     * @return
     */
    StudentScoreData findRecord(SearchDetailApiGet receiveData);

    /**
     * 成绩登记
     * @param model
     */
    void updateNum(StudentItemScoreModel model);

    /**
     * 学生查询成绩
     * @param student
     * @return
     */
    StudentScoreData findScoreToStudent(UserModel student);

    /**
     * 系主任 查看学生成绩信息
     * @param student
     * @return
     */
    List<StudentScoreData> findBaseInfoList(UserModel student);

    /**
     * 更新学生成绩
     * @param ids
     * @param scoreNums
     */
    void updateStudentScore(Integer[] ids, Integer[] scoreNums);
}
