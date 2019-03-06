package com.fhx.gdms.taskbook.controllers;

import com.fhx.gdms.supportUtil.ApiResult;
import com.fhx.gdms.supportUtil.FileUtil;
import com.fhx.gdms.taskbook.service.TaskBookService;
import com.fhx.gdms.user.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/taskBook")
public class TaskBookController {

    @Autowired
    private TaskBookService taskBookService;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult save(@RequestParam("file") MultipartFile file) {
        ApiResult apiResult = new ApiResult();

        if (file.isEmpty()) {
            apiResult.setCode(-1);
            apiResult.setMsg("Please select a file to upload");
            return apiResult;
        }

        String uploadDir = FileUtil.getThesesFileDir();

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDir + file.getOriginalFilename());
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
