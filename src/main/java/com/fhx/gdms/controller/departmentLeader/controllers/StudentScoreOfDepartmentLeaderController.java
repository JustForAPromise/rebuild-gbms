package com.fhx.gdms.controller.departmentLeader.controllers;

import com.fhx.gdms.service.studentScoreAllInfo.api.SearchDetailApiGet;
import com.fhx.gdms.service.studentScoreAllInfo.data.StudentScoreData;
import com.fhx.gdms.service.studentScoreAllInfo.service.StudentScoreService;
import com.fhx.gdms.service.user.model.UserModel;
import com.fhx.gdms.service.user.service.StudentService;
import com.fhx.gdms.supportUtil.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/departmentLeader:studentScore")
public class StudentScoreOfDepartmentLeaderController {

    @Autowired
    private StudentScoreService studentScoreService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/listStudent", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult listStudent(SearchDetailApiGet apiGet) {
        ApiResult apiResult = new ApiResult();

        UserModel departmentLeader = (UserModel) session.getAttribute("userInfo");

        UserModel student = new UserModel();
        student.setMajorId(apiGet.getMajorId());
        if (apiGet.getNo() != null) {
            student.setNo("%" + apiGet.getNo() + "%");
        }
        if (apiGet.getName() != null) {
            student.setName("%" + apiGet.getName() + "%");
        }
        student.setDepartmentId(departmentLeader.getDepartmentId());

        List<StudentScoreData> studentScoreDataList = studentScoreService.findBaseInfoList(student);

        apiResult.setData(studentScoreDataList);

        return apiResult;
    }

    @RequestMapping(value = "/student/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView listStudent(@PathVariable Integer id) {

        UserModel departmentLeader = (UserModel) session.getAttribute("userInfo");

        UserModel student = new UserModel();
        student.setId(id);
        student = studentService.findById(id);

        if (student.getDepartmentId().intValue() != departmentLeader.getDepartmentId().intValue()) {
            return null;
        }

        StudentScoreData studentScoreData = studentScoreService.findScoreToStudent(student);

        ModelAndView modelAndView = new ModelAndView("/departmentLeader/detailInfoOfStudentScore.html");
        modelAndView.addObject("studentInfo", studentScoreData);

        return modelAndView;
    }
}
