package com.fhx.gdms.user.model;

import com.fhx.gdms.department.model.DepartmentModel;
import com.fhx.gdms.major.model.MajorModel;
import com.fhx.gdms.power.model.PowerModel;
import lombok.Data;

import java.util.Date;

@Data
public class UserModel {

    Integer id;

    /**
     * 工号
     */
    String no;

    /**
     * 姓名
     */
    String name;

    /**
     * 相片
     */
    String image;

    /**
     * 性别
     */
    Integer gender;

    /**
     * 密码
     */
    String password;

    /**
     * 身份 1教师 2学生
     */
    Integer identify;

    /**
     * 联系电话
     */
    private String phone;

//    /**
//     * 职位
//     */
//    private String position;

    /**
     * 简介
     */
    private String introduce;

    /**
     *  普通学生
     */
    private Boolean ordinaryStudent;

    /**
     * 普通教师
     */
    private Boolean ordinaryTeacher;

    /**
     * 普通教师
     */
    private Boolean systemAdministrator;

    /**
     * 教务员
     */
    private Boolean senateMembers;

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
    private Integer departmentId;

    /**
     * 专业id
     */
    private Integer majorId;

    /**
     * 指导教师id
     */
    private Integer teacherId;

    /**
     * 权限id
     */
    private Integer powerId;

    private DepartmentModel departmentModel;

    private MajorModel majorModel;

    private UserModel teacherModel;

    private PowerModel powerModel;
}
