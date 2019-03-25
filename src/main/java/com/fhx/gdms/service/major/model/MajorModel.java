package com.fhx.gdms.service.major.model;

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
