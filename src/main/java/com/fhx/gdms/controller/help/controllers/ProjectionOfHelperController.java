package com.fhx.gdms.controller.help.controllers;

import com.fhx.gdms.service.power.service.PowerService;
import com.fhx.gdms.service.projections.model.ProjectionModel;
import com.fhx.gdms.service.projections.service.ProjectionService;
import com.fhx.gdms.service.user.model.UserModel;
import com.fhx.gdms.supportUtil.ApiPageResult;
import com.fhx.gdms.supportUtil.ApiResult;
import com.fhx.gdms.supportUtil.FileUtil;
import jxl.CellView;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.*;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.Boolean;
import java.util.List;

@Controller
@RequestMapping("/helper/projection")
public class ProjectionOfHelperController {

    @Autowired
    private ProjectionService projectionService;

    @Autowired
    private PowerService powerService;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/findList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult findList(ProjectionModel projectionModel) {

        UserModel helper = (UserModel) session.getAttribute("userInfo");

        if (projectionModel.getTitle() != null) {
            projectionModel.setTitle("%" + projectionModel.getTitle() + "%");
        }
        projectionModel.setDepartmentId(helper.getDepartmentId());

        List<ProjectionModel> projectionModelList = projectionService.findList(projectionModel);

        Integer total = projectionService.findTotal(projectionModel);

        ApiResult apiResult = new ApiPageResult(projectionModelList, total, projectionModel.getPage(), projectionModel.getSize());

        return apiResult;
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult findById(Integer id) {
        ApiResult apiResult = new ApiResult();

        ProjectionModel result = projectionService.findById(id);

        apiResult.setCode(0);
        apiResult.setData(result);
        return apiResult;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult update(ProjectionModel model) {
        ApiResult apiResult = new ApiResult();

        model = projectionService.updateProjection(model);

        if (model != null) {
            apiResult.setCode(0);
            apiResult.setMsg("更新成功");
            apiResult.setData(model);
        } else {
            apiResult.setCode(-1);
            apiResult.setMsg("ERROR！");
        }
        return apiResult;
    }

    /************  课题信息导出  *************/
    @RequestMapping(value = "/down", method = RequestMethod.GET)
    void record(ProjectionModel model, HttpServletResponse response) throws UnsupportedEncodingException {

        List<ProjectionModel> projectionModelList = projectionService.findList(model);
        if (projectionModelList.size() == 0){
            return;
        }

        // 文件名
        String filename = "课题信息.xls";
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
                WritableSheet sheet = workbook.createSheet("课题信息", 0);
                sheet.setColumnView(0, 11);
                sheet.setColumnView(1, 17);

                sheet.setColumnView(2, 40);
                sheet.setColumnView(3, 50);
                sheet.setColumnView(4, 50);
                sheet.setColumnView(5, 12);
                sheet.setColumnView(6, 55);
                sheet.setColumnView(7, 45);

                CellView cellView= new CellView();
                cellView.setHidden(true);
                sheet.setColumnView(9, cellView);

                // 设置字体;
                WritableFont font = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
                WritableCellFormat cellFormat = new WritableCellFormat(font);

                // 设置背景颜色;
                cellFormat.setBackground(Colour.WHITE);

                //设置边框
                cellFormat.setBorder(Border.ALL, BorderLineStyle.DASH_DOT);

                // 设置文字居中对齐方式;
                cellFormat.setAlignment(Alignment.CENTRE);
                // 设置垂直居中;
                cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

                // 设置自动换行;
                cellFormat.setWrap(true);

                // 单元格
                Label label0 = new Label(0, 0, "指导教师", cellFormat);
                Label label1 = new Label(1, 0, "专业需求", cellFormat);
                Label label2 = new Label(2, 0, "课题标题", cellFormat);
                Label label3 = new Label(3, 0, "课题简介", cellFormat);
                Label label4 = new Label(4, 0, "课题要求", cellFormat);
                Label label5 = new Label(5, 0, "审核状态", cellFormat);
                Label label6 = new Label(6, 0, "审核意见", cellFormat);

                Label tip = new Label(7, 0, "审核状态可选值（0未审核 1通过 2未通过）", cellFormat);

                sheet.addCell(label0);
                sheet.addCell(label1);
                sheet.addCell(label2);
                sheet.addCell(label3);
                sheet.addCell(label4);
                sheet.addCell(label5);
                sheet.addCell(label6);
                sheet.addCell(tip);
                // 给第二行设置背景、字体颜色、对齐方式等等;
                WritableFont font2 = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
                WritableCellFormat cellFormat2 = new WritableCellFormat(font2);
                // 设置文字居中对齐方式;
                cellFormat2.setAlignment(Alignment.CENTRE);

                //设置边框
                cellFormat2.setBorder(Border.ALL, BorderLineStyle.DASH_DOT);

                // 设置垂直居中;
                cellFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);
                cellFormat2.setAlignment(Alignment.CENTRE);
                cellFormat2.setBackground(Colour.WHITE);
                cellFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);
                cellFormat2.setWrap(true);

                for (int r = 1, lenth = projectionModelList.size(); r <= lenth; r++){
                    Label teacherName = new Label(0, r, projectionModelList.get(r-1).getTeacherModel().getName(), cellFormat2);
                    Label major = new Label(1, r, projectionModelList.get(r-1).getMajorModel().getMajor(), cellFormat2);
                    Label title = new Label(2, r, projectionModelList.get(r-1).getTitle(), cellFormat2);
                    Label introduce = new Label(3, r, projectionModelList.get(r-1).getIntroduce(), cellFormat2);
                    Label demand = new Label(4, r, projectionModelList.get(r-1).getDemand(), cellFormat2);
                    Label status = new Label(5, r, String.valueOf(projectionModelList.get(r-1).getAuditStatus()), cellFormat2);
                    Label remark = new Label(6, r, projectionModelList.get(r-1).getAuditRemark(), cellFormat2);

                    Label id = new Label(9, r, String.valueOf(projectionModelList.get(r-1).getId()), cellFormat2);

                    sheet.addCell(teacherName);
                    sheet.addCell(major);
                    sheet.addCell(title);
                    sheet.addCell(introduce);
                    sheet.addCell(demand);
                    sheet.addCell(status);
                    sheet.addCell(remark);
                    sheet.addCell(id);

                }


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
            if(file.exists()){
                file.delete();
            }
        }
    }


    @RequestMapping(value = "/batchUpdate", method = RequestMethod.POST)
    @ResponseBody
    ApiResult batchUpdate(@RequestParam("file") MultipartFile file) {
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

                ProjectionModel projectionModel = new ProjectionModel();

                for (int i = 1, j= sheet.getRows(); i< j; i++){
                    if ("".equals(sheet.getCell(0,i).getContents())){
                        break;
                    }
                    projectionModel.setId(Integer.valueOf(sheet.getCell(9,i).getContents()));
//                    projectionModel.setTitle(sheet.getCell(2,i).getContents());
//                    projectionModel.setIntroduce(sheet.getCell(3,i).getContents());
//                    projectionModel.setDemand(sheet.getCell(4,i).getContents());
                    projectionModel.setAuditStatus(Integer.valueOf(sheet.getCell(5,i).getContents()));
                    projectionModel.setAuditRemark(sheet.getCell(6,i).getContents());

                    projectionService.update(projectionModel);
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
