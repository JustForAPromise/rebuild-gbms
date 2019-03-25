package com.fhx.gdms.service.web;

import com.fhx.gdms.supportUtil.ApiResult;
import com.fhx.gdms.supportUtil.FileUtil;
import com.fhx.gdms.service.user.model.UserModel;
import com.fhx.gdms.service.user.service.StudentService;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.*;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.Boolean;

@Controller
@RequestMapping("/file")
public class ExcelOfStudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/getExcelOfStudent", method = RequestMethod.GET)
    void record(HttpServletResponse response) throws UnsupportedEncodingException {
        ApiResult apiResult = new ApiResult();

        // 文件名
        String filename = "学生信息导入模板.xls";
        // 写到服务器上
        String path = FileUtil.getBaseFileDir() + filename;

        File file = new File(path);

        try {
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
                // 创建写工作簿对象
                WritableWorkbook workbook = Workbook.createWorkbook(file);
                // 工作表
                WritableSheet sheet = workbook.createSheet("学生信息", 0);
                // 设置字体;
                WritableFont font = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
                WritableCellFormat cellFormat = new WritableCellFormat(font);

                // 设置背景颜色;
                cellFormat.setBackground(Colour.WHITE);

                // 设置边框;
                cellFormat.setBorder(Border.ALL, BorderLineStyle.DASH_DOT);

                // 设置文字居中对齐方式;
                cellFormat.setAlignment(Alignment.CENTRE);
                // 设置垂直居中;
                cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

                // 给sheet电子版中所有的列设置默认的列的宽度;
                sheet.getSettings().setDefaultColumnWidth(15);

                // 设置自动换行;
                cellFormat.setWrap(true);

                // 单元格
                Label label0 = new Label(0, 0, "学号", cellFormat);
                Label label1 = new Label(1, 0, "姓名", cellFormat);
                Label label2 = new Label(2, 0, "性别", cellFormat);
                Label label3 = new Label(3, 0, "联系电话", cellFormat);

                sheet.addCell(label0);
                sheet.addCell(label1);
                sheet.addCell(label2);
                sheet.addCell(label3);

                // 给第二行设置背景、字体颜色、对齐方式等等;
                WritableFont font2 = new WritableFont(WritableFont.ARIAL, 14, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
                WritableCellFormat cellFormat2 = new WritableCellFormat(font2);
                // 设置文字居中对齐方式;
                cellFormat2.setAlignment(Alignment.CENTRE);
                // 设置垂直居中;
                cellFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);
                cellFormat2.setBackground(Colour.WHITE);
                cellFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);
                cellFormat2.setWrap(true);

                //开始执行写入操作
                workbook.write();
                //关闭流
                workbook.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 第六步，下载excel

        OutputStream out = null;
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        // 下载文件能正常显示中文
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String(FileUtil.getFileName(file).getBytes("UTF-8"), "ISO-8859-1"));
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

    @RequestMapping(value = "/batchImportOfStudent", method = RequestMethod.POST)
    @ResponseBody
    ApiResult batchImportOfStudent(@RequestParam("departmentId") Integer departmentId,
                                   @RequestParam("majorId") Integer majorId,
                                   @RequestParam("file") MultipartFile file) {
        ApiResult apiResult = new ApiResult();
        Boolean isExcel = false;
        if (file.isEmpty()) {
            apiResult.setCode(-1);
            apiResult.setMsg("请选择导入文件！");
            return apiResult;
        } else if (!file.getOriginalFilename().endsWith("xls") && !file.getOriginalFilename().endsWith("xlsx")) {
            apiResult.setCode(-1);
            apiResult.setMsg("文件格式错误！");
            return apiResult;
        }else{
            try {
                Workbook workbook = Workbook.getWorkbook(file.getInputStream());
                Sheet sheet = workbook.getSheet(0);
                System.out.println(sheet.getColumns()+"  "+sheet.getRows());

                UserModel student = new UserModel();
                student.setDepartmentId(departmentId);
                student.setMajorId(majorId);

                for (int i = 1, j= sheet.getRows(); i< j; i++){
                    if ("".equals(sheet.getCell(0,i).getContents())){
                        break;
                    }
                    student.setNo(sheet.getCell(0,i).getContents());
                    student.setName(sheet.getCell(1,i).getContents());

                    if ("男".equals(sheet.getCell(2,i).getContents())) {
                        student.setGender(1);
                    }else if ("女".equals(sheet.getCell(2,i).getContents())){
                        student.setGender(2);
                    }
                    student.setPhone(sheet.getCell(3,i).getContents());
                    studentService.saveStudent(student);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (BiffException e) {
                e.printStackTrace();
            }
            apiResult.setCode(0);
            apiResult.setMsg("数据导入成功！");
            return apiResult;
        }
    }
}
