package com.fhx.gdms.teacher.service.impl;

import com.fhx.gdms.teacher.model.TeacherModel;
import com.fhx.gdms.teacher.repository.TeacherRepository;
import com.fhx.gdms.teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public TeacherModel findByNameAndPassword(String name, String password) {
        TeacherModel model = new TeacherModel();
        model.setName(name);
        model.setPassword(password);

        model = teacherRepository.findByNameAndPassword(model);

        return model;
    }

    @Override
    public TeacherModel save(TeacherModel model) {
        return null;
    }

    @Override
    public TeacherModel update(TeacherModel model) {
        return null;
    }

    @Override
    public TeacherModel saveTeacher(TeacherModel model) {
        return null;
    }

    @Override
    public TeacherModel updateTeacher(TeacherModel model) {
        return null;
    }

    @Override
    public List<TeacherModel> findAll() {
        return null;
    }

    @Override
    public List<TeacherModel> findTeacher(TeacherModel model) {
        return null;
    }

    @Override
    public TeacherModel findById(Integer id) {
        return teacherRepository.findById(id);
    }
}
