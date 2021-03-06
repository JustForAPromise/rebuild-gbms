package com.fhx.gdms.controller.login.web;

import login.com.department.service.DepartmentService;
import login.com.power.service.PowerService;
import login.com.user.model.UserModel;
import login.com.user.service.HelperService;
import login.com.user.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/teacher")
public class LoginOfTeacherController {

    @Autowired
    private HttpSession session;

    @Autowired
    private HelperService helperService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private PowerService powerService;

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView login(Integer identify, String no, String password) {
        UserModel userModel = new UserModel();
        ModelAndView modelAndView = null;

        if (identify == 2) {
            userModel = teacherService.findByNoAndPasswd(no, password);
            if (userModel != null) {
                modelAndView = new ModelAndView("/teacher/index.html");
            }

        } else if (identify == 3) {
            userModel = helperService.findByNoAndPasswd(no, password);
            if (userModel != null) {
                modelAndView = new ModelAndView("/helper/index.html");
            }

        }

        if (userModel == null) {
            modelAndView = new ModelAndView("/teacherLogin.html");
            modelAndView.addObject("tip", "密码或用户名错误！");
            return modelAndView;
        } else {
            if (userModel.getPowerId() != null) {
                userModel.setPowerModel(powerService.findById(userModel.getPowerId()));
            }
            if (userModel.getDepartmentId() != null) {
                userModel.setDepartmentModel(departmentService.findById(userModel.getDepartmentId()));
            }

            session.setAttribute("userInfo", userModel);
            return modelAndView;
        }
    }

    @RequestMapping(value = "/loginOut", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView loginOut() {
        UserModel userModel = (UserModel) session.getAttribute("userInfo");
        session.invalidate();

        ModelAndView modelAndView =  new ModelAndView("/teacherLogin.html");
        return modelAndView;
    }
}
