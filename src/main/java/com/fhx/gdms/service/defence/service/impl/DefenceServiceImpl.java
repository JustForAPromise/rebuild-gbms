package com.fhx.gdms.service.defence.service.impl;

import com.fhx.gdms.service.defence.model.DefenceModel;
import com.fhx.gdms.service.defence.repository.DefenceRepository;
import com.fhx.gdms.service.defence.service.DefenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefenceServiceImpl implements DefenceService {

    @Autowired
    private DefenceRepository defenceRepository;

    @Override
    public DefenceModel save(DefenceModel model) {
        return null;
    }

    @Override
    public DefenceModel update(DefenceModel model) {
        return null;
    }

    @Override
    public DefenceModel saveTeacher(DefenceModel model) {
        return null;
    }

    @Override
    public DefenceModel updateTeacher(DefenceModel model) {
        return null;
    }

    @Override
    public List<DefenceModel> findAll() {
        return null;
    }

    @Override
    public List<DefenceModel> findTeacher(DefenceModel model) {
        return null;
    }
}
