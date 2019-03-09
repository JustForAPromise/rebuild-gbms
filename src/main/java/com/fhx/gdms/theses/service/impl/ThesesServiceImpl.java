package com.fhx.gdms.theses.service.impl;

import com.fhx.gdms.projections.service.ProjectionService;
import com.fhx.gdms.theses.model.ThesesModel;
import com.fhx.gdms.theses.repository.ThesesRepository;
import com.fhx.gdms.theses.service.ThesesService;
import com.fhx.gdms.user.model.UserModel;
import com.fhx.gdms.user.service.StudentService;
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

    @Override
    public ThesesModel save(ThesesModel model) {
        thesesRepository.save(model);

        return this.findById(model.getId());
    }

    @Override
    public ThesesModel update(ThesesModel model) {
        thesesRepository.update(model);

        return this.findById(model.getId());
    }

    @Override
    public List<ThesesModel> findList(ThesesModel model) {
        return thesesRepository.findList(model);
    }

    @Transactional
    @Override
    public void saveTheses(ThesesModel taskBookModel) {
        ThesesModel existModel = thesesRepository.findOne(taskBookModel);
        if (existModel != null) {
            this.deleteThesesRecord(existModel);
        }

        this.save(taskBookModel);
    }

    @Override
    public ThesesModel findById(Integer id) {
        return thesesRepository.findById(id);
    }

    @Override
    public void deleteThesesRecord(ThesesModel existModel) {
        File file = new File(existModel.getFilePath());
        file.delete();

        this.deleteById(existModel.getId());
    }

    @Override
    public void deleteById(Integer id) {
        thesesRepository.deleteById(id);
    }

    @Override
    public List<ThesesModel> listTheses(UserModel teacher, UserModel student) {

        if (student.getNo() != null) {
            student.setNo("%" + student.getNo() + "%");
        }
        if (student.getName() != null) {
            student.setName("%" + student.getName() + "%");
        }
        student.setTeacherId(teacher.getId());
        List<Integer> studentIds = studentService.listStudentId(student);

        ThesesModel thesesModel = new ThesesModel();
        thesesModel.setStudentIds(studentIds);
        thesesModel.setTeacherId(teacher.getId());

        List<ThesesModel> thesesModelList = thesesRepository.findList(thesesModel);

        thesesModelList.stream().forEach(data -> {
            data.setStudent(studentService.findById(data.getStudentId()));
            data.setProjection(projectionService.findById(data.getProjectionId()));
        });
        return thesesModelList;
    }

    @Transactional
    @Override
    public ThesesModel updateAudit(Integer id, Integer status, String remark) {

        ThesesModel model = this.findById(id);
        if (model == null) {
            return null;
        }

        model.setAuditStatus(status);
        model.setAuditRemark(remark);
        return this.update(model);
    }
}
