package com.fhx.gdms.service.materialStatus.service;

import com.fhx.gdms.service.materialStatus.model.MaterialStatusModel;

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

    /**
     * find one
     * @param materialStatus
     * @return
     */
    MaterialStatusModel findOne(MaterialStatusModel materialStatus);
}
