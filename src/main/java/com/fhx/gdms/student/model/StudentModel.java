package com.fhx.gdms.student.model;

import com.fhx.gdms.department.model.DepartmentModel;
import com.fhx.gdms.major.model.MajorModel;
import com.fhx.gdms.teacher.model.TeacherModel;
import lombok.Data;

import java.util.Date;

@Data
public class StudentModel {

    private Integer id;

    /**
     * 学号
     */
    private String no;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 相片
     */
    private String image;

    /**
     *
     */
    private String identify;

    /**
     * 简介
     */
    private String introduce;

    /**
     * 备注
     */
    private String remark;
    /**
     * 密码
     */
    private String password;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 系别id
     */
    private Integer departmentId;

    /**
     * 专业id
     */
    private Integer majorId;

    /**
     * 指导教师id
     */
    private Integer teacherId;

    private DepartmentModel departmentModel;

    private MajorModel majorModel;

    private TeacherModel teacherModel;
}
