package com.fhx.gdms.student.service.impl;

import com.fhx.gdms.student.model.StudentModel;
import com.fhx.gdms.student.repository.StudentRepository;
import com.fhx.gdms.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public StudentModel findByNameAndPassword(String name, String password) {
        StudentModel model = new StudentModel();
        model.setName(name);
        model.setPassword(password);

        model = studentRepository.findByNameAndPassword(model);

        return model;
    }
}
