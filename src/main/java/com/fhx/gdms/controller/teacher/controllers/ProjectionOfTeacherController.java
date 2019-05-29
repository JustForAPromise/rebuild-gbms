package com.fhx.gdms.controller.teacher.controllers;

import login.com.supportUtil.ApiResult;
import login.com.projections.model.ProjectionModel;
import login.com.projections.service.ProjectionService;
import login.com.user.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/teacher/projection")
public class ProjectionOfTeacherController {

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

        model.setAuditStatus(0);
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

    @RequestMapping(value = "/findById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult findById(Integer id) {
        ApiResult apiResult = new ApiResult();

        ProjectionModel result = projectionService.findById(id);

        apiResult.setData(result);

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

    @RequestMapping(value = "/deleteById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult deleteById(Integer id) {
        ApiResult apiResult = new ApiResult();

        projectionService.deleteById(id);

        apiResult.setCode(0);
        apiResult.setMsg("已删除");
        return apiResult;
    }
}
