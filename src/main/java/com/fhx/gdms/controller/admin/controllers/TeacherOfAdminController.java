package com.fhx.gdms.controller.admin.controllers;

import login.com.supportUtil.ApiPageResult;
import login.com.supportUtil.ApiResult;
import login.com.user.model.UserModel;
import login.com.user.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/admin/teacher")
public class TeacherOfAdminController {
    @Autowired
    private TeacherService teacherService;

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
        ApiResult apiResult = null;

        model.setName((model.getName() != null) ? "%" + model.getName() + "%" : null);
        model.setNo((model.getNo() != null) ? "%" + model.getNo() + "%" : null);

        List<UserModel> studentModelList = teacherService.findTeacher(model);
        Integer total = teacherService.findTotal(model);

        apiResult = new ApiPageResult(studentModelList, total, model.getPage(), model.getSize());

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
