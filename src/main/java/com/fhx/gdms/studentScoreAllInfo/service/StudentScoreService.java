package com.fhx.gdms.studentScoreAllInfo.service;

import com.fhx.gdms.studentScoreAllInfo.data.StudentScoreData;
import com.fhx.gdms.studentScoreRecord.model.StudentScoreRecordModel;

import java.util.List;

public interface StudentScoreService {

    /**
     * 查找学生成绩记录 （所有信息)
     * @param studentId
     * @return
     */
    StudentScoreData findRecord(Integer studentId);

    /**
     * 成绩登记
     * @param model
     */
    void updateNum(StudentScoreRecordModel model);
}
