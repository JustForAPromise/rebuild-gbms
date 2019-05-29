package com.fhx.gdms.controller.student.controllers;

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
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private HttpSession session;

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

    @RequestMapping(value = "/getPersonInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView findPersonInfo() {
        UserModel student = (UserModel) session.getAttribute("userInfo");
        student = studentService.findById(student.getId());

        if (student.getTeacherId() != null) {
            student.setTeacherModel(teacherService.findById(student.getTeacherId()));
        }
        if (student.getPowerId() != null) {
            student.setPowerModel(powerService.findById(student.getPowerId()));
        }
        if (student.getDepartmentId() != null) {
            student.setDepartmentModel(departmentService.findById(student.getDepartmentId()));
        }
        if (student.getMajorId() != null) {
            student.setMajorModel(majorService.findById(student.getMajorId()));
        }
        SelectRecordModel selectRecordModel = selectRecordService.findHavedSelectedRecordByStudentId(student.getId());
        if (selectRecordModel != null) {
            ProjectionModel projectionModel = projectionService.findById(selectRecordModel.getProjectionId());
            student.setProjectionModel(projectionModel);
        }

        ModelAndView modelAndView = new ModelAndView("/student/personInfo/personInfo.html");
        modelAndView.addObject("userInfo", student);

        return modelAndView;
    }

    @RequestMapping(value = "/projectionInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView getProjectionInfoPage() {
        UserModel student = (UserModel) session.getAttribute("userInfo");
        student = studentService.findById(student.getId());

        if (student.getTeacherId() == null) {
            ModelAndView modelAndView = new ModelAndView("/student/info/projectionInfo.html");
            modelAndView.addObject("flag", true);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("/student/info/projectionInfo.html");
        return modelAndView;
    }
}
