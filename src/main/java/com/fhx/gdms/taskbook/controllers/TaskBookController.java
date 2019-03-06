package com.fhx.gdms.taskbook.controllers;

import com.fhx.gdms.supportUtil.ApiResult;
import com.fhx.gdms.supportUtil.FileUtil;
import com.fhx.gdms.taskbook.service.TaskBookService;
import com.fhx.gdms.user.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
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



    // 获取指定目录下的第一个文件

    File path = null;
        try {
        path = new File(ResourceUtils.getURL("classpath:").getPath());
    } catch (
    FileNotFoundException e) {
        e.printStackTrace();
    }
    String filePath = path.getParentFile().getParentFile().getParent() + File.separator + "test" + File.separator;

    File scFileDir = new File(filePath);
    File TrxFiles[] = scFileDir.listFiles();
        System.out.println(TrxFiles[0]);
    String fileName = TrxFiles[0].getName(); //下载的文件名

    // 如果文件名不为空，则进行下载
        if (fileName != null) {
        //设置文件路径
        String realPath = filePath;
        File file = new File(realPath, fileName);

        // 如果文件名存在，则进行下载
        if (file.exists()) {
            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

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
                System.out.println("Download the song successfully!");
            } catch (Exception e) {
                System.out.println("Download the song failed!");
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
        return null;
}
