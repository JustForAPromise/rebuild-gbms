package com.fhx.gdms.selectRecord.controllers;

import com.fhx.gdms.selectRecord.model.SelectRecordModel;
import com.fhx.gdms.selectRecord.service.SelectRecordService;
import com.fhx.gdms.supportUtil.ApiResult;
import com.fhx.gdms.user.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/selectRecord")
public class SelectRecordController {

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

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult update(SelectRecordModel model) {
        ApiResult apiResult = new ApiResult();

        model = selectRecordService.updateSelect(model);

        if (model != null) {
            apiResult.setCode(0);
            apiResult.setMsg("更新成功");
            apiResult.setData(model);
        } else {
            apiResult.setCode(-1);
            apiResult.setMsg("ERROR！");
        }
        return apiResult;
    }

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
