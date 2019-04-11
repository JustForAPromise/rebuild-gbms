package com.fhx.gdms.controller.student.controllers;

import com.fhx.gdms.service.department.service.DepartmentService;
import com.fhx.gdms.service.major.service.MajorService;
import com.fhx.gdms.service.power.service.PowerService;
import com.fhx.gdms.service.projections.model.ProjectionModel;
import com.fhx.gdms.service.projections.service.ProjectionService;
import com.fhx.gdms.service.selectRecord.model.SelectRecordModel;
import com.fhx.gdms.service.selectRecord.service.SelectRecordService;
import com.fhx.gdms.service.user.model.UserModel;
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
