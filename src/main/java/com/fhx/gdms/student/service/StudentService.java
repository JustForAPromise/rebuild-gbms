package com.fhx.gdms.student.service;

import com.fhx.gdms.student.model.StudentModel;

public interface StudentService {
    /**
     * 根据用户名及密码查找用户
     * @param name
     * @param password
     * @return
     */
    StudentModel findByNameAndPassword(String name, String password);
}
