package com.fhx.gdms.material.model;

import com.fhx.gdms.projections.model.ProjectionModel;
import com.fhx.gdms.user.model.UserModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MaterialModel {

    private Integer id;

    /**
     * 文件类型 1任务书 2论文
     */
    private Integer fileType;

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

    /*********** 辅助参数 *********/
    /**
     * 学生id  in参数
     */
    private List<Integer> studentIds;
}
