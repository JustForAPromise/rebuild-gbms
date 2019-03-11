package com.fhx.gdms.selectRecord.service.impl;

import com.fhx.gdms.materialStatus.model.MaterialStatusModel;
import com.fhx.gdms.materialStatus.service.MaterialStatusService;
import com.fhx.gdms.projections.service.ProjectionService;
import com.fhx.gdms.selectRecord.model.SelectRecordModel;
import com.fhx.gdms.selectRecord.repository.SelectRecordRepository;
import com.fhx.gdms.selectRecord.service.SelectRecordService;
import com.fhx.gdms.user.service.StudentService;
import com.fhx.gdms.user.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SelectRecordServiceImpl implements SelectRecordService {
    @Autowired
    private SelectRecordRepository selectRecordRepository;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ProjectionService projectionService;

    @Autowired
    private MaterialStatusService materialStatusService;

    @Override
    public SelectRecordModel saveSelect(SelectRecordModel model) {
        model = this.save(model);

        return model;
    }

    @Override
    public SelectRecordModel save(SelectRecordModel model) {
        selectRecordRepository.save(model);

        return this.findById(model.getId());
    }

    @Override
    public SelectRecordModel findById(Integer id) {

        return selectRecordRepository.findById(id);
    }

    @Override
    public SelectRecordModel updateSelect(SelectRecordModel model) {
        model = this.update(model);

        return model;
    }

    @Override
    public SelectRecordModel update(SelectRecordModel model) {
        selectRecordRepository.update(model);

        return this.findById(model.getId());
    }

    @Override
    public List<SelectRecordModel> listStudent(Integer teacherId) {
        List<SelectRecordModel> modelList = selectRecordRepository.findByTeacherId(teacherId);
        modelList.stream().forEach(data->{
            data.setStudentModel(studentService.findById(data.getStudentId()));
            data.setTeacherModel(teacherService.findById(data.getTeacherId()));
            data.setProjectionModel(projectionService.findById(data.getProjectionId()));
        });

        return modelList;
    }

    @Override
    public Integer refuceStudent(Integer id) {
        return selectRecordRepository.refuceStudent(id);
    }

    @Transactional
    @Override
    public Integer receiveStudent(Integer id) {
        SelectRecordModel selectRecordModel =  this.findById(id);
        Integer flag =  selectRecordRepository.receiveStudent(id);

        studentService.updateTeacherId(selectRecordModel.getStudentId(), selectRecordModel.getTeacherId());

        projectionService.updateStudentId(selectRecordModel.getProjectionId(), selectRecordModel.getStudentId());

        selectRecordRepository.refuseOtherRequestOfStudent(selectRecordModel.getStudentId());

        MaterialStatusModel materialStatusModel = new MaterialStatusModel();
        materialStatusModel.setTeacherId(selectRecordModel.getTeacherId());
        materialStatusModel.setStudentId(selectRecordModel.getStudentId());
        materialStatusModel.setProjectionId(selectRecordModel.getProjectionId());
        materialStatusModel.setTaskSubmitStatus(0);
        materialStatusModel.setThesesSubmitStatus(0);
        materialStatusService.saveStatus(materialStatusModel);

        return flag;
    }

    @Override
    public List<Integer> findByUserId(Integer userId) {
        List<Integer> projectionId = selectRecordRepository.findByUserId(userId);

        return projectionId;
    }

    @Override
    public Integer findTotalByUserId(Integer studentId, Integer status) {
        return selectRecordRepository.findTotalByStudentId(studentId, status);
    }

    @Override
    public List<SelectRecordModel> listReceiveStudent(Integer teacherId) {

        List<SelectRecordModel> modelList = selectRecordRepository.listReceiveStudent(teacherId);
        modelList.stream().forEach(data->{
            data.setStudentModel(studentService.findById(data.getStudentId()));
            data.setTeacherModel(teacherService.findById(data.getTeacherId()));
            data.setProjectionModel(projectionService.findById(data.getProjectionId()));
        });

        return modelList;
    }

    @Override
    public SelectRecordModel findHavedSelectedRecordByStudentId(Integer studentId) {
        return selectRecordRepository.findHavedSelectedRecordByStudentId(studentId);
    }
}
