package com.fhx.gdms.power.model;

import com.fhx.gdms.department.model.DepartmentModel;
import com.fhx.gdms.user.model.UserModel;
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
     * 教务员
     */
    private Boolean senateMembers;

    /**
     * 课题审核员
     */
    private Boolean projectAuditor;

    /**
     * 答辩审核员
     */
    private Boolean answerAuditor;

    /**
     * 系统管理员
     */
    private Boolean systemAdministrator;

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
