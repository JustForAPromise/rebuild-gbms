package com.fhx.gdms.projections.controllers;

import com.fhx.gdms.projections.service.ProjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProjectionController {
    @Autowired
    private ProjectionService projectionService;
}
