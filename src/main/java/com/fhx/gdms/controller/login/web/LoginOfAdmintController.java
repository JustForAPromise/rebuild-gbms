package com.fhx.gdms.controller.login.web;

import com.fhx.gdms.service.department.service.DepartmentService;
import com.fhx.gdms.service.major.service.MajorService;
import com.fhx.gdms.service.power.service.PowerService;
import com.fhx.gdms.service.projections.model.ProjectionModel;
import com.fhx.gdms.service.projections.service.ProjectionService;
import com.fhx.gdms.service.selectRecord.model.SelectRecordModel;
import com.fhx.gdms.service.selectRecord.service.SelectRecordService;
import com.fhx.gdms.service.user.model.UserModel;
import com.fhx.gdms.service.user.service.AdminService;
import com.fhx.gdms.service.user.service.HelperService;
import com.fhx.gdms.service.user.service.StudentService;
import com.fhx.gdms.service.user.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginOfAdmintController {

    @Autowired
    private HttpSession session;

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView login(Integer identify, String no, String password) {
        UserModel userModel = new UserModel();
        ModelAndView modelAndView = null;

            userModel = adminService.findByNoAndPasswd(no, password);

            if (userModel != null) {
                modelAndView = new ModelAndView("/admin/index.html");
            }

        if (userModel == null) {
            modelAndView = new ModelAndView("/adminLogin.html");
            modelAndView.addObject("tip", "密码或用户名错误！");
            return modelAndView;
        } else {
            session.setAttribute("userInfo", userModel);
            return modelAndView;
        }
    }

    @RequestMapping(value = "/loginOut", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView loginOut() {
        UserModel userModel = (UserModel) session.getAttribute("userInfo");
        session.invalidate();

        ModelAndView modelAndView = new ModelAndView("/adminLogin.html");
        return modelAndView;
    }
}
