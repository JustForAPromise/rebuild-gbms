package com.fhx.gdms.taskbook.controllers;

import com.fhx.gdms.projections.service.ProjectionService;
import com.fhx.gdms.supportUtil.ApiResult;
import com.fhx.gdms.supportUtil.FileUtil;
import com.fhx.gdms.taskbook.model.TaskBookModel;
import com.fhx.gdms.taskbook.service.TaskBookService;
import com.fhx.gdms.user.model.UserModel;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/taskBook")
public class TaskBookController {

    @Autowired
    private TaskBookService taskBookService;


    @Autowired
    private ProjectionService projectionService;

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

        //获取用户信息
        UserModel student = (UserModel)session.getAttribute("userInfo");

        //构建model
        TaskBookModel taskBookModel = new TaskBookModel();
        taskBookModel.setStudentId(student.getId());
        taskBookModel.setTeacherId(student.getTeacherId());
        taskBookModel.setProjectionId(projectionService.findByUserIdAndTeacherId(student.getId(), student.getTeacherId()).getId());
        taskBookModel.setAuditStatus(0);

        //拼接学生文件夹
        String uploadDir = FileUtil.getTaskBookFileDir();
        uploadDir = uploadDir + student.getNo() + File.separator;
        try {
            byte[] bytes = file.getBytes();

            String prefix=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
            String path = uploadDir + file.getOriginalFilename()+"-"+ new DateTime().toString("yyMMddHHmmss") + "." + prefix;
            File newFile = new File(path);
            if (!newFile.getParentFile().exists()){
                newFile.getParentFile().mkdirs();
            }
            Files.write(Paths.get(path), bytes);

            //保存文件路径
            taskBookModel.setFilePath(path);
        } catch (IOException e) {
            e.printStackTrace();
            apiResult.setCode(-1);
            apiResult.setMsg("上传失败！");
            return apiResult;
        }

        taskBookService.saveTaskBook(taskBookModel);

        apiResult.setCode(0);
        apiResult.setMsg("上传成功！");
        return apiResult;
    }


    @RequestMapping(value = "/records", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView records() {
        ApiResult apiResult = new ApiResult();

        //获取用户信息
        UserModel student = (UserModel)session.getAttribute("userInfo");
        //构建model
        TaskBookModel taskBookModel = new TaskBookModel();
        taskBookModel.setStudentId(student.getId());
        taskBookModel.setTeacherId(student.getTeacherId());
        taskBookModel.setProjectionId(projectionService.findByUserIdAndTeacherId(student.getId(), student.getTeacherId()).getId());

        List<TaskBookModel> list = taskBookService.findList(taskBookModel);

        ModelAndView modelAndView = new ModelAndView("/student/info/submitHistory.html");
        modelAndView.addObject("records", list);
        return modelAndView;
    }


    @RequestMapping(value = "/updateAudit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult updateAudit(Integer id, Integer status, String remark) {
        ApiResult apiResult = new ApiResult();
        //获取用户信息

        TaskBookModel result = taskBookService.updateAudit(id, status, remark);

        apiResult.setCode(0);
        apiResult.setMsg("审批成功！");
        return apiResult;
    }

    @RequestMapping(value = "/listTaskBook", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult listTaskBook(String no, String name) {
        ApiResult apiResult = new ApiResult();
        //获取用户信息
        UserModel teacher = (UserModel)session.getAttribute("userInfo");

        UserModel student = new UserModel();
        student.setNo(no);
        student.setName(name);

        List<TaskBookModel> list = taskBookService.listTaskBook(teacher, student);

        apiResult.setCode(0);
        apiResult.setData(list);
        return apiResult;
    }

    @RequestMapping(value = "/record/{id}", method = RequestMethod.GET)
    void record(@PathVariable("id") Integer id, HttpServletResponse response) throws UnsupportedEncodingException {
        ApiResult apiResult = new ApiResult();

//        //获取用户信息
//        UserModel userModel = (UserModel) session.getAttribute("userInfo");
//        //构建model
//        TaskBookModel taskBookModel = taskBookService.findById(id);
//        if (userModel.getId() != taskBookModel.getStudentId()) {
//            return;
//        }

        TaskBookModel taskBookModel = taskBookService.findById(id);

        File file = new File(taskBookModel.getFilePath());

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
