package com.fhx.gdms.theses.service.impl;

import com.fhx.gdms.theses.model.ThesesModel;
import com.fhx.gdms.theses.repository.ThesesRepository;
import com.fhx.gdms.theses.service.ThesesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class ThesesServiceImpl implements ThesesService {

    @Autowired
    private ThesesRepository thesesRepository;

    @Override
    public ThesesModel save(ThesesModel model) {
        thesesRepository.save(model);

        return this.findById(model.getId());
    }

    @Override
    public ThesesModel update(ThesesModel model) {
        return null;
    }

    @Override
    public List<ThesesModel> findList(ThesesModel model) {
        return thesesRepository.findList(model);
    }

    @Override
    public void saveTheses(ThesesModel taskBookModel) {
        ThesesModel existModel = thesesRepository.findOne(taskBookModel);
        if (existModel != null) {
            this.deleteThesesRecord(existModel);
        }

        this.save(taskBookModel);
    }

    @Override
    public ThesesModel findById(Integer id) {
        return thesesRepository.findById(id);
    }

    @Override
    public void deleteThesesRecord(ThesesModel existModel) {
        File file = new File(existModel.getFilePath());
        file.delete();

        this.deleteById(existModel.getId());
    }

    @Override
    public void deleteById(Integer id) {
        thesesRepository.deleteById(id);
    }

}
