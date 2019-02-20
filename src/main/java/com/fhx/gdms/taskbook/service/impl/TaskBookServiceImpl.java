package com.fhx.gdms.taskbook.service.impl;

import com.fhx.gdms.taskbook.model.TaskBookModel;
import com.fhx.gdms.taskbook.repository.TaskBookRepository;
import com.fhx.gdms.taskbook.service.TaskBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskBookServiceImpl implements TaskBookService {
    @Autowired
    private TaskBookRepository taskBookRepository;

    @Override
    public TaskBookModel save(TaskBookModel model) {
        return null;
    }

    @Override
    public TaskBookModel update(TaskBookModel model) {
        return null;
    }

    @Override
    public TaskBookModel saveTeacher(TaskBookModel model) {
        return null;
    }

    @Override
    public TaskBookModel updateTeacher(TaskBookModel model) {
        return null;
    }

    @Override
    public List<TaskBookModel> findAll() {
        return null;
    }

    @Override
    public List<TaskBookModel> findTeacher(TaskBookModel model) {
        return null;
    }
}
