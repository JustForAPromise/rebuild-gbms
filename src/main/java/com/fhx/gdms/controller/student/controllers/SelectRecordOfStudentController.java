package com.fhx.gdms.controller.student.controllers;

import com.fhx.gdms.service.selectRecord.model.SelectRecordModel;
import com.fhx.gdms.service.selectRecord.service.SelectRecordService;
import com.fhx.gdms.service.user.model.UserModel;
import com.fhx.gdms.supportUtil.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/student:selectRecord")
public class SelectRecordOfStudentController {

    @Autowired
    private SelectRecordService selectRecordService;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult save(SelectRecordModel model) {
        ApiResult apiResult = new ApiResult();
        UserModel userModel = (UserModel) session.getAttribute("userInfo");

        Integer total;

        total = selectRecordService.findTotalByUserId(userModel.getId(), 1);
        if (!(total <= 0)) {
            apiResult.setCode(-1);
            apiResult.setMsg("你所选课题已被教师接收！\n选课能功能不再对你开发，详情请看个人信息");
            return apiResult;
        }

        total = selectRecordService.findTotalByUserId(userModel.getId(), 0);
        if (total >= 5) {
            apiResult.setCode(-1);
            apiResult.setMsg("可选课题数:5 \n你已选满，请等待教师查阅！");
            return apiResult;
        }

        model.setStudentId(userModel.getId());

        model = selectRecordService.saveSelect(model);

        if (model != null) {
            apiResult.setCode(0);
            apiResult.setMsg("已选择，待教师审核");
            apiResult.setData(model);
        } else {
            apiResult.setCode(-1);
            apiResult.setMsg("ERROR");
        }
        return apiResult;
    }
}
