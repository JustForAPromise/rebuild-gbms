package com.fhx.gdms.service.defence.service;

import com.fhx.gdms.service.defence.model.DefenceModel;

import java.util.List;

public interface DefenceService {
    /**
     *  保存（base）
     * @param model
     * @return
     */
    DefenceModel save(DefenceModel model);
    /**
     * 更新（base）
     * @param model
     * @return
     */
    DefenceModel update(DefenceModel model);

    /**
     * 创建
     * @param model
     * @return
     */
    DefenceModel saveTeacher(DefenceModel model);

    /**
     * 更新
     * @param model
     * @return
     */
    DefenceModel updateTeacher(DefenceModel model);

    /**
     * 查询全部
     * @return
     */
    List<DefenceModel> findAll();

    /**
     * 条件查询
     * @param model
     * @return
     */
    List<DefenceModel> findTeacher(DefenceModel model);
}
