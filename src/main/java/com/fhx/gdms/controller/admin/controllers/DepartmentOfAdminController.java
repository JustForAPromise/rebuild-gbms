package com.fhx.gdms.controller.admin.controllers;

import com.fhx.gdms.service.department.model.DepartmentModel;
import com.fhx.gdms.service.department.service.DepartmentService;
import com.fhx.gdms.supportUtil.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/admin/department")
public class DepartmentOfAdminController {
    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult save(String departmentName){
        ApiResult apiResult = new ApiResult();

        DepartmentModel model = new DepartmentModel();
        model.setDepartment(departmentName);

        model = departmentService.saveDepartment(model);

        if (model != null){
            apiResult.setCode(0);
            apiResult.setMsg("添加成功");
            apiResult.setData(model);
        }else{
            apiResult.setCode(-1);
            apiResult.setMsg("该系名已存在");
        }
        return apiResult;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult update(Integer id, String departmentName){
        ApiResult apiResult = new ApiResult();

        DepartmentModel model = new DepartmentModel();
        model.setId(id);
        model.setDepartment(departmentName);

        model = departmentService.updateDepartment(model);

        if (model != null){
            apiResult.setCode(0);
            apiResult.setMsg("更新成功");
            apiResult.setData(model);
        }else{
            apiResult.setCode(-1);
            apiResult.setMsg("更新失败，系名已存在！");
        }
        return apiResult;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult listAll(){
        ApiResult apiResult = new ApiResult();

        List<DepartmentModel> departmentModelList = departmentService.findAll();

        apiResult.setData(departmentModelList);

        return apiResult;
    }

    @RequestMapping(value = "/findByName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult findByName(String departmentName){
        ApiResult apiResult = new ApiResult();

        departmentName = "%"+departmentName+"%";
        List<DepartmentModel> departmentModelList = departmentService.findByName(departmentName);

        apiResult.setData(departmentModelList);

        return apiResult;
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult deleteById(Integer id){
        ApiResult apiResult = new ApiResult();

        departmentService.deleteById(id);

        apiResult.setCode(0);
        apiResult.setMsg("已删除");
        return apiResult;
    }
}
