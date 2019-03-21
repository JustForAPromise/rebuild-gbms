package com.fhx.gdms.scoreItem.service;

import com.fhx.gdms.scoreItem.model.ScoreItemModel;
import com.fhx.gdms.user.model.UserModel;

import java.util.List;

public interface ScoreItemService {
    /**
     *  保存（base）
     * @param model
     * @return
     */
    ScoreItemModel save(ScoreItemModel model);

    /**
     * 创建
     * @param model
     * @return
     */
    Integer saveScoreItem(ScoreItemModel model);

    /**
     * itemName 查询
     * @param itemName
     * @return
     */
    ScoreItemModel findByItemName(String itemName);

    /**
     * 状态更新
     * @param model
     * @return
     */
    ScoreItemModel updateStatus(ScoreItemModel model);

    /**
     * list all
     * @return
     */
    List<ScoreItemModel> listAll();

    /**
     * id查询
     * @param id
     * @return
     */
    ScoreItemModel findById(Integer id);

    /**
     * 处于开启状态的评分项目
     * @return
     */
    List<ScoreItemModel> findAlive();

    /**
     * 更新
     * @param model
     * @return
     */
    ScoreItemModel updateItem(ScoreItemModel model);
}
