package com.fhx.gdms.controller.student.controllers;

import login.com.supportUtil.ApiPageResult;
import login.com.supportUtil.ApiResult;
import login.com.projections.model.ProjectionModel;
import login.com.projections.service.ProjectionService;
import login.com.user.model.UserModel;
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
@RequestMapping("/student/projection")
public class ProjectionOfStudentController {

    @Autowired
    private ProjectionService projectionService;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/findById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult findById(Integer id) {
        ApiResult apiResult = new ApiResult();

        ProjectionModel result = projectionService.findById(id);

        apiResult.setData(result);

        return apiResult;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView findByIdWithUrl(@PathVariable("id") Integer id) {

        ProjectionModel result = projectionService.findById(id);

        ModelAndView modelAndView = new ModelAndView("/departmentLeader/projectionDetail.html");
        modelAndView.addObject("info", result);

        return modelAndView;
    }

    @RequestMapping(value = "/listProjectionToStudent", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult listProjectionToStudent(ProjectionModel model, Integer status) {

        UserModel student = (UserModel) session.getAttribute("userInfo");

        model.setDepartmentId(student.getDepartmentId());
        model.setTitle((model.getTitle() != null) ? "%" + model.getTitle() + "%" : null);
        model.setMajorId(student.getMajorId());

        List<ProjectionModel> projectionModelList = projectionService.listProjectionToStudent(model, student , status);
        Integer total = projectionService.findTotal(model);

        ApiResult apiResult = new ApiPageResult(projectionModelList, total, model.getPage(), model.getSize());

        return apiResult;
    }
}
