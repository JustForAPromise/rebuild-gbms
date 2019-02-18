package com.fhx.gdms.admin.service;

import com.fhx.gdms.admin.model.AdminModel;

public interface AdminService {
    /**
     * 根据用户名及密码查找用户
     * @param name
     * @param password
     * @return
     */
    AdminModel findByNameAndPassword(String name, String password);

    /**
     * id查找
     * @param id
     * @return
     */
    AdminModel findById(Integer id);
}
