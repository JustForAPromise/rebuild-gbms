package com.fhx.gdms.service.studentNumOfTeacher.service;

import com.fhx.gdms.service.studentNumOfTeacher.model.StudentNumOfTeacherModel;
import com.fhx.gdms.service.user.model.UserModel;

import java.util.List;

public interface StudentNumOfTeacherService {
    /**
     * 查找教师
     * @param model
     * @return
     */
    List<StudentNumOfTeacherModel>  findList(UserModel model);

    /**
     * find by teacher id
     * @param teacherId
     * @return
     */
    StudentNumOfTeacherModel findByTeacherId(Integer teacherId);

    /**
     * 创建（base）
     * @param model
     * @return
     */
    StudentNumOfTeacherModel save(StudentNumOfTeacherModel model);

    /**
     * find by id
     * @param id
     * @return
     */
    StudentNumOfTeacherModel findById(Integer id);

    /**
     * 更新
     * @param model
     * @return
     */
    StudentNumOfTeacherModel update(StudentNumOfTeacherModel model);
}
