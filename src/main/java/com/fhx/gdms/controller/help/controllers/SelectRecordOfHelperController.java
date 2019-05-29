package com.fhx.gdms.controller.help.controllers;

import login.com.supportUtil.ApiResult;
import login.com.selectRecord.model.SelectRecordModel;
import login.com.selectRecord.service.SelectRecordService;
import login.com.user.model.UserModel;
import login.com.user.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/helper/selectRecord")
public class SelectRecordOfHelperController {

    @Autowired
    private SelectRecordService selectRecordService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/updateRecord", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult updateRecord(SelectRecordModel model) {
        ApiResult apiResult = new ApiResult();

        UserModel helper = (UserModel) session.getAttribute("userInfo");
        if (helper == null) {
            apiResult.setCode(-1);
            apiResult.setMsg("请先登录");
            return apiResult;
        }else if (helper.getIdentify() != 3){
            apiResult.setCode(-1);
            apiResult.setMsg("权限不足");
            return apiResult;
        }

        UserModel student = studentService.findByNo(model.getStudentNo());
        if (student == null){
            apiResult.setCode(-1);
            apiResult.setMsg("学号："+model.getStudentNo()+" 不存在");
            return apiResult;
        }

        model.setStudentId(student.getId());

        SelectRecordModel result = selectRecordService.updateRecord(model);

        if (result != null) {
            apiResult.setCode(0);
            apiResult.setMsg("SUCCESS！");
        } else {
            apiResult.setCode(-1);
            apiResult.setMsg("ERROR！");
        }
        return apiResult;
    }
}
