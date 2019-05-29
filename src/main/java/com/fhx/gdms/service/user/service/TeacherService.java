package com.fhx.gdms.service.user.service;

import login.com.user.model.UserModel;

import java.util.List;

public interface TeacherService {
    /**
     * 根据用户名及密码查找用户
     * @param name
     * @param password
     * @return
     */
    UserModel findByNameAndPassword(String name, String password);
    /**
     *  保存（base）
     * @param model
     * @return
     */
    UserModel save(UserModel model);
    /**
     * 更新（base）
     * @param model
     * @return
     */
    UserModel update(UserModel model);


    /**
     * 创建
     * @param model
     * @return
     */
    UserModel saveTeacher(UserModel model);

    /**
     * 更新
     * @param model
     * @return
     */
    UserModel updateTeacher(UserModel model);

    /**
     * 条件查找
     * @param model
     * @return
     */
    List<UserModel> findTeacher(UserModel model);

    /**
     * 学号查询
     * @param no
     * @return
     */
    UserModel findByNo(String no);

    /**
     * id查询
     * @param id
     * @return
     */
    UserModel findById(Integer id);

    /**
     * 系id 查询
     * @param departmentId
     * @return
     */
    List<UserModel> findByDepartmentId(Integer departmentId);

    /**
     * 删除
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 登录（学工号 + 密码）
     * @param no
     * @param password
     * @return
     */
    UserModel findByNoAndPasswd(String no, String password);

    /**
     * find list
     * @param model
     * @return
     */
    List<UserModel> findList(UserModel model);

    /**
     * find total
     * @param model
     * @return
     */
    Integer findTotal(UserModel model);
}
