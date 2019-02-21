package com.fhx.gdms.student.service.impl;

import com.fhx.gdms.department.service.DepartmentService;
import com.fhx.gdms.major.service.MajorService;
import com.fhx.gdms.student.model.StudentModel;
import com.fhx.gdms.student.repository.StudentRepository;
import com.fhx.gdms.student.service.StudentService;
import com.fhx.gdms.teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private TeacherService teacherService;

    @Override
    public StudentModel save(StudentModel model) {
        studentRepository.save(model);

        return this.findById(model.getId());
    }

    @Override
    public StudentModel update(StudentModel model) {
        return null;
    }

    @Override
    public StudentModel findByNameAndPassword(String name, String password) {
        StudentModel model = new StudentModel();
        model.setName(name);
        model.setPassword(password);

        model = studentRepository.findByNameAndPassword(model);

        return model;
    }

    @Override
    public StudentModel saveStudent(StudentModel model) {
        StudentModel existModel = this.findByNo(model.getNo());
        if (existModel != null){
            return null;
        }

        return this.save(model);
    }

    @Override
    public StudentModel updateStudent(StudentModel model) {
        return null;
    }

    @Override
    public List<StudentModel> findAll() {
        return null;
    }

    @Override
    public List<StudentModel> findStudent(StudentModel model) {
        return null;
    }

    @Override
    public StudentModel findByNo(String no) {
        return studentRepository.findByNo(no);
    }

    @Override
    public StudentModel findById(Integer id) {
        return studentRepository.findById(id);
    }

    @Override
    public List<StudentModel> findByMajorIdAndDepartmentId(Integer departmentId, Integer majorId) {
        List<StudentModel> modelList = studentRepository.findByMajorIdAndDepartmentId(departmentId, majorId);

        modelList.stream().forEach(data ->{
            data.setDepartmentModel(departmentService.findById(data.getDepartmentId()));
            data.setMajorModel(majorService.findById(data.getMajorId()));
            if (data.getTeacherId() != null) {
                data.setTeacherModel(teacherService.findById(data.getTeacherId()));
            }
        });

        return modelList;
    }
}
