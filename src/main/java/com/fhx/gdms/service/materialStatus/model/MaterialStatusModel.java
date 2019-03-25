package com.fhx.gdms.service.materialStatus.model;

import com.fhx.gdms.service.projections.model.ProjectionModel;
import com.fhx.gdms.service.user.model.UserModel;
import lombok.Data;

import java.util.Date;

@Data
public class MaterialStatusModel {

    private Integer id;

    /**
     * 任务书提交状态 0未提交 1已提交
     */
    private Integer taskSubmitStatus;

    /**
     * 任务书审阅状态 0未审核 1通过 2未通过
     */
    private Integer taskAuditStatus;

    /**
     * 论文提交状态 0未提交 1已提交
     */
    private Integer thesesSubmitStatus;

    /**
     * 论文审阅状态 0未审核 1未通过 2未通过
     */
    private Integer thesesAuditStatus;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 学生id
     */
    private Integer studentId;

    /**
     * 指导教师id
     */
    private Integer teacherId;

    /**
     * 课题id
     */
    private Integer projectionId;

    /**
     * 扩展：学生
     */
    private UserModel student;

    /**
     * 扩展：指导教师
     */
    private UserModel teacher;

    /**
     * 扩展：指导教师
     */
    private ProjectionModel projection;
}
