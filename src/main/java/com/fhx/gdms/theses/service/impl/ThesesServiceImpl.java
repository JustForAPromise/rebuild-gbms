package com.fhx.gdms.theses.service.impl;

import com.fhx.gdms.theses.model.ThesesModel;
import com.fhx.gdms.theses.repository.ThesesRepository;
import com.fhx.gdms.theses.service.ThesesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThesesServiceImpl implements ThesesService {
    @Autowired
    private ThesesRepository thesesRepository;

    @Override
    public ThesesModel save(ThesesModel model) {
        return null;
    }

    @Override
    public ThesesModel update(ThesesModel model) {
        return null;
    }

    @Override
    public ThesesModel saveTeacher(ThesesModel model) {
        return null;
    }

    @Override
    public ThesesModel updateTeacher(ThesesModel model) {
        return null;
    }

    @Override
    public List<ThesesModel> findAll() {
        return null;
    }

    @Override
    public List<ThesesModel> findTeacher(ThesesModel model) {
        return null;
    }
}
