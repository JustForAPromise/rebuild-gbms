package com.fhx.gdms.user.controllers;

import com.fhx.gdms.supportUtil.ApiResult;
import com.fhx.gdms.user.model.UserModel;
import com.fhx.gdms.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("user")
public class UserController {

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

        loginUser  = userService.updatePwd(loginUser.getId(), password);

        apiResult.setCode(0);
        apiResult.setMsg("密码已修改！");
        return apiResult;
    }

    @RequestMapping(value = "/addSupports", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult addSupports(UserModel model){
        ApiResult apiResult = new ApiResult();

        UserModel loginUser = (UserModel)session.getAttribute("userInfo");
        if (loginUser == null){
            apiResult.setCode(-1);
            apiResult.setMsg("请先登录！");
            return apiResult;
        }else if (!loginUser.getPowerModel().getDepartmentLeader()){
            apiResult.setCode(-1);
            apiResult.setMsg("权限不足！");
            return apiResult;
        }

        model = userService.addSupports(model);
        if (model == null){
            apiResult.setCode(-1);
            apiResult.setMsg("工号已被使用！");
            return apiResult;
        }

        apiResult.setCode(0);
        apiResult.setMsg("已添加");
        return apiResult;
    }
}
