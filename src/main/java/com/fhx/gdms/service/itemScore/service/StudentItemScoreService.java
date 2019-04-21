package com.fhx.gdms.service.itemScore.service;

import com.fhx.gdms.service.itemScore.model.StudentItemScoreModel;

import java.util.List;

public interface StudentItemScoreService {

    /**
     * 创建（base）
     * @param model
     * @return
     */
    StudentItemScoreModel save(StudentItemScoreModel model);

    /**
     * 更新（base）
     * @param model
     * @return
     */
    StudentItemScoreModel update(StudentItemScoreModel model);

    /**
     * 创建记录
     * @param model
     * @return
     */
    StudentItemScoreModel createRecord(StudentItemScoreModel model);

    /**
     * id查询
     * @param id
     * @return
     */
    StudentItemScoreModel findById(Integer id);

    /**
     * list by the id of student
     * @param studentId
     * @param type      类型 1指导教师 2批阅教师 3答辩教师
     * @return
     */
    List<StudentItemScoreModel> ListByStudentId(Integer studentId, Integer type);


}
