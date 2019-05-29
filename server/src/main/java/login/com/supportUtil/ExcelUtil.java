package login.com.supportUtil;

import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.File;

/**
 * @Auther fanhanxi
 * @Date 2019/3/14
 * @Description:
 */
public class ExcelUtil {

    public static File createFile(){
        //第一步创建workbook
        HSSFWorkbook wb = new HSSFWorkbook();

        //第二步创建sheet
        HSSFSheet sheet = wb.createSheet("info");

        //第三步创建行row:添加表头0行
        HSSFRow row = sheet.createRow(0);
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);  //居中


        //第四步创建单元格
        HSSFCell cell = row.createCell(0);         //第一个单元格
        cell.setCellValue("delectedTime");                  //设定值
        cell.setCellStyle(style);                   //内容居中

        cell = row.createCell(1);                   //第二个单元格
        cell.setCellValue("shuzhangya");
        cell.setCellStyle(style);

        cell = row.createCell(2);                   //第二个单元格
        cell.setCellValue("shousuoya");
        cell.setCellStyle(style);

        cell = row.createCell(3);                   //第二个单元格
        cell.setCellValue("xinlv");
        cell.setCellStyle(style);


//
//        //第五步插入数据
//        for (int i = 0; i < result.size(); i++) {
//            row = sheet.createRow(i+1);
//            //创建单元格并且添加数据
//            row.createCell(0).setCellValue(new DateTime(model.getDetectedTime()).toString("yyyy-MM-dd HH:mm:ss"));
//            row.createCell(1).setCellValue(model.getDiastolicPressure());
//            row.createCell(2).setCellValue(model.getSystolicPressure());
//            row.createCell(3).setCellValue(model.getHeartRate());
//        }

//        //第六步将生成excel文件保存到指定路径下
//        try {
//            FileOutputStream fout = new FileOutputStream("D:\\errorCondition.xls");
//            wb.write(fout);
//            fout.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return null;
    }

}
