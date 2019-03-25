package com.fhx.gdms.controller.help.controllers;

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
@RequestMapping("helper:teacher")
public class TeacherOfHelperController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult save(UserModel model) {
        ApiResult apiResult = new ApiResult();

        String no = model.getNo();
        if (no == null || "".equals(no)) {
            apiResult.setCode(-1);
            apiResult.setMsg("学号不能为空");
        }

        model = teacherService.saveTeacher(model);

        if (model != null) {
            apiResult.setCode(0);
            apiResult.setMsg("添加成功");
            apiResult.setData(model);
        } else {
            apiResult.setCode(-1);
            apiResult.setMsg("教师工号已存在");
        }
        return apiResult;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult update(UserModel model) {
        ApiResult apiResult = new ApiResult();

        model = teacherService.updateTeacher(model);

        if (model != null) {
            apiResult.setCode(0);
            apiResult.setMsg("更新成功");
            apiResult.setData(model);
        } else {
            apiResult.setCode(-1);
            apiResult.setMsg("教师工号已存在！");
        }
        return apiResult;
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    void deleteById(Integer id) {

        teacherService.deleteById(id);
    }


    @RequestMapping(value = "/findById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult findById(Integer id) {
        ApiResult apiResult = new ApiResult();

        UserModel model = teacherService.findById(id);

        apiResult.setData(model);

        return apiResult;
    }

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

    @RequestMapping(value = "/listTeacher", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult listTeacher() {
        ApiResult apiResult = new ApiResult();

        UserModel userInfo = (UserModel) session.getAttribute("userInfo");

        List<UserModel> UserModelList = teacherService.findByDepartmentId(userInfo.getDepartmentId());

        apiResult.setData(UserModelList);

        return apiResult;
    }


    @RequestMapping(value = "/findByDepartmentId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult findByDepartmentId(Integer departmentId, Integer majorId) {
        ApiResult apiResult = new ApiResult();

        List<UserModel> UserModelList = teacherService.findByDepartmentId(departmentId);

        apiResult.setData(UserModelList);

        return apiResult;
    }
}
