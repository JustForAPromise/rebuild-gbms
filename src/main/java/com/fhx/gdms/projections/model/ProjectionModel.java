package com.fhx.gdms.projections.model;

import lombok.Data;

import java.util.Date;

@Data
public class ProjectionModel {

    Integer id;
    /**
     * 课题编号
     */
    String no;

    /**
     * 标题
     */
    String title;

    /**
     * 简介
     */
    String introduce;

    /**
     * 要求
     */
    String require;

    /**
     * 备注
     */
    String remark;

    /**
     * 创建时间
     */
    Date create_time;

    /**
     * 更新时间
     */
    Date update_time;

    /**
     * 教师id
     */
    Integer teacher_id;

    /**
     * 学生id
     */
    Integer student_id;
}
