package com.fhx.gdms.user.service;

import com.fhx.gdms.user.model.UserModel;

public interface AdminService {
    /**
     * 根据用户名及密码查找用户
     * @param name
     * @param password
     * @return
     */
    UserModel findByNameAndPassword(String name, String password);

    /**
     * id查找
     * @param id
     * @return
     */
    UserModel findById(Integer id);
}
