package com.fhx.gdms.controller.reviewTeacher.controllers;

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

@Controller
@RequestMapping("/review/studentScore")
public class StudentScoreOfReviewController {

    @Autowired
    private StudentScoreService studentScoreService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/recordOfReview", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView recordOfReview(SearchDetailApiGet receiveData) {
        UserModel teacher = (UserModel) session.getAttribute("userInfo");
        if (teacher.getIdentify() != 1) {
            return null;
        }

        if (receiveData.getNo() != null){
            UserModel student = new UserModel();
            student.setDepartmentId(teacher.getDepartmentId());
            student.setNo(receiveData.getNo());
            student =  studentService.findOne(student);
            if (student == null){
                ModelAndView modelAndView = new ModelAndView("/reviewTeacher/studentScore.html");
                modelAndView.addObject("tips", "学号不存在");
                return modelAndView;
            }else{
                receiveData.setStudentId(student.getId());
            }
        }
        receiveData.setType(2);



        StudentScoreData result = studentScoreService.findRecord(receiveData);

        ModelAndView modelAndView = new ModelAndView("/reviewTeacher/studentScoreDetail.html");
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
}
