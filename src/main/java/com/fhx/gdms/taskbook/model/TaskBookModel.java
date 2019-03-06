package com.fhx.gdms.taskbook.model;

import com.fhx.gdms.projections.model.ProjectionModel;
import com.fhx.gdms.user.model.UserModel;
import lombok.Data;

import java.util.Date;

@Data
public class TaskBookModel {

    private Integer id;

    /**
     * 文件地址
     */
    private String filePath;

    /**
     * 审阅状态 0未审阅 1通过 2未通过
     */
    private Integer auditStatus;


    /**
     * 评语
     */
    private String auditRemark;

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
