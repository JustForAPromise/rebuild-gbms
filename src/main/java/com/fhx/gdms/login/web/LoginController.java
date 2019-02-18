package com.fhx.gdms.login.web;

import com.fhx.gdms.admin.model.AdminModel;
import com.fhx.gdms.admin.service.AdminService;
import com.fhx.gdms.student.model.StudentModel;
import com.fhx.gdms.student.service.StudentService;
import com.fhx.gdms.teacher.model.TeacherModel;
import com.fhx.gdms.teacher.service.TeacherService;
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
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView login(Integer identify, String name, String password) {

        if (identify == 1) {
            AdminModel adminModel = adminService.findByNameAndPassword(name, password);
            if (adminModel != null) {
                ModelAndView modelAndView = new ModelAndView("/admin/index.html");
                session.setAttribute("userInfo", adminModel);
                return modelAndView;
            }

        } else if (identify == 2) {
            TeacherModel teacherModel = teacherService.findByNameAndPassword(name, password);
            if (teacherModel != null) {
                ModelAndView modelAndView = new ModelAndView("/teacher/index.html");
                session.setAttribute("userInfo", teacherModel);
                return modelAndView;
            }

        } else if (identify == 3) {
            StudentModel studentModel = studentService.findByNameAndPassword(name, password);
            if (studentModel != null) {
                ModelAndView modelAndView = new ModelAndView("/helper/index.html");
                session.setAttribute("userInfo", studentModel);
                return modelAndView;
            }

        } else if (identify == 4) {
            StudentModel studentModel = studentService.findByNameAndPassword(name, password);
            if (studentModel != null) {
                ModelAndView modelAndView = new ModelAndView("/student/index.html");
                session.setAttribute("userInfo", studentModel);
                return modelAndView;
            }
        }

        ModelAndView loginFailView = new ModelAndView("/login.html");
        loginFailView.addObject("tip","密码或用户名错误！");
        return loginFailView;
    }
}
