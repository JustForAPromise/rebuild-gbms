package com.fhx.gdms.service.user.service.impl;

import com.fhx.gdms.service.user.model.UserModel;
import com.fhx.gdms.service.user.repository.HelperRepository;
import com.fhx.gdms.service.user.service.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelperServiceImpl implements HelperService {
    @Autowired
    private HelperRepository helperRepository;

    @Override
    public UserModel findByNameAndPassword(String name, String password) {
        UserModel model = new UserModel();
        model.setPassword(password);
        model.setName(name);
        model = helperRepository.findByNameAndPassword(model);

        return model;
    }

    @Override
    public UserModel findByNoAndPasswd(String no, String password) {
        UserModel model = new UserModel();
        model.setPassword(password);
        model.setNo(no);
        model = helperRepository.findByNoAndPassword(model);

        return model;
    }
}
