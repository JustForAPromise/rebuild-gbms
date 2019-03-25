package com.fhx.gdms.controller.admin.controllers;

import com.fhx.gdms.service.user.model.UserModel;
import com.fhx.gdms.service.user.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private HttpSession session;

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ModelAndView getUserInfo(){
        UserModel loginUser = (UserModel)session.getAttribute("userInfo");
        loginUser  = adminService.findById(loginUser.getId());

        ModelAndView modelAndView = new ModelAndView("/admin/personInfo/personInfo.html");
        modelAndView.addObject("userInfo", loginUser);

        return modelAndView;
    }
}
