package com.fhx.gdms.projections.service.impl;

import com.fhx.gdms.projections.model.ProjectionModel;
import com.fhx.gdms.projections.repository.ProjectionRepository;
import com.fhx.gdms.projections.service.ProjectionService;
import com.fhx.gdms.selectRecord.service.SelectRecordService;
import com.fhx.gdms.user.model.UserModel;
import com.fhx.gdms.user.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectionServiceImpl implements ProjectionService {
    @Autowired
    private ProjectionRepository projectionRepository;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SelectRecordService selectRecordService;

    @Override
    public ProjectionModel save(ProjectionModel model) {
        projectionRepository.save(model);

        return this.findById(model.getId());
    }

    @Override
    public ProjectionModel update(ProjectionModel model) {
        projectionRepository.update(model);

        return this.findById(model.getId());
    }

    @Override
    public List<ProjectionModel> findAll() {
        return null;
    }

    @Override
    public ProjectionModel findById(Integer id) {
        ProjectionModel model = projectionRepository.findById(id);

        model.setTeacherModel(teacherService.findById(model.getTeacherId()));

        return model;
    }

    @Override
    public ProjectionModel updateProjection(ProjectionModel model) {
        model = this.update(model);

        return model;
    }

    @Override
    public void deleteById(Integer id) {
        projectionRepository.deleteById(id);
    }

    @Override
    public List<ProjectionModel> findByTitle(String title) {
        return null;
    }

    @Override
    public ProjectionModel saveProjection(ProjectionModel model, UserModel userInfo) {
        model.setTeacherId(userInfo.getId());
        model.setDepartmentId(userInfo.getDepartmentId());

        return this.save(model);
    }

    @Override
    public List<ProjectionModel> findList(ProjectionModel model) {

        List<ProjectionModel>  modelList = projectionRepository.findList(model);
        modelList.stream().forEach(data->{
            data.setTeacherModel(teacherService.findById(data.getTeacherId()));
        });

        return modelList;
    }

    @Override
    public List<ProjectionModel> listAllProjection(ProjectionModel model) {
        List<ProjectionModel> modelList = projectionRepository.listAllProjection(model);
        modelList.stream().forEach(data->{
            data.setTeacherModel(teacherService.findById(data.getTeacherId()));
        });

        return modelList;
    }

    @Override
    public List<ProjectionModel> listProjectionToStudent(ProjectionModel projectionModel, UserModel student, Integer status) {
        //学生已选课题id
        List<Integer> projectionIds = selectRecordService.findByUserId(student.getId());

        if (status == 1) {
            if (projectionIds.size() <= 0){
                return null;
            }
            projectionModel.setProjectionIdIn(projectionIds);
        }else if (status == 0){
            projectionModel.setProjectionIdNotIn(projectionIds);
        }
        projectionModel.setAuditStatus(1);
        List<ProjectionModel> modelList = projectionRepository.findList(projectionModel);
        modelList.stream().forEach(data->{
            data.setTeacherModel(teacherService.findById(data.getTeacherId()));
        });

        return modelList;
    }

    @Override
    public void updateStudentId(Integer projectionId, Integer studentId) {
        projectionRepository.updateStudentId(projectionId, studentId);
    }

    @Override
    public ProjectionModel findByUserIdAndTeacherId(Integer studentId, Integer teacherId) {
        return projectionRepository.findByUserIdAndTeacherId(studentId, teacherId);
    }

    @Override
    public List<ProjectionModel> listProjectionToAudit(Integer teacherId, Integer departmentId) {
        List<ProjectionModel> results =  projectionRepository.listProjectionToAudit(teacherId, departmentId);
        results.stream().forEach(data ->{
            data.setTeacherModel(teacherService.findById(data.getTeacherId()));
        });

        return results;
    }

    @Override
    public List<ProjectionModel> findListToAudit(ProjectionModel model) {
        List<ProjectionModel> results =  projectionRepository.findListToAudit(model);
        results.stream().forEach(data ->{
            data.setTeacherModel(teacherService.findById(data.getTeacherId()));
        });

        return results;
    }
}
