package com.fhx.gdms.taskbook.model;

import lombok.Data;

import java.util.Date;

@Data
public class TaskBookModel {

    Integer id;

    /**
     * 文件地址
     */
    String file;

    /**
     * 终稿地址
     */
    String finalFile;

    /**
     * 提交次数
     */
    String uploadNum;

    /**
     * 创建时间
     */
    Date createTime;

    /**
     * 更新时间
     */
    Date updateTime;

    /**
     * 学生id
     */
    Integer studentId;

    /**
     * 指导教师id
     */
    Integer teacherId;

    /**
     * 课题id
     */
    Integer projectionId;


}
