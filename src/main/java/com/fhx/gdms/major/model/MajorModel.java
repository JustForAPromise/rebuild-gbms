package com.fhx.gdms.major.model;

import lombok.Data;

import java.util.Date;

@Data
public class MajorModel {

    Integer id;

    /**
     * 专业名称
     */
    String major;

    /**
     * 简介
     */
    String introduce;

    /**
     * 创建时间
     */
    Date create_time;

    /**
     * 系别id
     */
    Integer department_id;
}
