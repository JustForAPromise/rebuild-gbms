package com.fhx.gdms.service.scoreItem.service;

import com.fhx.gdms.service.scoreItem.model.ScoreItemModel;

import java.util.List;
import java.util.Map;

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
     * @param type  类型 1指导教师 2批阅教师 3答辩教师
     * @return
     */
    List<ScoreItemModel> findAlive(Integer type);

    /**
     * 更新
     * @param model
     * @return
     */
    ScoreItemModel updateItem(ScoreItemModel model);

    /**
     * 统计分率
     * @return
     */
    List<Map<String, Integer>> countRate();
}
