package com.fhx.gdms.controller.admin.controllers;

import com.fhx.gdms.service.power.service.PowerService;
import com.fhx.gdms.service.user.model.UserModel;
import com.fhx.gdms.service.user.service.StudentService;
import com.fhx.gdms.supportUtil.ApiPageResult;
import com.fhx.gdms.supportUtil.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/admin:student")
public class StudentOfAdminController {
    @Autowired
    private StudentService studentService;

    @Autowired
    protected PowerService powerService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult save(UserModel model) {
        ApiResult apiResult = new ApiResult();

        String no = model.getNo();
        if (no == null || "".equals(no)) {
            apiResult.setCode(-1);
            apiResult.setMsg("学号不能为空");
        }

        model = studentService.saveStudent(model);

        if (model != null) {
            apiResult.setCode(0);
            apiResult.setMsg("添加成功");
            apiResult.setData(model);
        } else {
            apiResult.setCode(-1);
            apiResult.setMsg(no + " 已存在");
        }
        return apiResult;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult update(UserModel model) {
        ApiResult apiResult = new ApiResult();

        model = studentService.updateStudent(model);

        if (model != null) {
            apiResult.setCode(0);
            apiResult.setMsg("更新成功");
            apiResult.setData(model);
        } else {
            apiResult.setCode(-1);
            apiResult.setMsg("学号已存在！");
        }
        return apiResult;
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    void deleteById(Integer id) {

        studentService.deleteById(id);
    }


    @RequestMapping(value = "/findById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult findById(Integer id) {
        ApiResult apiResult = new ApiResult();

        UserModel model = studentService.findById(id);

        apiResult.setData(model);
        return apiResult;
    }

    @RequestMapping(value = "/findStudent", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult findStudent(UserModel model) {
        ApiResult apiResult = null;

        model.setName((model.getName() != null) ? "%" + model.getName() + "%" : null);
        model.setNo((model.getName() != null) ? "%" + model.getNo() + "%" : null);

        List<UserModel> studentModelList = studentService.findStudent(model);
        Integer total = studentService.findTotal(model);


        apiResult = new ApiPageResult(studentModelList, total, model.getPage(), model.getSize());

        return apiResult;
    }


    @RequestMapping(value = "/findByMajorIdAndDepartmentId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult findByMajorIdAndDepartmentId(UserModel model) {
        ApiResult apiResult = null;

        List<UserModel> studentModelList = studentService.findStudent(model);
        Integer total = studentService.findTotal(model);

        apiResult = new ApiPageResult(studentModelList, total, model.getPage(), model.getSize());

        return apiResult;
    }
}
