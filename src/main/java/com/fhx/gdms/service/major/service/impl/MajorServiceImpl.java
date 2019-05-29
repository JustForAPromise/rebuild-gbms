package com.fhx.gdms.service.major.service.impl;

import login.com.major.model.MajorModel;
import login.com.major.repository.MajorRepository;
import login.com.major.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajorServiceImpl implements MajorService {

    @Autowired
    private MajorRepository majorRepository;

    @Override
    public List<MajorModel> findByDepartmentId(Integer departmentId) {
        List<MajorModel> result = majorRepository.findByDepartmentId(departmentId);

        return result;
    }

    @Override
    public MajorModel saveMajor(MajorModel model) {
        MajorModel existModel = this.findOneByName(model.getMajor());
        if (existModel != null){
            return null;
        }

        return this.save(model);
    }

    @Override
    public MajorModel updateMajor(MajorModel model) {
        MajorModel existModel = this.findOneByName(model.getMajor());
        if (existModel != null){
            if (existModel.getIntroduce() == null){
                return this.update(model);
            }else if (!existModel.getIntroduce().equals(model.getIntroduce())){
                return this.update(model);
            }else {
                return null;
            }
        }

        return this.update(model);
    }

    @Override
    public MajorModel findOneByName(String majorName) {
        return majorRepository.findByName(majorName);

    }

    @Override
    public MajorModel update(MajorModel model) {
        majorRepository.update(model);

        return this.findById(model.getId());
    }

    @Override
    public MajorModel findById(Integer id) {
        return majorRepository.findById(id);
    }

    @Override
    public MajorModel save(MajorModel model) {
        majorRepository.save(model);

        return this.findById(model.getId());
    }

    @Override
    public List<MajorModel> deleteMajorById(Integer id) {
        MajorModel model = this.findById(id);
        this.deleteById(id);

        return this.findByDepartmentId(model.getDepartmentId());
    }

    @Override
    public void deleteById(Integer id) {
        majorRepository.deleteById(id);
    }
}
