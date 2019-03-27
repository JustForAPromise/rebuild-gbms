package com.fhx.gdms.controller.help.controllers;

import com.fhx.gdms.service.studentNumOfTeacher.model.StudentNumOfTeacherModel;
import com.fhx.gdms.service.studentNumOfTeacher.service.StudentNumOfTeacherService;
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
@RequestMapping("/helper:teacherStudentNum")
public class TeacherStudentNumController {

    @Autowired
    private HttpSession session;

    @Autowired
    private StudentNumOfTeacherService studentNumOfTeacherService;

    @RequestMapping(value = "/findList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult findList(UserModel model) {
        ApiResult apiResult = new ApiResult();

        UserModel loginUser = (UserModel) session.getAttribute("userInfo");

        model.setDepartmentId(loginUser.getDepartmentId());

        List<StudentNumOfTeacherModel> results = studentNumOfTeacherService.findList(model);

        apiResult.setCode(0);
        apiResult.setData(results);
        return apiResult;
    }

    @RequestMapping(value = "/updateNum", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult updateNum(StudentNumOfTeacherModel model) {
        ApiResult apiResult = new ApiResult();

        UserModel loginUser = (UserModel) session.getAttribute("userInfo");
        if (loginUser.getIdentify() < 3 ){
            apiResult.setCode(-1);
            apiResult.setMsg("权限不足！");
            return apiResult;
        }

        StudentNumOfTeacherModel results = studentNumOfTeacherService.update(model);

        apiResult.setCode(0);
        apiResult.setData(results);
        return apiResult;
    }
}
