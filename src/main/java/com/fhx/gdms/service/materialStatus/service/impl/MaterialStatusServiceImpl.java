package com.fhx.gdms.service.materialStatus.service.impl;

import com.fhx.gdms.service.materialStatus.model.MaterialStatusModel;
import com.fhx.gdms.service.materialStatus.repository.MaterialStatusRepository;
import com.fhx.gdms.service.materialStatus.service.MaterialStatusService;
import com.fhx.gdms.service.projections.service.ProjectionService;
import com.fhx.gdms.service.user.service.StudentService;
import com.fhx.gdms.service.user.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialStatusServiceImpl implements MaterialStatusService {
    @Autowired
    private MaterialStatusRepository materialStatusRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ProjectionService projectionService;

    @Override
    public List<MaterialStatusModel> findList(MaterialStatusModel model) {
        List<MaterialStatusModel> list =  materialStatusRepository.findList(model);
        list.stream().forEach(data ->{
            data.setStudent(studentService.findById(data.getStudentId()));
            data.setTeacher(teacherService.findById(data.getTeacherId()));
            data.setProjection(projectionService.findById(data.getProjectionId()));
        });

        return list;
    }

    @Override
    public MaterialStatusModel findOne(MaterialStatusModel materialStatus) {
        return materialStatusRepository.findOne(materialStatus);
    }

    @Override
    public void saveStatus(MaterialStatusModel materialStatusModel) {

        MaterialStatusModel existModel = materialStatusRepository.findOne(materialStatusModel);
        if (existModel == null){
            materialStatusRepository.save(materialStatusModel);
        }else{
            materialStatusRepository.update(materialStatusModel);
        }
    }

    @Override
    public void updateStatus(MaterialStatusModel materialStatusModel) {
        materialStatusRepository.update(materialStatusModel);
    }

}
