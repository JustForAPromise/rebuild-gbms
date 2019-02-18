package com.fhx.gdms.department.model;

import lombok.Data;

import java.util.Date;

@Data
public class DepartmentModel {

    private Integer id;

    /**
     * 系别
     */
    private String department;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
