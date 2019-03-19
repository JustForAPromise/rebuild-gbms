package com.fhx.gdms.supportUtil;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @Auther fanhanxi
 * @Date 2019/3/6
 * @Description:
 */
public class FileUtil {

    public static String getTaskBookFileDir() {
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String filePath = path.getParent()
                + File.separator + "upload" + File.separator + "taskBook" + File.separator;
        return filePath;
    }
    public static String getThesesFileDir() {
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String filePath = path.getParent() + File.separator + "upload" + File.separator + "theses" + File.separator;

        return filePath;
    }

    public static String getBaseFileDir() {
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String filePath = path.getParent() + File.separator + "upload" + File.separator ;

        return filePath;
    }

    public static String getFileName(File file) {

        String sp = String.valueOf(File.separatorChar);
        String[] fileNames = null;

        if (sp.equals("\\")){
            fileNames = file.getPath().split("\\\\");
        }else{
            fileNames = file.getPath().split("/");
        }


        String fileName=fileNames[fileNames.length-1];

        return fileName;
    }
}
