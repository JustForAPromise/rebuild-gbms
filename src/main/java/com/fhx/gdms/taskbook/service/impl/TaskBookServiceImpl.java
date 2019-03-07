package com.fhx.gdms.taskbook.service.impl;

import com.fhx.gdms.projections.service.ProjectionService;
import com.fhx.gdms.taskbook.model.TaskBookModel;
import com.fhx.gdms.taskbook.repository.TaskBookRepository;
import com.fhx.gdms.taskbook.service.TaskBookService;
import com.fhx.gdms.user.model.UserModel;
import com.fhx.gdms.user.service.StudentService;
import javafx.concurrent.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class TaskBookServiceImpl implements TaskBookService {
    @Autowired
    private TaskBookRepository taskBookRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ProjectionService projectionService;

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

    @Override
    public List<TaskBookModel> listTaskBook(UserModel teacher, UserModel student) {

        if (student.getNo() != null) {
            student.setNo("%" + student.getNo() + "%");
        }
        if (student.getName() != null) {
            student.setName("%" + student.getName() + "%");
        }
        student.setTeacherId(teacher.getId());
        List<Integer> studentIds = studentService.listStudentId(student);

        TaskBookModel taskBookModel = new TaskBookModel();
        taskBookModel.setStudentIds(studentIds);
        taskBookModel.setTeacherId(teacher.getId());

        List<TaskBookModel> taskBookModelList = taskBookRepository.findList(taskBookModel);

        taskBookModelList.stream().forEach(data ->{
            data.setStudent(studentService.findById(data.getStudentId()));
            data.setProjection(projectionService.findById(data.getProjectionId()));
        });
        return taskBookModelList;
    }

}
