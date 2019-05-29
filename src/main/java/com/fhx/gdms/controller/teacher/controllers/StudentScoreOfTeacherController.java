package com.fhx.gdms.controller.teacher.controllers;

import login.com.supportUtil.ApiResult;
import login.com.studentScore.itemScore.model.StudentItemScoreModel;
import login.com.studentScoreAllInfo.api.SearchDetailApiGet;
import login.com.studentScoreAllInfo.data.StudentScoreData;
import login.com.studentScoreAllInfo.service.StudentScoreService;
import login.com.user.model.UserModel;
import login.com.user.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/teacher/studentScore")
public class StudentScoreOfTeacherController {

    @Autowired
    private StudentScoreService studentScoreService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/listAllStudent", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult listAll() {
        ApiResult apiResult = new ApiResult();

        UserModel teacher = (UserModel) session.getAttribute("userInfo");

        List<UserModel> students = studentService.findByTeacherId(teacher.getId());

        apiResult.setData(students);

        return apiResult;
    }

    @RequestMapping(value = "/recordOfOrdinary", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView recordOfOrdinary(SearchDetailApiGet receiveData) {
        UserModel teacher = (UserModel) session.getAttribute("userInfo");
        if (teacher.getIdentify() != 1) {
            return null;
        }
        receiveData.setType(1);

        StudentScoreData result = studentScoreService.findRecord(receiveData);

        ModelAndView modelAndView = new ModelAndView("/teacher/info/studentScoreDetail.html");
        modelAndView.addObject("scoreAllInfo", result);

        return modelAndView;
    }


    @RequestMapping(value = "/updateNum", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult updateNum(StudentItemScoreModel model) {
        ApiResult apiResult = new ApiResult();

        UserModel teacher = (UserModel) session.getAttribute("userInfo");
        if (teacher.getIdentify() != 1) {
            apiResult.setCode(-1);
            apiResult.setMsg("权限不足！");
            return apiResult;
        }
        studentScoreService.updateNum(model);

        apiResult.setCode(0);
        apiResult.setMsg("登记成功");
        return apiResult;
    }



    //待验证
    @RequestMapping(value = "/updateStudentScore", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult updateStudentScore(Integer studentId, Integer[] ids, Integer[] scoreNums, Integer type) {
        ApiResult apiResult = new ApiResult();

        studentScoreService.updateStudentScore(studentId, ids, scoreNums, type);

        apiResult.setCode(0);
        apiResult.setMsg("登记成功");
        return apiResult;
    }
}
