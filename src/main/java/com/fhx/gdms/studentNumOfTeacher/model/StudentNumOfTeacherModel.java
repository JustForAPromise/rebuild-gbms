package com.fhx.gdms.studentNumOfTeacher.model;

import com.fhx.gdms.department.model.DepartmentModel;
import com.fhx.gdms.user.model.UserModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

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
