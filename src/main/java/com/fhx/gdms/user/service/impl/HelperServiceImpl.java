package com.fhx.gdms.user.service.impl;

import com.fhx.gdms.user.model.UserModel;
import com.fhx.gdms.user.repository.AdminRepository;
import com.fhx.gdms.user.repository.HelperRepository;
import com.fhx.gdms.user.service.AdminService;
import com.fhx.gdms.user.service.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelperServiceImpl implements HelperService {
    @Autowired
    private HelperRepository helperRepository;

    @Override
    public UserModel findByNameAndPassword(String name, String password) {
        UserModel model = new UserModel();
        model.setName(name);
        model.setPassword(password);
        model.setSenateMembers(true);

        model = helperRepository.findByNameAndPassword(model);

        return model;
    }
}
