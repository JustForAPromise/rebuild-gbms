package com.fhx.gdms.taskbook.service.impl;

import com.fhx.gdms.taskbook.model.TaskBookModel;
import com.fhx.gdms.taskbook.repository.TaskBookRepository;
import com.fhx.gdms.taskbook.service.TaskBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class TaskBookServiceImpl implements TaskBookService {
    @Autowired
    private TaskBookRepository taskBookRepository;

    @Override
    public TaskBookModel save(TaskBookModel model) {
        taskBookRepository.save(model);

        return this.findById(model.getId());
    }

    @Override
    public TaskBookModel update(TaskBookModel model) {
        return null;
    }

    @Override
    public List<TaskBookModel> findList(TaskBookModel model) {
        return taskBookRepository.findList(model);
    }

    @Override
    public void saveTaskBook(TaskBookModel taskBookModel) {
        TaskBookModel existModel = taskBookRepository.findOne(taskBookModel);
        if (existModel != null){
            this.deleteTackBookRecord(existModel);
        }

        this.save(taskBookModel);
    }

    @Override
    public TaskBookModel findById(Integer id) {
        return taskBookRepository.findById(id);
    }

    @Override
    public void deleteTackBookRecord(TaskBookModel existModel) {
        File file = new File(existModel.getFilePath());
        file.delete();

        this.deleteById(existModel.getId());
    }

    @Override
    public void deleteById(Integer id) {
        taskBookRepository.deleteById(id);
    }
}
