package com.fhx.gdms.service.power.service.impl;

import login.com.department.service.DepartmentService;
import login.com.power.model.PowerModel;
import login.com.power.repository.PowerRepository;
import login.com.power.service.PowerService;
import login.com.user.model.UserModel;
import login.com.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PowerServiceImpl implements PowerService {

    @Autowired
    private PowerRepository powerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;

    @Override
    public PowerModel save(PowerModel model) {
        powerRepository.save(model);

        return this.findById(model.getId());
    }

    @Override
    public PowerModel findById(Integer id) {
        PowerModel model = new PowerModel();
        model.setId(id);

        return  powerRepository.findById(model);
    }

    @Override
    public PowerModel update(PowerModel model) {
        powerRepository.update(model);

        return this.findById(model.getId());
    }

    @Override
    public PowerModel findByUserId(Integer userId) {
        return powerRepository.findByUserId(userId);
    }

    @Override
    public PowerModel createPower(Integer id, Integer departmentId) {
        PowerModel model = new PowerModel();
        model.setUserId(id);
        model.setDepartmentId(departmentId);
        return this.save(model);
    }

    @Override
    public List<PowerModel> findByDepartmentId(Integer departmentId) {
        List<PowerModel> results = powerRepository.findByDepartmentId(departmentId);

        results.stream().forEach(data ->{
            data.setUserModel(userService.findById(data.getUserId()));
            data.setDepartmentModel(departmentService.findById(data.getDepartmentId()));
        });

        return results;
    }

    @Override
    public PowerModel deleteById(Integer id) {
        PowerModel oldModel = this.findById(id);

        powerRepository.deleteById(id);

        return oldModel;
    }

    @Override
    public PowerModel savePower(UserModel model) {
        PowerModel existModel = this.findByUserId(model.getId());
        if (existModel != null){
            return null;
        }else{
            PowerModel temp = new PowerModel();
            temp.setUserId(model.getId());
            temp.setDepartmentId(model.getDepartmentId());
            temp =  this.save(temp);

            userService.updatePowerById(temp.getUserId(), temp.getId());

            return temp;
        }
    }

}
