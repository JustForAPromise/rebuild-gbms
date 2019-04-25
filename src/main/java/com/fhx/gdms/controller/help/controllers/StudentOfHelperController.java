package com.fhx.gdms.controller.help.controllers;

import com.fhx.gdms.service.user.model.UserModel;
import com.fhx.gdms.service.user.service.StudentService;
import com.fhx.gdms.supportUtil.ApiPageResult;
import com.fhx.gdms.supportUtil.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/helper/student")
public class StudentOfHelperController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/findById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult findById(Integer id) {
        ApiResult apiResult = new ApiResult();

        UserModel model = studentService.findById(id);

        apiResult.setData(model);
        return apiResult;
    }

    @RequestMapping(value = "/listToHelper", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult listTohelper(UserModel model) {

        UserModel helper = (UserModel) session.getAttribute("userInfo");

        model.setDepartmentId(helper.getDepartmentId());
        if (model.getName() != null) {
            model.setName("%" + model.getName() + "%");
        }
        if (model.getNo() != null) {
            model.setNo("%" + model.getNo() + "%");
        }

        model.setWithoutProjection(true);

        List<UserModel> studentModelList = studentService.findStudent(model);

        List<UserModel> results = new ArrayList<>();

        studentModelList.stream().forEach(data -> {
            if (data.getProjectionSelectModel() == null) {
                results.add(data);
            }
        });

        studentModelList.stream().forEach(data -> {
            if (data.getProjectionSelectModel() != null) {
                results.add(data);
            }
        });

        Integer total = studentService.findTotal(model);


        ApiResult apiResult =  new ApiPageResult(results, total, model.getPage(), model.getSize());

        return apiResult;
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult reset(Integer id) {
        ApiResult apiResult = new ApiResult();

        UserModel helper = (UserModel) session.getAttribute("userInfo");
        if (helper == null) {
            apiResult.setCode(-1);
            apiResult.setMsg("请先登录！");
            return apiResult;
        }else if (helper.getIdentify() != 3){
            apiResult.setCode(-1);
            apiResult.setMsg("权限不足！");
            return apiResult;
        }

        UserModel student = studentService.findById(id);
        student.setPassword(student.getNo());

        student = studentService.update(student);
        if (student != null) {
            apiResult.setCode(0);
            apiResult.setMsg("更新成功! 初始密码与学号相同");
        } else {
            apiResult.setCode(-1);
            apiResult.setMsg("ERROR！");
        }
        return apiResult;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView findDetail(@PathVariable Integer id) {

        UserModel helper = (UserModel) session.getAttribute("userInfo");
        if (helper == null) {
           return null;
        }

        UserModel student =studentService.findById(id);

        ModelAndView modelAndView = new ModelAndView("/helper/info/student-detail-info.html");
        modelAndView.addObject("info", student);

        return modelAndView;
    }
}
