package com.fhx.gdms.controller.student.controllers;

import com.fhx.gdms.service.user.model.UserModel;
import com.fhx.gdms.service.user.service.TeacherService;
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
@RequestMapping("student:teacher")
public class TeacherOfStudentController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/listTeacher", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult listTeacher() {
        ApiResult apiResult = new ApiResult();

        UserModel userInfo = (UserModel) session.getAttribute("userInfo");

        List<UserModel> UserModelList = teacherService.findByDepartmentId(userInfo.getDepartmentId());

        apiResult.setData(UserModelList);

        return apiResult;
    }
}
