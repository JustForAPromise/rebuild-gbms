package com.fhx.gdms.controller.student.controllers;

import com.fhx.gdms.service.user.model.UserModel;
import com.fhx.gdms.service.user.service.UserService;
import com.fhx.gdms.supportUtil.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/student:user")
public class UserOfStudentController {

    @Autowired
    private HttpSession session;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult updatePwd(String password){
        ApiResult apiResult = new ApiResult();

        UserModel loginUser = (UserModel)session.getAttribute("userInfo");
        if (loginUser == null){
            apiResult.setCode(-1);
            apiResult.setMsg("请先登录！");
            return apiResult;
        }

        userService.updatePwd(loginUser.getId(), password);

        apiResult.setCode(0);
        apiResult.setMsg("密码已修改！");
        return apiResult;
    }
}
