package com.fhx.gdms.controller.teacher.controllers;

import com.fhx.gdms.controller.student.scoreItem.model.ScoreItemModel;
import com.fhx.gdms.controller.student.scoreItem.service.ScoreItemService;
import com.fhx.gdms.controller.student.supportUtil.ApiResult;
import com.fhx.gdms.controller.student.user.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/scoreItem")
public class ScoreItemController {

    @Autowired
    private ScoreItemService scoreItemService;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult save(ScoreItemModel model) {
        ApiResult apiResult = new ApiResult();

        UserModel userInfo = (UserModel) session.getAttribute("userInfo");
        if (userInfo.getIdentify() != 4) {
            apiResult.setCode(-1);
            apiResult.setMsg("权限不足");
            return apiResult;
        }

        Integer flag = scoreItemService.saveScoreItem(model);

        if (flag == 0) {
            apiResult.setCode(0);
            apiResult.setMsg("添加成功");
            apiResult.setData(model);
        } else if (flag == 1){
            apiResult.setCode(0);
            apiResult.setMsg("评分项目已存在！");
        }else{
            apiResult.setCode(-1);
            apiResult.setMsg("ERROR！");
        }
        return apiResult;
    }

    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult updateStatus(Integer id, Integer status) {
        ApiResult apiResult = new ApiResult();

        UserModel userInfo = (UserModel) session.getAttribute("userInfo");
        if (userInfo.getIdentify() != 4) {
            apiResult.setCode(-1);
            apiResult.setMsg("权限不足");
            return apiResult;
        }

        ScoreItemModel model = new ScoreItemModel();
        model.setId(id);
        model.setStatus(status);

        model = scoreItemService.updateStatus(model);

        if (model == null) {
            apiResult.setCode(-1);
            apiResult.setMsg("ERROR！");
        } else if (model != null){
            apiResult.setCode(0);
            apiResult.setMsg("已更新！");
        }
        return apiResult;
    }

    @RequestMapping(value = "/updateItem", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult updateItem(ScoreItemModel model) {
        ApiResult apiResult = new ApiResult();

        UserModel userInfo = (UserModel) session.getAttribute("userInfo");
        if (userInfo.getIdentify() != 4) {
            apiResult.setCode(-1);
            apiResult.setMsg("权限不足");
            return apiResult;
        }

        ScoreItemModel exitModel = scoreItemService.findById(model.getId());
        if (exitModel.getStatus() ==1){
            apiResult.setCode(-1);
            apiResult.setMsg("启用状态，禁止修改！");
            return apiResult;
        }

        model = scoreItemService.updateItem(model);

        if (model == null) {
            apiResult.setCode(-1);
            apiResult.setMsg("ERROR！");
        } else if (model != null){
            apiResult.setCode(0);
            apiResult.setMsg("已更新！");
        }
        return apiResult;
    }
    @RequestMapping(value = "/listAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult listAll() {
        ApiResult apiResult = new ApiResult();

        List<ScoreItemModel> departmentModelList = scoreItemService.listAll();

        apiResult.setData(departmentModelList);

        return apiResult;
    }

    @RequestMapping(value = "/countRate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult countRate() {
        ApiResult apiResult = new ApiResult();

        List<Map<String, Integer>> result = scoreItemService.countRate();

        apiResult.setData(result);

        return apiResult;
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult findById(Integer id) {
        ApiResult apiResult = new ApiResult();

        ScoreItemModel result = scoreItemService.findById(id);
        if (result == null){
            apiResult.setCode(-1);
            apiResult.setMsg("记录不存在");

        }else{
            apiResult.setCode(0);
            apiResult.setData(result);
        }

        return apiResult;
    }
}
