package com.fhx.gdms.service.user.service.impl;

import com.fhx.gdms.service.user.model.UserModel;
import com.fhx.gdms.service.user.repository.AdminRepository;
import com.fhx.gdms.service.user.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserModel findByNameAndPassword(String name, String password) {
        UserModel model = new UserModel();
        model.setName(name);
        model.setPassword(password);
        model = adminRepository.findByNameAndPassword(model);

        return model;
    }

    @Override
    public UserModel findById(Integer id) {
        return adminRepository.findById(id);
    }

    @Override
    public UserModel findByNoAndPasswd(String no, String password) {
        UserModel model = new UserModel();
        model.setNo(no);
        model.setPassword(password);
        model = adminRepository.findByNoAndPassword(model);

        return model;
    }
}