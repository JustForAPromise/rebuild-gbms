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
     * 条件查询
     * @param model
     * @return
     */
    List<ThesesModel> findList(ThesesModel model);

    /**
     * 创建
     * @param model
     */
    void saveTheses(ThesesModel model);

    /**
     * id 查询
     * @param id
     * @return
     */
    ThesesModel findById(Integer id);

    /**
     * 删除提交记录(包括文件)
     * @param existModel
     */
    void deleteThesesRecord(ThesesModel existModel);

    /**
     * 删除记录
     * @param id
     */
    void deleteById(Integer id);
}
