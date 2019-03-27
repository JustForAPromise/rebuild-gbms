package com.fhx.gdms.service.studentScoreAllInfo.service;

import com.fhx.gdms.service.studentScoreAllInfo.api.SearchDetailApiGet;
import com.fhx.gdms.service.studentScoreAllInfo.data.StudentScoreData;
import com.fhx.gdms.service.studentScoreRecord.model.StudentScoreRecordModel;
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
    void updateNum(StudentScoreRecordModel model);

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
}
