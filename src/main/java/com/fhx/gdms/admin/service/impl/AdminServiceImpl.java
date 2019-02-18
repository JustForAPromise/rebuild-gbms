package com.fhx.gdms.admin.service.impl;

import com.fhx.gdms.admin.model.AdminModel;
import com.fhx.gdms.admin.repository.AdminRepository;
import com.fhx.gdms.admin.service.AdminService;
import com.fhx.gdms.teacher.repository.TeachjerRepository;
import com.fhx.gdms.teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public AdminModel findByNameAndPassword(String name, String password) {
        AdminModel model = new AdminModel();
        model.setName(name);
        model.setPassword(password);

        model = adminRepository.findByNameAndPassword(model);

        return model;
    }

    @Override
    public AdminModel findById(Integer id) {
        return adminRepository.findById(id);
    }
}
