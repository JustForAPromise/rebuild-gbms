package com.fhx.gdms.teacher.controllers;

import com.fhx.gdms.supportUtil.ApiResult;
import com.fhx.gdms.teacher.model.TeacherModel;
import com.fhx.gdms.teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;


    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult save(String name){
        ApiResult apiResult = new ApiResult();

        TeacherModel model = new TeacherModel();
        model.setName(name);

        model = teacherService.saveTeacher(model);

        if (model != null){
            apiResult.setCode(0);
            apiResult.setMsg("添加成功");
            apiResult.setData(model);
        }else{
            apiResult.setCode(-1);
            apiResult.setMsg("该系名已存在");
        }
        return apiResult;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult update(Integer id, String name){
        ApiResult apiResult = new ApiResult();

        TeacherModel model = new TeacherModel();
        model.setId(id);
        model.setName(name);

        model = teacherService.updateTeacher(model);

        if (model != null){
            apiResult.setCode(0);
            apiResult.setMsg("更新成功");
            apiResult.setData(model);
        }else{
            apiResult.setCode(-1);
            apiResult.setMsg("更新失败，系名已存在！");
        }
        return apiResult;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult listAll(){
        ApiResult apiResult = new ApiResult();

        List<TeacherModel> departmentModelList = teacherService.findAll();

        apiResult.setData(departmentModelList);

        return apiResult;
    }

    @RequestMapping(value = "/findStudent", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult findStudent(TeacherModel model){
        ApiResult apiResult = new ApiResult();

        model.setName("%"+ model.getName() +"%");;
        List<TeacherModel> departmentModelList = teacherService.findTeacher(model);

        apiResult.setData(departmentModelList);

        return apiResult;
    }
}