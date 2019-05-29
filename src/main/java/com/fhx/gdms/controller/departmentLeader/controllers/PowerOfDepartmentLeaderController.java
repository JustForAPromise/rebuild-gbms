package com.fhx.gdms.controller.departmentLeader.controllers;

import login.com.supportUtil.ApiResult;
import login.com.power.model.PowerModel;
import login.com.power.service.PowerService;
import login.com.user.model.UserModel;
import login.com.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/departmentLeader/power")
public class PowerOfDepartmentLeaderController {
    @Autowired
    private PowerService powerService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult save(UserModel model) {
        ApiResult apiResult = new ApiResult();

        String no = model.getNo();
        UserModel existModel = userService.findOne(model);

        if (existModel == null) {
            apiResult.setCode(-1);
            apiResult.setMsg("添加失败，请确认用户信息");
            return apiResult;
        }
        if(existModel.getIdentify() != 1){
            apiResult.setCode(-1);
            apiResult.setMsg("请选择教师身份用户！");
            return apiResult;
        }

        PowerModel powerModel = powerService.savePower(existModel);

        if (model != null) {
            apiResult.setCode(0);
            apiResult.setMsg("添加成功");
            apiResult.setData(powerModel);
        } else {
            apiResult.setCode(-1);
            apiResult.setMsg("工号:" + model.getNo() + " 已存在");
        }
        return apiResult;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult update(PowerModel model) {
        ApiResult apiResult = new ApiResult();

        model = powerService.update(model);

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

    @RequestMapping(value = "/findByDepartmentId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult findByName(Integer departmentId) {
        ApiResult apiResult = new ApiResult();

        List<PowerModel> powerModelList = powerService.findByDepartmentId(departmentId);

        apiResult.setCode(0);
        apiResult.setData(powerModelList);

        return apiResult;
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult deleteById(Integer id) {
        ApiResult apiResult = new ApiResult();

        PowerModel oldModel = powerService.deleteById(id);

        apiResult.setCode(0);
        apiResult.setData(oldModel);
        apiResult.setMsg("已删除");
        return apiResult;
    }
}
