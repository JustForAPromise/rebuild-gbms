package com.fhx.gdms.controller.departmentLeader.controllers;

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
@RequestMapping("departmentLeader/teacher")
public class TeacherOfDepartmentLeaderController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/findTeacher", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult findStudent(UserModel model) {
        ApiResult apiResult = new ApiResult();

        if (model.getName() != null) {
            model.setName("%" + model.getName() + "%");
        }
        if (model.getNo() != null) {
            model.setNo("%" + model.getNo() + "%");
        }

        List<UserModel> UserModelList = teacherService.findTeacher(model);

        apiResult.setData(UserModelList);

        return apiResult;
    }

    @RequestMapping(value = "/listTeacherToLeader", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult listTeacherToLeader() {
        ApiResult apiResult = new ApiResult();

        UserModel leader = (UserModel)session.getAttribute("userInfo");
        if (leader == null){
            return apiResult;
        }else if (!leader.getPowerModel().getDepartmentLeader()){
            return null;
        }

        List<UserModel> UserModelList = teacherService.findByDepartmentId(leader.getDepartmentId());

        apiResult.setData(UserModelList);

        return apiResult;
    }
}
