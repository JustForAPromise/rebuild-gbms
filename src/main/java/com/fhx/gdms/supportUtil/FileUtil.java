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
        String filePath = path.getParentFile().getParentFile().getParent()
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
        String filePath = path.getParentFile().getParentFile().getParent() + File.separator + "upload" + File.separator + "theses" + File.separator;

        return filePath;
    }
}
