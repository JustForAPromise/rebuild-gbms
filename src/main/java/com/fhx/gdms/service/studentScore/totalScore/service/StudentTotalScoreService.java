package com.fhx.gdms.service.studentScore.totalScore.service;

import com.fhx.gdms.service.studentScore.totalScore.model.StudentTotalScoreModel;
import com.fhx.gdms.service.user.model.UserModel;

public interface StudentTotalScoreService {
    /**
     *  创建（base）
     * @param model
     * @return
     */
    StudentTotalScoreModel save(StudentTotalScoreModel model);

    /**
     * 更新（base）
     * @param model
     * @return
     */
    StudentTotalScoreModel update(StudentTotalScoreModel model);

    /**
     * id查询
     * @param id
     * @return
     */
    StudentTotalScoreModel findById(Integer id);

    /**
     * 根据学生信息查询记录
     * @param studentModel
     * @return
     */
    StudentTotalScoreModel findByStudentMolel(UserModel studentModel);
}