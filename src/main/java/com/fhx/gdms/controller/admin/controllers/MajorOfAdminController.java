package com.fhx.gdms.controller.admin.controllers;

import com.fhx.gdms.service.major.model.MajorModel;
import com.fhx.gdms.service.major.service.MajorService;
import com.fhx.gdms.service.user.model.UserModel;
import com.fhx.gdms.supportUtil.ApiResult;
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
@RequestMapping("/admin:major")
public class MajorOfAdminController {

    @Autowired
    private HttpSession session;

    @Autowired
    private MajorService majorService;

    @RequestMapping(value = "/initPage", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView initPage(Integer departmentId) {
        List<MajorModel> majorModelList = majorService.findByDepartmentId(departmentId);

        ModelAndView modelAndView = new ModelAndView("/admin/infoManage/major.html");
        modelAndView.addObject("majors", majorModelList);

        session.setAttribute("departmentId", departmentId);

        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult save(String majorName, String introduce) {
        ApiResult apiResult = new ApiResult();

        MajorModel model = new MajorModel();
        model.setMajor(majorName);
        model.setIntroduce(introduce);
        model.setDepartmentId((Integer) session.getAttribute("departmentId"));

        model = majorService.saveMajor(model);

        if (model != null) {
            apiResult.setCode(0);
            apiResult.setMsg("添加成功");
            apiResult.setData(model);
        } else {
            apiResult.setCode(-1);
            apiResult.setMsg("该专业已存在");
        }
        return apiResult;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult update(Integer id, String majorName, String introduce) {
        ApiResult apiResult = new ApiResult();

        MajorModel model = new MajorModel();
        model.setId(id);
        model.setMajor(majorName);
        model.setIntroduce(introduce);

        model = majorService.updateMajor(model);

        if (model != null) {
            apiResult.setCode(0);
            apiResult.setMsg("更新成功");
            apiResult.setData(model);
        } else {
            apiResult.setCode(-1);
            apiResult.setMsg("更新失败，专业已存在！");
        }
        return apiResult;
    }

    @RequestMapping(value = "/findByDepartmentId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult findByDepartmentId(Integer departmentId) {
        ApiResult apiResult = new ApiResult();

        session.setAttribute("departmentId", departmentId);

        List<MajorModel> majorModelList = majorService.findByDepartmentId(departmentId);

        apiResult.setCode(0);
        apiResult.setData(majorModelList);
        return apiResult;
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult deleteById(Integer id){
        ApiResult apiResult = new ApiResult();

        List<MajorModel> majorModelList = majorService.deleteMajorById(id);

        apiResult.setCode(0);
        apiResult.setMsg("已删除");
        apiResult.setData(majorModelList);
        return apiResult;
    }

    @RequestMapping(value = "/listMajorWithLoginUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult listMajorWithLoginUser() {
        ApiResult apiResult = new ApiResult();

        UserModel loginUser = (UserModel)session.getAttribute("userInfo");

        List<MajorModel> majorModelList = majorService.findByDepartmentId(loginUser.getDepartmentId());

        apiResult.setCode(0);
        apiResult.setData(majorModelList);
        return apiResult;
    }
}
