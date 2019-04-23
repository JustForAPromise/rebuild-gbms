package com.fhx.gdms.controller.teacher.controllers;

import com.fhx.gdms.service.material.model.MaterialModel;
import com.fhx.gdms.service.material.service.TaskBookService;
import com.fhx.gdms.service.projections.service.ProjectionService;
import com.fhx.gdms.service.user.model.UserModel;
import com.fhx.gdms.supportUtil.ApiResult;
import com.fhx.gdms.supportUtil.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;

@Controller
@RequestMapping("/teacher/taskBook")
public class TaskBookOfTeacherController {

    @Autowired
    private TaskBookService taskBookService;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/updateAudit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult updateAudit(Integer id, Integer status, String remark) {
        ApiResult apiResult = new ApiResult();
        //获取用户信息

        MaterialModel result = taskBookService.updateAudit(id, status, remark);

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

        List<MaterialModel> list = taskBookService.listTaskBook(teacher, student);

        apiResult.setCode(0);
        apiResult.setData(list);
        return apiResult;
    }

    @RequestMapping(value = "/record/{id}", method = RequestMethod.GET)
    void record(@PathVariable("id") Integer id, HttpServletResponse response) throws UnsupportedEncodingException {
        ApiResult apiResult = new ApiResult();

        MaterialModel taskBookModel = taskBookService.findById(id);

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
