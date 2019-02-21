package com.fhx.gdms.student.controllers;

import com.fhx.gdms.student.model.StudentModel;
import com.fhx.gdms.student.service.StudentService;
import com.fhx.gdms.supportUtil.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult save(StudentModel model){
        ApiResult apiResult = new ApiResult();

        String no = model.getNo();

        model = studentService.saveStudent(model);

        if (model != null){
            apiResult.setCode(0);
            apiResult.setMsg("添加成功");
            apiResult.setData(model);
        }else{
            apiResult.setCode(-1);
            apiResult.setMsg(no+" 已存在");
        }
        return apiResult;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult update(Integer id, String name){
        ApiResult apiResult = new ApiResult();

        StudentModel model = new StudentModel();
        model.setId(id);
        model.setName(name);

        model = studentService.updateStudent(model);

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

        List<StudentModel> departmentModelList = studentService.findAll();

        apiResult.setData(departmentModelList);

        return apiResult;
    }

    @RequestMapping(value = "/findStudent", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult findStudent(StudentModel model){
        ApiResult apiResult = new ApiResult();

        model.setName("%"+ model.getName() +"%");;
        List<StudentModel> departmentModelList = studentService.findStudent(model);

        apiResult.setData(departmentModelList);

        return apiResult;
    }


    @RequestMapping(value = "/findByMajorIdAndDepartmentId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult findByMajorIdAndDepartmentId(Integer departmentId, Integer majorId){
        ApiResult apiResult = new ApiResult();

        List<StudentModel> departmentModelList = studentService.findByMajorIdAndDepartmentId(departmentId, majorId );

        apiResult.setData(departmentModelList);

        return apiResult;
    }
}
