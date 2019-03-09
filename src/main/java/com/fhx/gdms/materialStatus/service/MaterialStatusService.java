package com.fhx.gdms.materialStatus.service;

import com.fhx.gdms.materialStatus.model.MaterialStatusModel;
import com.fhx.gdms.material.model.MaterialModel;
import com.fhx.gdms.user.model.UserModel;

import java.util.List;

public interface MaterialStatusService {
    /**
     * 状态保存
     * @param materialStatusModel
     */
    void saveStatus(MaterialStatusModel materialStatusModel);

    /***
     * 状态更新
     * @param materialStatusModel
     */
    void updateStatus(MaterialStatusModel materialStatusModel);

    /**
     * 条件查询
     * @param model
     * @return
     */
    List<MaterialStatusModel> findList(MaterialStatusModel model);
}
