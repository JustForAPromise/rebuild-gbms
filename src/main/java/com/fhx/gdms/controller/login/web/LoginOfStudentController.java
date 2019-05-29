package com.fhx.gdms.controller.login.web;

import login.com.department.service.DepartmentService;
import login.com.major.service.MajorService;
import login.com.power.service.PowerService;
import login.com.projections.model.ProjectionModel;
import login.com.projections.service.ProjectionService;
import login.com.selectRecord.model.SelectRecordModel;
import login.com.selectRecord.service.SelectRecordService;
import login.com.user.model.UserModel;
import login.com.user.service.StudentService;
import login.com.user.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class LoginOfStudentController {

    @Autowired
    private HttpSession session;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private PowerService powerService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private SelectRecordService selectRecordService;

    @Autowired
    private ProjectionService projectionService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView login(Integer identify, String no, String password) {
        ModelAndView modelAndView = null;

        UserModel userModel = studentService.findByNoAndPasswd(no, password);
        if (userModel != null) {
            modelAndView = new ModelAndView("/student/index.html");
        }

        if (userModel == null) {
            modelAndView = new ModelAndView("/studentLogin.html");
            modelAndView.addObject("tip", "密码或用户名错误！");
        } else {
            if (userModel.getPowerId() != null) {
                userModel.setPowerModel(powerService.findById(userModel.getPowerId()));
            }
            if (userModel.getTeacherId() != null) {
                userModel.setTeacherModel(teacherService.findById(userModel.getTeacherId()));
            }
            if (userModel.getDepartmentId() != null) {
                userModel.setDepartmentModel(departmentService.findById(userModel.getDepartmentId()));
            }
            if (userModel.getMajorId() != null) {
                userModel.setMajorModel(majorService.findById(userModel.getMajorId()));
            }
            SelectRecordModel selectRecordModel = selectRecordService.findHavedSelectedRecordByStudentId(userModel.getId());
            if (selectRecordModel != null) {
                ProjectionModel projectionModel = projectionService.findById(selectRecordModel.getProjectionId());
                userModel.setProjectionModel(projectionModel);
            }
            session.setAttribute("userInfo", userModel);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/loginOut", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView loginOut() {
        UserModel userModel = (UserModel) session.getAttribute("userInfo");
        session.invalidate();

        ModelAndView modelAndView = null;
        modelAndView = new ModelAndView("/studentLogin.html");
        return modelAndView;
    }
}
