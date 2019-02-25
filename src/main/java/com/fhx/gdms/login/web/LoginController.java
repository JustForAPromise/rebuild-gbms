package com.fhx.gdms.login.web;

import com.fhx.gdms.power.service.PowerService;
import com.fhx.gdms.user.service.AdminService;
import com.fhx.gdms.user.service.HelperService;
import com.fhx.gdms.user.service.StudentService;
import com.fhx.gdms.user.model.UserModel;
import com.fhx.gdms.user.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private HttpSession session;

    @Autowired
    private AdminService adminService;

    @Autowired
    private HelperService helperService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private PowerService powerService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView login(Integer identify, String name, String password) {
        UserModel userModel = new UserModel();

        if (identify == 1) {
            userModel = adminService.findByNameAndPassword(name, password);
            if (userModel != null) {
                ModelAndView modelAndView = new ModelAndView("/admin/index.html");
                return modelAndView;
            }

        } else if (identify == 2) {
            userModel = teacherService.findByNameAndPassword(name, password);
            if (userModel != null) {
                ModelAndView modelAndView = new ModelAndView("/teacher/index.html");
                return modelAndView;
            }

        } else if (identify == 3) {
            userModel = helperService.findByNameAndPassword(name, password);
            if (userModel != null) {
                ModelAndView modelAndView = new ModelAndView("/helper/index.html");
                return modelAndView;
            }

        } else if (identify == 4) {
            userModel = studentService.findByNameAndPassword(name, password);
            if (userModel != null) {
                ModelAndView modelAndView = new ModelAndView("/student/index.html");
                return modelAndView;
            }
        }

        userModel.setPowerModel(powerService.findByUserId(userModel.getId()));
        session.setAttribute("userInfo", userModel);

        ModelAndView loginFailView = new ModelAndView("/login.html");
        loginFailView.addObject("tip", "密码或用户名错误！");
        return loginFailView;
    }
}
