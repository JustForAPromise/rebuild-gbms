package com.fhx.gdms.theses.model;

import java.util.Date;

public class ThesesModel {

    Integer id;

    /**
     * 论文文件链接
     */
    String fileLink;

    /**
     * 论文终稿链接
     */
    String finalFileLink;

    /**
     * 提交次数
     */
    Integer uploadNum;

    /**
     * 创建时间
     */
    Date createTime;

    /**
     * 更新时间
     */
    Date updateTime;

    /**
     * 指导教师id
     */
    Integer teacherId;

    /**
     * 学生id
     */
    Integer studentId;

    /**
     * 课题id
     */
    Integer projectionId;
}
