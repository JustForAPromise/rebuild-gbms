package com.fhx.gdms.teacher.model;

import lombok.Data;

import java.util.Date;

@Data
public class TeacherModel {

    Integer id;

    /**
     * 工号
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
     * 职位
     */
    String position;

    /**
     * 密码
     */
    String password;

    /**
     * 权限
     */
    String identify;

    /**
     * 简介
     */
    String introduce;

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
}
