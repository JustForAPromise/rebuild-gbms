package com.fhx.gdms.projections.service.impl;

import com.fhx.gdms.projections.model.ProjectionModel;
import com.fhx.gdms.projections.repository.ProjectionRepository;
import com.fhx.gdms.projections.service.ProjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectionServiceImpl implements ProjectionService {
    @Autowired
    private ProjectionRepository projectionRepository;

    @Override
    public ProjectionModel save(ProjectionModel model) {
        return null;
    }

    @Override
    public ProjectionModel update(ProjectionModel model) {
        return null;
    }

    @Override
    public ProjectionModel saveTeacher(ProjectionModel model) {
        return null;
    }

    @Override
    public ProjectionModel updateTeacher(ProjectionModel model) {
        return null;
    }

    @Override
    public List<ProjectionModel> findAll() {
        return null;
    }

    @Override
    public List<ProjectionModel> findTeacher(ProjectionModel model) {
        return null;
    }
}
