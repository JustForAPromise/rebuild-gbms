package com.fhx.gdms.teacher.service;


import com.fhx.gdms.teacher.model.TeacherModel;

import java.util.List;

public interface TeacherService {
    /**
     * 根据用户名及密码查找用户
     * @param name
     * @param password
     * @return
     */
    TeacherModel findByNameAndPassword(String name, String password);

    /**
     *  保存（base）
     * @param model
     * @return
     */
    TeacherModel save(TeacherModel model);
    /**
     * 更新（base）
     * @param model
     * @return
     */
    TeacherModel update(TeacherModel model);

    /**
     * 创建
     * @param model
     * @return
     */
    TeacherModel saveTeacher(TeacherModel model);

    /**
     * 更新
     * @param model
     * @return
     */
    TeacherModel updateTeacher(TeacherModel model);

    /**
     * 查询全部
     * @return
     */
    List<TeacherModel> findAll();

    /**
     * 条件查询
     * @param model
     * @return
     */
    List<TeacherModel> findTeacher(TeacherModel model);
}
