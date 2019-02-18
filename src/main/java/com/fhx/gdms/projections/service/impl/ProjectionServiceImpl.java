package com.fhx.gdms.projections.service.impl;

import com.fhx.gdms.projections.repository.ProjectionRepository;
import com.fhx.gdms.projections.service.ProjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectionServiceImpl implements ProjectionService {
    @Autowired
    private ProjectionRepository projectionRepository;
}
