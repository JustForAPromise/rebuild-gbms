package com.fhx.gdms.scoreItem.service.impl;

import com.fhx.gdms.scoreItem.model.ScoreItemModel;
import com.fhx.gdms.scoreItem.repository.ScoreItemRepository;
import com.fhx.gdms.scoreItem.service.ScoreItemService;
import com.fhx.gdms.user.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ScoreItemServiceImpl implements ScoreItemService {
    @Autowired
    private ScoreItemRepository scoreItemRepository;

    @Override
    public ScoreItemModel save(ScoreItemModel model) {
        scoreItemRepository.save(model);

        return this.findById(model.getId());
    }

    @Override
    public Integer saveScoreItem(ScoreItemModel model) {
        ScoreItemModel existModel = this.findByItemName(model.getItemName());
        if (existModel != null){
            return -1;
        }

        this.save(model);
        return 0;
    }

    @Override
    public ScoreItemModel findByItemName(String itemName) {
        return scoreItemRepository.findByItemName(itemName);
    }

    @Override
    public ScoreItemModel updateStatus(ScoreItemModel model) {
        scoreItemRepository.updateStatus(model);

        return this.findById(model.getId());
    }

    @Override
    public List<ScoreItemModel> listAll() {
        List<ScoreItemModel> listModel = scoreItemRepository.listAll();

        return listModel;
    }

    @Override
    public ScoreItemModel findById(Integer id) {
        ScoreItemModel model = scoreItemRepository.findById(id);
        return model;
    }

    @Override
    public List<ScoreItemModel> findAlive() {
        return scoreItemRepository.findAlive();
    }

    @Override
    public ScoreItemModel updateItem(ScoreItemModel model) {
        scoreItemRepository.updateItem(model);

        return this.findById(model.getId());
    }

    @Override
    public List<Map<String, Integer>> countRate() {
        return scoreItemRepository.countRate();
    }
}
