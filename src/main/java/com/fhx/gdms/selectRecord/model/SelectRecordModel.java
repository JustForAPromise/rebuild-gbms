package com.fhx.gdms.selectRecord.model;

import com.fhx.gdms.projections.model.ProjectionModel;
import com.fhx.gdms.user.model.UserModel;
import lombok.Data;

import java.util.Date;

@Data
public class SelectRecordModel {

    private Integer id;

    /**
     * 0未处理 1通过 2拒绝
     */
    private Integer auditStatus;

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
    private Integer projectionId;

    private UserModel teacherModel;

    private UserModel studentModel;

    private ProjectionModel projectionModel;
}
