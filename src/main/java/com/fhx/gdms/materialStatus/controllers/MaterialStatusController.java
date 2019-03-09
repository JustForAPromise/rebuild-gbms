package com.fhx.gdms.materialStatus.controllers;

import com.fhx.gdms.materialStatus.model.MaterialStatusModel;
import com.fhx.gdms.materialStatus.service.MaterialStatusService;
import com.fhx.gdms.projections.service.ProjectionService;
import com.fhx.gdms.supportUtil.ApiResult;
import com.fhx.gdms.supportUtil.FileUtil;
import com.fhx.gdms.material.model.MaterialModel;
import com.fhx.gdms.user.model.UserModel;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/materialStatus")
public class MaterialStatusController {

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
