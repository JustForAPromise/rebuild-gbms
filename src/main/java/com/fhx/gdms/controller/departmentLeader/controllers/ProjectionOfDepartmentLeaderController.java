package com.fhx.gdms.controller.departmentLeader.controllers;

import com.fhx.gdms.service.projections.model.ProjectionModel;
import com.fhx.gdms.service.projections.service.ProjectionService;
import com.fhx.gdms.supportUtil.ApiPageResult;
import com.fhx.gdms.supportUtil.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/departmentLeader:projection")
public class ProjectionOfDepartmentLeaderController {

    @Autowired
    private ProjectionService projectionService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult list(ProjectionModel model) {

        if (model.getTitle() != null){
            model.setTitle("%"+model.getTitle()+"%");
        }

        List<ProjectionModel> results = projectionService.list(model);
        Integer total = projectionService.findTotal(model);

        ApiResult apiResult = new ApiPageResult(results, total, model.getPage(), model.getSize());

        return apiResult;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView findByIdWithUrl(@PathVariable("id") Integer id) {

        ProjectionModel result = projectionService.findById(id);

        ModelAndView modelAndView = new ModelAndView("/departmentLeader/projectionDetail.html");
        modelAndView.addObject("info", result);

        return modelAndView;
    }
}
