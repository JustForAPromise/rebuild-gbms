package com.fhx.gdms.service.user.service;

import login.com.user.model.UserModel;

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

    /**
     * 登录（学工号 + 密码）
     * @param no
     * @param password
     * @return
     */
    UserModel findByNoAndPasswd(String no, String password);
}
