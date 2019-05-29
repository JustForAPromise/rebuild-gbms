package com.fhx.gdms.controller.student.controllers;

import login.com.supportUtil.ApiResult;
import login.com.supportUtil.FileUtil;
import login.com.material.model.MaterialModel;
import login.com.material.service.ThesesService;
import login.com.projections.service.ProjectionService;
import login.com.user.model.UserModel;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/student/theses")
public class ThesesOfStudentController {

    @Autowired
    private ThesesService thesesService;

    @Autowired
    private HttpSession session;

    @Autowired
    private ProjectionService projectionService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult save(@RequestParam("file") MultipartFile file) {
        ApiResult apiResult = new ApiResult();
        if (file.isEmpty()) {
            apiResult.setCode(-1);
            apiResult.setMsg("Please select a file to upload");
            return apiResult;
        }

        //获取用户信息
        UserModel student = (UserModel) session.getAttribute("userInfo");

        //构建model
        MaterialModel thesesModel = new MaterialModel();
        thesesModel.setStudentId(student.getId());
        thesesModel.setTeacherId(student.getTeacherId());
        thesesModel.setProjectionId(projectionService.findByUserIdAndTeacherId(student.getId(), student.getTeacherId()).getId());

        //查询是否已通过审核
        thesesModel.setAuditStatus(1);
        MaterialModel isPass = thesesService.findOne(thesesModel);
        if (isPass != null){
            apiResult.setCode(-1);
            apiResult.setMsg("审核已通过，无需再次提交！");
            return apiResult;
        }

        //拼接学生文件夹
        String uploadDir = FileUtil.getThesesFileDir();
        uploadDir = uploadDir + student.getNo() + File.separator;
        try {
            byte[] bytes = file.getBytes();

            Integer endIndex = file.getOriginalFilename().lastIndexOf(".")+1;

            String prefix=file.getOriginalFilename().substring(endIndex);
            String path = uploadDir + file.getOriginalFilename().substring(0,endIndex -1)+"-"+ new DateTime().toString("yyMMddHHmmss") + "." + prefix;

            File newFile = new File(path);
            if (!newFile.getParentFile().exists()) {
                newFile.getParentFile().mkdirs();
            }
            Files.write(Paths.get(path), bytes);

            //保存文件路径
            thesesModel.setFilePath(path);
        } catch (IOException e) {
            e.printStackTrace();
            apiResult.setCode(-1);
            apiResult.setMsg("上传失败！");
            return apiResult;
        }

        thesesModel.setAuditStatus(0);
        thesesService.saveTheses(thesesModel);

        apiResult.setCode(0);
        apiResult.setMsg("上传成功！");
        return apiResult;

    }

    @RequestMapping(value = "/records", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView records() {
        ApiResult apiResult = new ApiResult();

        //获取用户信息
        UserModel student = (UserModel) session.getAttribute("userInfo");
        //构建model
        MaterialModel taskBookModel = new MaterialModel();
        taskBookModel.setStudentId(student.getId());
        taskBookModel.setTeacherId(student.getTeacherId());
        taskBookModel.setProjectionId(projectionService.findByUserIdAndTeacherId(student.getId(), student.getTeacherId()).getId());

        List<MaterialModel> list = thesesService.findList(taskBookModel);

        ModelAndView modelAndView = new ModelAndView("/student/info/submitHistory.html");
        modelAndView.addObject("records", list);
        return modelAndView;
    }


    @RequestMapping(value = "/record/{id}", method = RequestMethod.GET)
    void record(@PathVariable("id") Integer id, HttpServletResponse response) throws UnsupportedEncodingException {
        ApiResult apiResult = new ApiResult();

        UserModel student = (UserModel) session.getAttribute("userInfo");

        MaterialModel thesesModel = thesesService.findById(id);

        if (student.getId() != thesesModel.getStudentId()) {
            return;
        }
        File file = new File(thesesModel.getFilePath());

        if (file.exists()) {
            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(FileUtil.getFileName(file).replace(" ", "_").getBytes("UTF-8"), "ISO-8859-1"));

            // 实现文件下载
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
