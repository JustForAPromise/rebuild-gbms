package com.fhx.gdms.service.user.service.impl;

import com.fhx.gdms.service.user.model.UserModel;
import com.fhx.gdms.service.user.repository.UserRepository;
import com.fhx.gdms.service.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<UserModel> findSupportsByDepartmentId(Integer departmentId) {
        return userRepository.findSupportsByDepartmentId(departmentId);
    }

    @Override
    public UserModel updateSupports(UserModel model) {
        UserModel existModel = userRepository.findById(model.getId());
        if (existModel != null){
            if (existModel.getId() != model.getId()){
                return null;
            }
        }

        return this.update(model);
    }

    @Override
    public UserModel update(UserModel model) {
        userRepository.update(model);

        return this.findById(model.getId());
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
}
