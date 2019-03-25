package com.fhx.gdms.service.studentScoreRecord.service;

import com.fhx.gdms.service.studentScoreRecord.model.StudentScoreRecordModel;

import java.util.List;

public interface StudentScoreRecordService {

    /**
     * 创建（base）
     * @param model
     * @return
     */
    StudentScoreRecordModel save(StudentScoreRecordModel model);

    /**
     * 更新（base）
     * @param model
     * @return
     */
    StudentScoreRecordModel update(StudentScoreRecordModel model);

    /**
     * 创建记录
     * @param model
     * @return
     */
    StudentScoreRecordModel createRecord(StudentScoreRecordModel model);

    /**
     * id查询
     * @param id
     * @return
     */
    StudentScoreRecordModel findById(Integer id);

    /**
     * list by the id of student
     * @param studentId
     * @param type      类型 1指导教师 2批阅教师 3答辩教师
     * @return
     */
    List<StudentScoreRecordModel> ListByStudentId(Integer studentId, Integer type);


}
