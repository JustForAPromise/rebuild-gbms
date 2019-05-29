package com.fhx.gdms.controller.student.controllers;

import login.com.studentScoreAllInfo.data.StudentScoreData;
import login.com.studentScoreAllInfo.service.StudentScoreService;
import login.com.user.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/student/studentScore")
public class StudentScoreOfStudentController {

    @Autowired
    private StudentScoreService studentScoreService;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/findScoreToStudent", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView findScoreToStudent() {
        UserModel student = (UserModel) session.getAttribute("userInfo");
        if (student == null) {
            return null;
        }

        StudentScoreData result = studentScoreService.findScoreToStudent(student);

        if (result.getProjectionModel() == null) {
            ModelAndView modelAndView = new ModelAndView("/student/info/projectionInfo.html");
            modelAndView.addObject("flag", true);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("/student/info/soscore.html");
        modelAndView.addObject("scoreAllInfo", result);

        return modelAndView;
    }
}
