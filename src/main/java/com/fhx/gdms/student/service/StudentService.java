package com.fhx.gdms.student.service;

import com.fhx.gdms.student.model.StudentModel;

import java.util.List;

public interface StudentService {

    /**
     *  保存（base）
     * @param model
     * @return
     */
    StudentModel save(StudentModel model);
    /**
     * 更新（base）
     * @param model
     * @return
     */
    StudentModel update(StudentModel model);
    
    /**
     * 根据用户名及密码查找用户
     * @param name
     * @param password
     * @return
     */
    StudentModel findByNameAndPassword(String name, String password);

    /**
     * 创建
     * @param model
     * @return
     */
    StudentModel saveStudent(StudentModel model);

    /**
     * 更新
     * @param model
     * @return
     */
    StudentModel updateStudent(StudentModel model);

    /**
     * 查询全部
     * @return
     */
    List<StudentModel> findAll();

    /**
     * 条件查找
     * @param model
     * @return
     */
    List<StudentModel> findStudent(StudentModel model);

    /**
     * 学号查询
     * @param no
     * @return
     */
    StudentModel findByNo(String no);

    /**
     * id查询
     * @param id
     * @return
     */
    StudentModel findById(Integer id);

    /**
     * 系id + 专业id 查询
     * @param departmentId
     * @param majorId
     * @return
     */
    List<StudentModel> findByMajorIdAndDepartmentId(Integer departmentId, Integer majorId);
}
