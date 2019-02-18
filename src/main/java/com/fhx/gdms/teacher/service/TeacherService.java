package com.fhx.gdms.teacher.service;


import com.fhx.gdms.teacher.model.TeacherModel;

public interface TeacherService {
    /**
     * 根据用户名及密码查找用户
     * @param name
     * @param password
     * @return
     */
    TeacherModel findByNameAndPassword(String name, String password);
}
