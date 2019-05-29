package com.fhx.gdms.controller.teacher.controllers;

import login.com.supportUtil.ApiResult;
import login.com.selectRecord.model.SelectRecordModel;
import login.com.selectRecord.service.SelectRecordService;
import login.com.studentNumOfTeacher.model.StudentNumOfTeacherModel;
import login.com.studentNumOfTeacher.service.StudentNumOfTeacherService;
import login.com.user.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/teacher/selectRecord")
public class SelectRecordOfTeacherController {

    @Autowired
    private SelectRecordService selectRecordService;

    @Autowired
    private StudentNumOfTeacherService studentNumOfTeacherService;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/listStudent", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult listStudent() {
        ApiResult apiResult = new ApiResult();

        UserModel userModel = (UserModel) session.getAttribute("userInfo");

        List<SelectRecordModel> modelList = selectRecordService.listStudent(userModel.getId());

        if (modelList != null) {
            apiResult.setCode(0);
            apiResult.setData(modelList);
        } else {
            apiResult.setCode(-1);
            apiResult.setMsg("ERROR！");
        }
        return apiResult;
    }

    @RequestMapping(value = "/refuceStudent", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult refuceStudent(Integer id) {
        ApiResult apiResult = new ApiResult();

        Integer result = selectRecordService.refuceStudent(id);

        if (result > 0) {
            apiResult.setCode(0);
            apiResult.setMsg("SUCCESS！");
        } else {
            apiResult.setCode(-1);
            apiResult.setMsg("ERROR！");
        }
        return apiResult;
    }

    @RequestMapping(value = "/receiveStudent", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult receiveStudent(Integer id) {
        ApiResult apiResult = new ApiResult();

        UserModel userModel = (UserModel) session.getAttribute("userInfo");
        StudentNumOfTeacherModel model = studentNumOfTeacherService.findByTeacherId(userModel.getId());
        Integer totalOfHashReceive = selectRecordService.findTotalByTeacherId(userModel.getId());
        if (model.getStudentNum() <= totalOfHashReceive){
            apiResult.setCode(-1);
            apiResult.setMsg("可指导学生名额已满！");
            return apiResult;
        }

        Integer result = selectRecordService.receiveStudent(id);

        if (result > 0) {
            apiResult.setCode(0);
            apiResult.setMsg("SUCCESS！");
        } else {
            apiResult.setCode(-1);
            apiResult.setMsg("ERROR！");
        }
        return apiResult;
    }

    @RequestMapping(value = "/listReceiveStudent", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult listReceiveStudent() {
        ApiResult apiResult = new ApiResult();

        UserModel userModel = (UserModel) session.getAttribute("userInfo");

        List<SelectRecordModel> modelList = selectRecordService.listReceiveStudent(userModel.getId());

        if (modelList != null) {
            apiResult.setCode(0);
            apiResult.setData(modelList);
        } else {
            apiResult.setCode(-1);
            apiResult.setMsg("ERROR！");
        }
        return apiResult;
    }
}
