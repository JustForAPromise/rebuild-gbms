package com.fhx.gdms.controller.teacher.controllers;

import com.fhx.gdms.service.major.model.MajorModel;
import com.fhx.gdms.service.major.service.MajorService;
import com.fhx.gdms.service.user.model.UserModel;
import com.fhx.gdms.supportUtil.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/teacher:major")
public class MajorOfTeacherController {

    @Autowired
    private HttpSession session;

    @Autowired
    private MajorService majorService;

    @RequestMapping(value = "/listMajorWithLoginUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult listMajorWithLoginUser() {
        ApiResult apiResult = new ApiResult();

        UserModel loginUser = (UserModel)session.getAttribute("userInfo");

        List<MajorModel> majorModelList = majorService.findByDepartmentId(loginUser.getDepartmentId());

        apiResult.setCode(0);
        apiResult.setData(majorModelList);
        return apiResult;
    }
}
