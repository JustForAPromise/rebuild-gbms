package com.fhx.gdms.controller.student.controllers;

import com.fhx.gdms.controller.student.defence.model.DefenceModel;
import com.fhx.gdms.controller.student.defence.service.DefenceService;
import com.fhx.gdms.controller.student.supportUtil.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/defence")
public class DefenceController {
    @Autowired
    private DefenceService defenceService;
    
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult save(String departmentName){
        ApiResult apiResult = new ApiResult();

        DefenceModel model = new DefenceModel();


        return apiResult;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult listAll(){
        ApiResult apiResult = new ApiResult();


        return apiResult;
    }


}
