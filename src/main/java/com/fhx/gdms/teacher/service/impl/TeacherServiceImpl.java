package com.fhx.gdms.teacher.service.impl;

import com.fhx.gdms.teacher.model.TeacherModel;
import com.fhx.gdms.teacher.repository.TeachjerRepository;
import com.fhx.gdms.teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeachjerRepository teachjerRepository;

    @Override
    public TeacherModel findByNameAndPassword(String name, String password) {
        TeacherModel model = new TeacherModel();
        model.setName(name);
        model.setPassword(password);

        model = teachjerRepository.findByNameAndPassword(model);

        return model;
    }
}
