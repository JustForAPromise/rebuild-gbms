package com.fhx.gdms.user.service.impl;

import com.fhx.gdms.user.model.UserModel;
import com.fhx.gdms.user.repository.UserRepository;
import com.fhx.gdms.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserModel save(UserModel model) {
        userRepository.save(model);

        return this.findById(model.getId());
    }

    @Override
    public UserModel findByNoAndDepartmentId(String no, Integer departmentId) {
        UserModel model = new UserModel();
        model.setNo(no);
        model.setDepartmentId(departmentId);

        return userRepository.findByNoAndDepartmentId(model);
    }

    @Override
    public UserModel findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public UserModel findOne(UserModel model) {
        return userRepository.findOne(model);
    }

    @Override
    public void updatePowerById(Integer id, Integer powerId) {
        userRepository.updatePowerById(id, powerId);
    }

    @Override
    public UserModel updatePwd(Integer id, String password) {
        userRepository.updatePwd(id, password);

        return this.findById(id);
    }

    @Override
    public UserModel addSupports(UserModel model) {
        UserModel existModel = userRepository.findByNoAndDepartmentId(model);
        if (existModel != null){
            return null;
        }
        model.setIdentify(3);

        return this.save(model);
    }
}
