package com.fhx.gdms.service.studentNumOfTeacher.service.impl;

import com.fhx.gdms.service.studentNumOfTeacher.model.StudentNumOfTeacherModel;
import com.fhx.gdms.service.studentNumOfTeacher.repository.StudentNumOfTeacherRepository;
import com.fhx.gdms.service.studentNumOfTeacher.service.StudentNumOfTeacherService;
import com.fhx.gdms.service.user.model.UserModel;
import com.fhx.gdms.service.user.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentNumOfTeacherServiceImpl implements StudentNumOfTeacherService {
    @Autowired
    private StudentNumOfTeacherRepository studentNumOfTeacherRepository;

    @Autowired
    private TeacherService teacherService;


    @Override
    public StudentNumOfTeacherModel save(StudentNumOfTeacherModel model) {
        studentNumOfTeacherRepository.save(model);

        return this.findById(model.getId());
    }

    @Override
    public StudentNumOfTeacherModel findById(Integer id) {
        return studentNumOfTeacherRepository.findById(id);
    }

    @Override
    public StudentNumOfTeacherModel update(StudentNumOfTeacherModel model) {
       studentNumOfTeacherRepository.update(model);

       return this.findById(model.getId());
    }

    @Override
    public List<StudentNumOfTeacherModel> findList(UserModel model) {
        List<StudentNumOfTeacherModel> results = new ArrayList<>();

        if (model.getNo() != null) {
            model.setNo("%" + model.getNo() + "%");
        }
        if (model.getName() != null) {
            model.setName("%" + model.getName() + "%");
        }
        List<UserModel> teacherList = teacherService.findList(model);
        teacherList.stream().forEach(data -> {
            StudentNumOfTeacherModel studentNumOfTeacherModel = this.findByTeacherId(data.getId());
            if (studentNumOfTeacherModel == null) {
                studentNumOfTeacherModel = new StudentNumOfTeacherModel();
                studentNumOfTeacherModel.setTeacherId(data.getId());

                studentNumOfTeacherModel = this.save(studentNumOfTeacherModel);
            }
            studentNumOfTeacherModel.setTeacherModel(data);
            results.add(studentNumOfTeacherModel);
        });

        return results;
    }

    @Override
    public StudentNumOfTeacherModel findByTeacherId(Integer teacherId) {
        return studentNumOfTeacherRepository.findByTeacherId(teacherId);
    }

}
