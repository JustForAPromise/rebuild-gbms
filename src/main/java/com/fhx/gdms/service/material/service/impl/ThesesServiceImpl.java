package com.fhx.gdms.service.material.service.impl;

import com.fhx.gdms.service.material.model.MaterialModel;
import com.fhx.gdms.service.material.repository.ThesesRepository;
import com.fhx.gdms.service.material.service.ThesesService;
import com.fhx.gdms.service.materialStatus.model.MaterialStatusModel;
import com.fhx.gdms.service.materialStatus.service.MaterialStatusService;
import com.fhx.gdms.service.projections.service.ProjectionService;
import com.fhx.gdms.service.user.model.UserModel;
import com.fhx.gdms.service.user.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Service
public class ThesesServiceImpl implements ThesesService {

    @Autowired
    private ThesesRepository thesesRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ProjectionService projectionService;

    @Autowired
    private MaterialStatusService materialStatusService;

    @Override
    public MaterialModel save(MaterialModel model) {
        thesesRepository.save(model);

        return this.findById(model.getId());
    }

    @Override
    public MaterialModel update(MaterialModel model) {
        thesesRepository.update(model);

        return this.findById(model.getId());
    }

    @Override
    public List<MaterialModel> findList(MaterialModel model) {
        return thesesRepository.findList(model);
    }

    @Transactional
    @Override
    public void saveTheses(MaterialModel thesesModel) {
        thesesModel.setAuditStatus(0);
        MaterialModel existModel = thesesRepository.findOne(thesesModel);
        if (existModel != null) {
            this.deleteThesesRecord(existModel);
        }

        thesesModel = this.save(thesesModel);

        //材料提交状态保存
        MaterialStatusModel materialStatusModel = new MaterialStatusModel();
        materialStatusModel.setStudentId(thesesModel.getStudentId());
        materialStatusModel.setTeacherId(thesesModel.getTeacherId());
        materialStatusModel.setProjectionId(thesesModel.getProjectionId());
        materialStatusModel.setThesesSubmitStatus(1);
        materialStatusModel.setThesesAuditStatus(0);

        materialStatusService.saveStatus(materialStatusModel);

    }

    @Override
    public MaterialModel findById(Integer id) {
        return thesesRepository.findById(id);
    }

    @Override
    public void deleteThesesRecord(MaterialModel existModel) {
        File file = new File(existModel.getFilePath());
        file.delete();

        this.deleteById(existModel.getId());
    }

    @Override
    public void deleteById(Integer id) {
        thesesRepository.deleteById(id);
    }

    @Override
    public List<MaterialModel> listTheses(UserModel teacher, UserModel student) {

        if (student.getNo() != null) {
            student.setNo("%" + student.getNo() + "%");
        }
        if (student.getName() != null) {
            student.setName("%" + student.getName() + "%");
        }
        student.setTeacherId(teacher.getId());
        List<Integer> studentIds = studentService.listStudentId(student);

        MaterialModel thesesModel = new MaterialModel();
        thesesModel.setStudentIds(studentIds);
        thesesModel.setTeacherId(teacher.getId());

        List<MaterialModel> thesesModelList = thesesRepository.findList(thesesModel);

        thesesModelList.stream().forEach(data -> {
            data.setStudent(studentService.findById(data.getStudentId()));
            data.setProjection(projectionService.findById(data.getProjectionId()));
        });
        return thesesModelList;
    }

    @Transactional
    @Override
    public MaterialModel updateAudit(Integer id, Integer status, String remark) {

        MaterialModel model = this.findById(id);
        if (model == null) {
            return null;
        }

        model.setAuditStatus(status);
        model.setAuditRemark(remark);
        model = this.update(model);

        //状态更新
        MaterialStatusModel materialStatusModel = new MaterialStatusModel();
        materialStatusModel.setStudentId(model.getStudentId());
        materialStatusModel.setTeacherId(model.getTeacherId());
        materialStatusModel.setProjectionId(model.getProjectionId());
        materialStatusModel.setThesesAuditStatus(status);

        materialStatusService.updateStatus(materialStatusModel);

        return model;
    }

    @Override
    public MaterialModel findOne(MaterialModel thesesModel) {
        return thesesRepository.findOne(thesesModel);
    }
}
