package com.fhx.gdms.studentScoreRecord.service;

import com.fhx.gdms.studentScoreRecord.model.StudentScoreRecordModel;

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
     * @return
     */
    List<StudentScoreRecordModel> ListByStudentId(Integer studentId);


}
