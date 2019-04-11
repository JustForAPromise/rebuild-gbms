package com.fhx.gdms.controller.student.controllers;

import com.fhx.gdms.service.projections.model.ProjectionModel;
import com.fhx.gdms.service.projections.service.ProjectionService;
import com.fhx.gdms.service.user.model.UserModel;
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
@RequestMapping("/student:projection")
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
    ApiResult listProjectionToStudent(String title, Integer teacherId, Integer status) {
        ApiResult apiResult = new ApiResult();

        UserModel student = (UserModel) session.getAttribute("userInfo");
        ProjectionModel projectionModel = new ProjectionModel();
        projectionModel.setDepartmentId(student.getDepartmentId());
        projectionModel.setTitle("%"+title+"%");
        projectionModel.setTeacherId(teacherId);
        projectionModel.setMajorId(student.getMajorId());

        List<ProjectionModel> projectionModelList = projectionService.listProjectionToStudent(projectionModel, student , status);

        apiResult.setData(projectionModelList);

        return apiResult;
    }
}
