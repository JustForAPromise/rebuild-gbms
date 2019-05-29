package login.com.user.service.impl;

import login.com.user.model.UserModel;
import login.com.user.repository.AdminRepository;
import login.com.user.service.AdminService;
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
