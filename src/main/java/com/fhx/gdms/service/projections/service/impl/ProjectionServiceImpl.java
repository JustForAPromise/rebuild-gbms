package com.fhx.gdms.service.projections.service.impl;

import com.fhx.gdms.service.major.service.MajorService;
import com.fhx.gdms.service.projections.model.ProjectionModel;
import com.fhx.gdms.service.projections.repository.ProjectionRepository;
import com.fhx.gdms.service.projections.service.ProjectionService;
import com.fhx.gdms.service.selectRecord.model.SelectRecordModel;
import com.fhx.gdms.service.selectRecord.service.SelectRecordService;
import com.fhx.gdms.service.user.model.UserModel;
import com.fhx.gdms.service.user.service.TeacherService;
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
    private MajorService majorService;

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
    public List<ProjectionModel> list(ProjectionModel model) {
        List<ProjectionModel> results = projectionRepository.findList(model);
        results.stream().forEach(data -> {
            data.setTeacherModel(teacherService.findById(data.getTeacherId()));
            data.setMajorModel(majorService.findById(data.getMajorId()));
        });

        return results;
    }

    @Override
    public ProjectionModel findById(Integer id) {
        ProjectionModel model = projectionRepository.findById(id);

        model.setTeacherModel(teacherService.findById(model.getTeacherId()));
        model.setMajorModel(majorService.findById(model.getMajorId()));

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
    public ProjectionModel saveProjection(ProjectionModel model, UserModel userInfo) {
        model.setTeacherId(userInfo.getId());
        model.setDepartmentId(userInfo.getDepartmentId());

        return this.save(model);
    }

    @Override
    public List<ProjectionModel> findList(ProjectionModel model) {

        List<ProjectionModel> modelList = projectionRepository.findList(model);
        modelList.stream().forEach(data -> {
            data.setTeacherModel(teacherService.findById(data.getTeacherId()));
            data.setMajorModel(majorService.findById(data.getMajorId()));
        });

        return modelList;
    }

    @Override
    public List<ProjectionModel> listProjectionToStudent(ProjectionModel projectionModel, UserModel student, Integer status) {
        //学生已选课题id
        List<Integer> projectionIds = selectRecordService.findByUserId(student.getId());

        if (status == 1) {
            if (projectionIds.size() <= 0) {
                return null;
            }
            projectionModel.setProjectionIdIn(projectionIds);
        } else if (status == 0) {
            if (projectionIds.size() > 0) {
                projectionModel.setProjectionIdNotIn(projectionIds);
            }
        }
        projectionModel.setAuditStatus(1);
        List<ProjectionModel> modelList = projectionRepository.findList(projectionModel);
        modelList.stream().forEach(data -> {
            data.setTeacherModel(teacherService.findById(data.getTeacherId()));
            data.setMajorModel(majorService.findById(data.getMajorId()));

            SelectRecordModel selectRecordModel = new SelectRecordModel();
            selectRecordModel.setStudentId(student.getId());
            selectRecordModel.setProjectionId(data.getId());

            data.setSelectRecordModel(selectRecordService.findOne(selectRecordModel));
        });

        return modelList;
    }

    @Override
    public ProjectionModel findByUserIdAndTeacherId(Integer studentId, Integer teacherId) {
        SelectRecordModel selectRecordModel = new SelectRecordModel();
        selectRecordModel.setStudentId(studentId);
        selectRecordModel.setTeacherId(teacherId);
        selectRecordModel.setAuditStatus(1);
        selectRecordModel = selectRecordService.findOne(selectRecordModel);

        if (selectRecordModel == null) {
            return null;
        }

        return projectionRepository.findById(selectRecordModel.getProjectionId());
    }

    @Override
    public Integer findTotal(ProjectionModel model) {
        return projectionRepository.findTotal(model);
    }
}
