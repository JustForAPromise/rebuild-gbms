package com.fhx.gdms.service.power.service;

import login.com.power.model.PowerModel;
import login.com.user.model.UserModel;

import java.util.List;

public interface PowerService {
    /**
     *  保存（base）
     * @param model
     * @return
     */
    PowerModel save(PowerModel model);

    /**
     * 根据id查找记录
     * @param id
     * @return
     */
    PowerModel findById(Integer id);

    /**
     * 删除
     * @param id
     */
    PowerModel deleteById(Integer id);

    /**
     * 创建
     * @param model
     * @return
     */
    PowerModel savePower(UserModel model);

    /**
     * 系别id 查询
     * @param departmentId
     * @return
     */
    List<PowerModel> findByDepartmentId(Integer departmentId);

    /**
     * 更新（base）
     * @param model
     * @return
     */
    PowerModel update(PowerModel model);

    /**
     * userId 查询
     * @param userId
     * @return
     */
    PowerModel findByUserId(Integer userId);

    /**
     * 创建记录
     * @param id
     * @param departmentId
     * @return
     */
    PowerModel createPower(Integer id, Integer departmentId);
}
