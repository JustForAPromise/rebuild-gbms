package com.fhx.gdms.service.studentNumOfTeacher.model;

import login.com.user.model.UserModel;
import lombok.Data;

import java.util.Date;

@Data
public class StudentNumOfTeacherModel {

    private Integer id;

    /**
     * 可指导学生人数
     */
    private Integer studentNum;

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


    private UserModel teacherModel;
}
