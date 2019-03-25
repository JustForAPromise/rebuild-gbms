package com.fhx.gdms.controller.admin.studentScoreAllInfo.service;

import com.fhx.gdms.controller.admin.studentScoreAllInfo.api.SearchDetailApiGet;
import com.fhx.gdms.controller.admin.studentScoreAllInfo.data.StudentScoreData;
import com.fhx.gdms.controller.student.studentScoreRecord.model.StudentScoreRecordModel;
import com.fhx.gdms.controller.student.user.model.UserModel;

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
}
