package com.fhx.gdms.projections.model;

import com.fhx.gdms.department.model.DepartmentModel;
import com.fhx.gdms.major.model.MajorModel;
import com.fhx.gdms.user.model.UserModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProjectionModel {

    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 简介
     */
    private String introduce;

    /**
     * 要求
     */
    private String demand;

    /**
     * 审核状态 0未审核 1通过 2未通过
     */
    private Integer auditStatus;

    /**
     * 审核备注
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
     * 教师id
     */
    private Integer teacherId;

    /**
     * 学生id
     */
    private Integer studentId;

    /**
     * 系别id
     */
    private Integer departmentId;

    /**
     * 专业id
     */
    private Integer majorId;

    private UserModel teacherModel;

    private UserModel studentModel;

    private DepartmentModel departmentModel;

    private MajorModel majorModel;

    /******** 辅助参数 ******************/
    private List<Integer> projectionIdNotIn;
    private List<Integer> projectionIdIn;
}
