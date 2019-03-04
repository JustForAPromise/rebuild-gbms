package com.fhx.gdms.user.service;

import com.fhx.gdms.user.model.UserModel;

public interface UserService {

    /**
     * no + departmentId 查询
     * @param no
     * @param departmentId
     * @return
     */
    UserModel findByNoAndDepartmentId(String no, Integer departmentId);

    /**
     * id 查询
     * @param id
     * @return
     */
    UserModel findById(Integer id);

    /**
     * findOne
     * @param model
     * @return
     */
    UserModel findOne(UserModel model);

    /**
     * 更新用户 权限id
     * @param id
     * @param powerId
     */
    void updatePowerById(Integer id, Integer powerId);
}
