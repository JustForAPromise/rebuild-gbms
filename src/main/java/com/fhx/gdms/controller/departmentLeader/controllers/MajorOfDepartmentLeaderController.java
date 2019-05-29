package com.fhx.gdms.controller.departmentLeader.controllers;

import login.com.supportUtil.ApiResult;
import login.com.major.model.MajorModel;
import login.com.major.service.MajorService;
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
@RequestMapping("/departmentLeader/major")
public class MajorOfDepartmentLeaderController {

    @Autowired
    private HttpSession session;

    @Autowired
    private MajorService majorService;

    @RequestMapping(value = "/listMajor", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult listMajor() {
        ApiResult apiResult = new ApiResult();

        UserModel departmentLeader = (UserModel)session.getAttribute("userInfo");

        List<MajorModel> majorModelList = majorService.findByDepartmentId(departmentLeader.getDepartmentId());

        apiResult.setData(majorModelList);
        return apiResult;
    }
}
