package com.fhx.gdms.service.user.service.impl;

import com.fhx.gdms.service.department.service.DepartmentService;
import com.fhx.gdms.service.user.model.UserModel;
import com.fhx.gdms.service.user.repository.TeacherRepository;
import com.fhx.gdms.service.user.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private DepartmentService departmentService;

    @Override
    public UserModel findByNoAndPasswd(String no, String password) {
        UserModel model = new UserModel();
        model.setNo(no);
        model.setPassword(password);
        model = teacherRepository.findByNoAndPassword(model);

        return model;
    }

    @Override
    public List<UserModel> findList(UserModel model) {
        return  teacherRepository.findList(model);
    }

    @Override
    public UserModel findByNameAndPassword(String name, String password) {
        UserModel model = new UserModel();
        model.setName(name);
        model.setPassword(password);
        model = teacherRepository.findByNameAndPassword(model);

        return model;
    }

    @Override
    public UserModel save(UserModel model) {
        teacherRepository.save(model);

        return this.findById(model.getId());
    }

    @Override
    public UserModel update(UserModel model) {
        teacherRepository.update(model);

        return this.findById(model.getId());
    }


    @Override
    public UserModel saveTeacher(UserModel model) {

        UserModel existModel = this.findByNo(model.getNo());
        if (existModel != null){
            return null;
        }

        model.setIdentify(1);
        model = this.save(model);
        model = getMoreInfo(model);

        return model;
    }

    @Override
    public UserModel updateTeacher(UserModel model) {
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
    public List<UserModel> findTeacher(UserModel model) {
        List<UserModel> results =  this.findList(model);

        results.stream().forEach(data ->{
            data = getMoreInfo(data);
        });

        return results;
    }

    @Override
    public UserModel findByNo(String no) {
        return teacherRepository.findByNo(no);
    }

    @Override
    public UserModel findById(Integer id) {
        return teacherRepository.findById(id);
    }

    @Override
    public List<UserModel> findByDepartmentId(Integer departmentId) {
        List<UserModel> modelList = teacherRepository.findByDepartmentId(departmentId);

        modelList.stream().forEach(data ->{
            data = getMoreInfo(data);
        });

        return modelList;
    }

    @Override
    public void deleteById(Integer id) {
        teacherRepository.deleteById(id);
    }

    private UserModel getMoreInfo(UserModel model){
        model.setDepartmentModel(departmentService.findById(model.getDepartmentId()));

        return model;
    }


}
