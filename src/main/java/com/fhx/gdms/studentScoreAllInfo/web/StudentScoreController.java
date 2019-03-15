package com.fhx.gdms.studentScoreAllInfo.web;

import com.fhx.gdms.projections.model.ProjectionModel;
import com.fhx.gdms.projections.service.ProjectionService;
import com.fhx.gdms.scoreItem.service.ScoreItemService;
import com.fhx.gdms.studentScoreAllInfo.data.StudentScoreData;
import com.fhx.gdms.studentScoreAllInfo.service.StudentScoreService;
import com.fhx.gdms.studentScoreRecord.model.StudentScoreRecordModel;
import com.fhx.gdms.supportUtil.ApiResult;
import com.fhx.gdms.user.model.UserModel;
import com.fhx.gdms.user.service.StudentService;
import com.fhx.gdms.user.service.TeacherService;
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

    @RequestMapping(value = "/record", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView findByStudentId(Integer studentId) {
        UserModel teacher = (UserModel) session.getAttribute("userInfo");
        if (teacher.getIdentify() != 1) {
            return null;
        }

        StudentScoreData result = studentScoreService.findRecord(studentId);

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

}
