package com.fhx.gdms.student.model;

import lombok.Data;

import java.util.Date;

@Data
public class StudentModel {

    Integer id;

    /**
     * 学号
     */
    String no;

    /**
     * 姓名
     */
    String name;

    /**
     * 性别
     */
    Integer gender;

    /**
     * 相片
     */
    String image;

    /**
     *
     */
    String identify;

    /**
     * 简介
     */
    String introduce;

    /**
     * 备注
     */
    String remark;
    /**
     * 密码
     */
    String password;
    /**
     * 创建时间
     */
    Date createTime;

    /**
     * 更新时间
     */
    Date updateTime;

    /**
     * 系别id
     */
    Integer departmentId;

    /**
     * 专业id
     */
    Integer majorId;

    /**
     * 指导教师id
     */
    Integer teacherId;
}
