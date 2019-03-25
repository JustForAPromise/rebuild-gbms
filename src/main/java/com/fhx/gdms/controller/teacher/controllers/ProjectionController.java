package com.fhx.gdms.controller.teacher.controllers;

import com.fhx.gdms.controller.student.projections.model.ProjectionModel;
import com.fhx.gdms.controller.student.projections.service.ProjectionService;
import com.fhx.gdms.controller.student.supportUtil.ApiResult;
import com.fhx.gdms.controller.student.user.model.UserModel;
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
@RequestMapping("/projection")
public class ProjectionController {

    @Autowired
    private ProjectionService projectionService;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult save(ProjectionModel model) {
        ApiResult apiResult = new ApiResult();

        UserModel userInfo = (UserModel) session.getAttribute("userInfo");

        model = projectionService.saveProjection(model, userInfo);

        if (model != null) {
            apiResult.setCode(0);
            apiResult.setMsg("添加成功");
            apiResult.setData(model);
        } else {
            apiResult.setCode(-1);
            apiResult.setMsg("ERROR");
        }
        return apiResult;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult update(ProjectionModel model) {
        ApiResult apiResult = new ApiResult();

        model = projectionService.updateProjection(model);

        if (model != null) {
            apiResult.setCode(0);
            apiResult.setMsg("更新成功");
            apiResult.setData(model);
        } else {
            apiResult.setCode(-1);
            apiResult.setMsg("ERROR！");
        }
        return apiResult;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult list(ProjectionModel model) {
        ApiResult apiResult = new ApiResult();

        if (model.getTitle() != null){
            model.setTitle("%"+model.getTitle()+"%");
        }

        List<ProjectionModel> results = projectionService.list(model);

        apiResult.setData(results);

        return apiResult;
    }

    @RequestMapping(value = "/findList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult findList(ProjectionModel model) {
        ApiResult apiResult = new ApiResult();

        if (model.getTitle() != null){
            model.setTitle("%"+model.getTitle()+"%");
        }
        model.setAuditStatus(1);

        List<ProjectionModel> departmentModelList = projectionService.findList(model);

        apiResult.setData(departmentModelList);

        return apiResult;
    }

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

    @RequestMapping(value = "/findByTitle", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult findByName(String title) {
        ApiResult apiResult = new ApiResult();

        title = "%" + title + "%";
        List<ProjectionModel> departmentModelList = projectionService.findByTitle(title);

        apiResult.setData(departmentModelList);

        return apiResult;
    }

    @RequestMapping(value = "/findByAuditStatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult findByAuditStatus(Integer auditStatus) {
        ApiResult apiResult = new ApiResult();

        UserModel userInfo = (UserModel) session.getAttribute("userInfo");
        ProjectionModel model = new ProjectionModel();
        model.setAuditStatus(auditStatus);
        model.setTeacherId(userInfo.getId());

        List<ProjectionModel> projectionModelList = projectionService.findList(model);

        apiResult.setData(projectionModelList);

        return apiResult;
    }

    @RequestMapping(value = "/listAllProjection", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult listAllProjection() {
        ApiResult apiResult = new ApiResult();

        UserModel userInfo = (UserModel) session.getAttribute("userInfo");
        ProjectionModel model = new ProjectionModel();
        model.setDepartmentId(userInfo.getDepartmentId());

        List<ProjectionModel> projectionModelList = projectionService.listAllProjection(model);

        apiResult.setData(projectionModelList);

        return apiResult;
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult deleteById(Integer id) {
        ApiResult apiResult = new ApiResult();

        projectionService.deleteById(id);

        apiResult.setCode(0);
        apiResult.setMsg("已删除");
        return apiResult;
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
