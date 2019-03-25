package com.fhx.gdms.controller.student.studentScoreAllInfo.web;

import com.fhx.gdms.controller.student.studentScoreAllInfo.api.SearchDetailApiGet;
import com.fhx.gdms.controller.student.studentScoreAllInfo.data.StudentScoreData;
import com.fhx.gdms.controller.student.studentScoreAllInfo.service.StudentScoreService;
import com.fhx.gdms.controller.student.studentScoreRecord.model.StudentScoreRecordModel;
import com.fhx.gdms.controller.student.supportUtil.ApiResult;
import com.fhx.gdms.controller.student.user.model.UserModel;
import com.fhx.gdms.controller.student.user.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/studentScore")
public class StudentScoreController {

    @Autowired
    private StudentScoreService studentScoreService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/listAllStudent", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult listAll() {
        ApiResult apiResult = new ApiResult();

        UserModel teacher = (UserModel) session.getAttribute("userInfo");

        List<UserModel> students = studentService.findByTeacherId(teacher.getId());

        apiResult.setData(students);

        return apiResult;
    }

    @RequestMapping(value = "/recordOfOrdinary", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView recordOfOrdinary(SearchDetailApiGet receiveData) {
        UserModel teacher = (UserModel) session.getAttribute("userInfo");
        if (teacher.getIdentify() != 1) {
            return null;
        }
        receiveData.setType(1);

        StudentScoreData result = studentScoreService.findRecord(receiveData);

        ModelAndView modelAndView = new ModelAndView("/teacher/info/studentScoreDetail.html");
        modelAndView.addObject("scoreAllInfo", result);

        return modelAndView;
    }

    @RequestMapping(value = "/recordOfReview", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView recordOfReview(SearchDetailApiGet receiveData) {
        UserModel teacher = (UserModel) session.getAttribute("userInfo");
        if (teacher.getIdentify() != 1) {
            return null;
        }

        if (receiveData.getNo() != null){
            UserModel student = new UserModel();
            student.setDepartmentId(teacher.getDepartmentId());
            student.setNo(receiveData.getNo());
            student =  studentService.findOne(student);
            if (student == null){
                ModelAndView modelAndView = new ModelAndView("/reviewTeacher/studentScore.html");
                modelAndView.addObject("tips", "学号不存在");
                return modelAndView;
            }else{
                receiveData.setStudentId(student.getId());
            }
        }
        receiveData.setType(2);



        StudentScoreData result = studentScoreService.findRecord(receiveData);

        ModelAndView modelAndView = new ModelAndView("/teacher/info/studentScoreDetail.html");
        modelAndView.addObject("scoreAllInfo", result);

        return modelAndView;
    }

    @RequestMapping(value = "/recordOfResponse", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView recordOfResponse(SearchDetailApiGet receiveData) {
        UserModel teacher = (UserModel) session.getAttribute("userInfo");
        if (teacher.getIdentify() != 1) {
            return null;
        }

        if (receiveData.getNo() != null){
            UserModel student = new UserModel();
            student.setDepartmentId(teacher.getDepartmentId());
            student.setNo(receiveData.getNo());
            student =  studentService.findOne(student);
            if (student == null){
                ModelAndView modelAndView = new ModelAndView("/responseTeamLeader/studentScore.html");
                modelAndView.addObject("tips", "学号不存在");
                return modelAndView;
            }else{
                receiveData.setStudentId(student.getId());
            }
        }

        receiveData.setType(3);

        StudentScoreData result = studentScoreService.findRecord(receiveData);

        ModelAndView modelAndView = new ModelAndView("/teacher/info/studentScoreDetail.html");
        modelAndView.addObject("scoreAllInfo", result);

        return modelAndView;
    }

    @RequestMapping(value = "/updateNum", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult updateNum(StudentScoreRecordModel model) {
        ApiResult apiResult = new ApiResult();

        UserModel teacher = (UserModel) session.getAttribute("userInfo");
        if (teacher.getIdentify() != 1) {
            apiResult.setCode(-1);
            apiResult.setMsg("权限不足！");
            return apiResult;
        }
        studentScoreService.updateNum(model);

        apiResult.setCode(0);
        apiResult.setMsg("登记成功");
        return apiResult;
    }

    @RequestMapping(value = "/findScoreToStudent", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView findScoreToStudent() {
        UserModel student = (UserModel) session.getAttribute("userInfo");
        if (student == null) {
            return null;
        }

        StudentScoreData result = studentScoreService.findScoreToStudent(student);

        ModelAndView modelAndView = new ModelAndView("/student/info/soscore.html");
        modelAndView.addObject("scoreAllInfo", result);

        return modelAndView;
    }
}
