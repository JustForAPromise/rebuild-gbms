package com.fhx.gdms.theses.service;

import com.fhx.gdms.theses.model.ThesesModel;

import java.util.List;

public interface ThesesService {
    /**
     *  保存（base）
     * @param model
     * @return
     */
    ThesesModel save(ThesesModel model);
    /**
     * 更新（base）
     * @param model
     * @return
     */
    ThesesModel update(ThesesModel model);

    /**
     * 创建
     * @param model
     * @return
     */
    ThesesModel saveTeacher(ThesesModel model);

    /**
     * 更新
     * @param model
     * @return
     */
    ThesesModel updateTeacher(ThesesModel model);

    /**
     * 查询全部
     * @return
     */
    List<ThesesModel> findAll();

    /**
     * 条件查询
     * @param model
     * @return
     */
    List<ThesesModel> findTeacher(ThesesModel model);
}
