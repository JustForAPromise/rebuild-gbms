package com.fhx.gdms.controller.teacher.controllers;

import login.com.supportUtil.ApiResult;
import login.com.materialStatus.model.MaterialStatusModel;
import login.com.materialStatus.service.MaterialStatusService;
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
@RequestMapping("/teacher/materialStatus")
public class MaterialStatusOfTeacherController {

    @Autowired
    private MaterialStatusService materialStatusService;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/countTheses", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult countTheses(Integer submitStatus, Integer auditStatus) {
        ApiResult apiResult = new ApiResult();

        //获取用户信息
        UserModel teacher = (UserModel)session.getAttribute("userInfo");

        //构建model
        MaterialStatusModel materialStatusModel = new MaterialStatusModel();
        materialStatusModel.setTeacherId(teacher.getTeacherId());
        materialStatusModel.setThesesSubmitStatus(submitStatus);
        materialStatusModel.setThesesAuditStatus(auditStatus);
        List<MaterialStatusModel> results = materialStatusService.findList(materialStatusModel);

        apiResult.setCode(0);
        apiResult.setMsg("");
        apiResult.setData(results);
        return apiResult;
    }


    @RequestMapping(value = "/countTaskBook", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult countTaskBook(Integer submitStatus, Integer auditStatus) {
        ApiResult apiResult = new ApiResult();

        //获取用户信息
        UserModel teacher = (UserModel)session.getAttribute("userInfo");

        //构建model
        MaterialStatusModel materialStatusModel = new MaterialStatusModel();
        materialStatusModel.setTeacherId(teacher.getTeacherId());
        materialStatusModel.setTaskSubmitStatus(submitStatus);
        materialStatusModel.setTaskAuditStatus(auditStatus);
        List<MaterialStatusModel> results = materialStatusService.findList(materialStatusModel);

        apiResult.setCode(0);
        apiResult.setMsg("");
        apiResult.setData(results);
        return apiResult;
    }
}
