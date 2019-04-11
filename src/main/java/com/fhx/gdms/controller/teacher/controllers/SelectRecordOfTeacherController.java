package com.fhx.gdms.controller.teacher.controllers;

import com.fhx.gdms.service.selectRecord.model.SelectRecordModel;
import com.fhx.gdms.service.selectRecord.service.SelectRecordService;
import com.fhx.gdms.service.studentNumOfTeacher.model.StudentNumOfTeacherModel;
import com.fhx.gdms.service.studentNumOfTeacher.service.StudentNumOfTeacherService;
import com.fhx.gdms.service.user.model.UserModel;
import com.fhx.gdms.supportUtil.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/teacher:selectRecord")
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
