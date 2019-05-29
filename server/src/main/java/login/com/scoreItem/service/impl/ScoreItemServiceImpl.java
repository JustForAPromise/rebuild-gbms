package login.com.scoreItem.service.impl;

import login.com.scoreItem.model.ScoreItemModel;
import login.com.scoreItem.repository.ScoreItemRepository;
import login.com.scoreItem.service.ScoreItemService;
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
    public List<ScoreItemModel> findAlive(Integer type) {
        return scoreItemRepository.findAlive(type);
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
