package com.fhx.gdms.user.service.impl;

import com.fhx.gdms.department.service.DepartmentService;
import com.fhx.gdms.major.service.MajorService;
import com.fhx.gdms.user.model.UserModel;
import com.fhx.gdms.user.repository.StudentRepository;
import com.fhx.gdms.user.service.StudentService;
import com.fhx.gdms.user.service.TeacherService;
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
    public UserModel findByNameAndPassword(String name, String password) {
        UserModel model = new UserModel();
        model.setName(name);
        model.setPassword(password);
        model.setOrdinaryStudent(true);

        model = studentRepository.findByNameAndPassword(model);

        return model;
    }

    @Override
    public UserModel save(UserModel model) {
        studentRepository.save(model);

        return this.findById(model.getId());
    }

    @Override
    public UserModel update(UserModel model) {
        studentRepository.update(model);

        return this.findById(model.getId());
    }


    @Override
    public UserModel saveStudent(UserModel model) {
        UserModel existModel = this.findByNo(model.getNo());
        if (existModel != null){
            return null;
        }

        model.setIdentify(2);
        model.setOrdinaryStudent(true);
        model = this.save(model);
        model = getMoreInfo(model);

        return model;
    }

    @Override
    public UserModel updateStudent(UserModel model) {
        UserModel existModelWithNo = this.findByNo(model.getNo());
        if (existModelWithNo == null){
            model = this.update(model);
            model = getMoreInfo(model);

            return model;
        }
        if (existModelWithNo.getId() == model.getId()){
            model = this.update(model);
            model = getMoreInfo(model);

            return model;
        }else{
            return null;
        }
    }

    @Override
    public List<UserModel> findStudent(UserModel model) {
        List<UserModel> results =  studentRepository.findList(model);

        results.stream().forEach(data ->{
            data = getMoreInfo(data);
        });

        return results;
    }

    @Override
    public UserModel findByNo(String no) {
        return studentRepository.findByNo(no);
    }

    @Override
    public UserModel findById(Integer id) {
        return studentRepository.findById(id);
    }

    @Override
    public List<UserModel> findByMajorIdAndDepartmentId(Integer departmentId, Integer majorId) {
        List<UserModel> modelList = studentRepository.findByMajorIdAndDepartmentId(departmentId, majorId);

        modelList.stream().forEach(data ->{
            data = getMoreInfo(data);
        });

        return modelList;
    }

    @Override
    public void deleteById(Integer id) {
        studentRepository.deleteById(id);
    }

    private UserModel getMoreInfo(UserModel model){
        if (model.getTeacherId() != null) {
            model.setTeacherModel(teacherService.findById(model.getTeacherId()));
        }
        model.setDepartmentModel(departmentService.findById(model.getDepartmentId()));
        model.setMajorModel(majorService.findById(model.getMajorId()));

        return model;
    }
}
