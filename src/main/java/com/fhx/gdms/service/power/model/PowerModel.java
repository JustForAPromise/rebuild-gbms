package com.fhx.gdms.service.power.model;

import com.fhx.gdms.service.department.model.DepartmentModel;
import com.fhx.gdms.service.user.model.UserModel;
import lombok.Data;

import java.util.Date;

@Data
public class PowerModel {

    private Integer id;

    /**
     * 系负责人
     */
    private Boolean departmentLeader;

    /**
     * 评阅教师
     */
    private Boolean reviewTeacher;

    /**
     * 答辩组组长
     */
    private Boolean responseTeamLeader;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private Integer userId;

    private Integer departmentId;

    private UserModel userModel;

    private DepartmentModel departmentModel;
}