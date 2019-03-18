package com.fhx.gdms.helper.web;

import com.fhx.gdms.studentNumOfTeacher.model.StudentNumOfTeacherModel;
import com.fhx.gdms.studentNumOfTeacher.service.StudentNumOfTeacherService;
import com.fhx.gdms.supportUtil.ApiResult;
import com.fhx.gdms.user.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/help:teacher")
public class TeacherInfoController {

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
