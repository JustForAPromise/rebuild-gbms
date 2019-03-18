package com.fhx.gdms.scoreItem.model;

import com.fhx.gdms.department.model.DepartmentModel;
import com.fhx.gdms.user.model.UserModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ScoreItemModel {

    private Integer id;

    /**
     * 成绩评分项目
     */
    private String itemName;

    /**
     * 成绩占比
     */
    private Integer scoreRate;

    /**
     * 简介
     */
    private String introduce;

    /**
     * 状态 0关闭 1启用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}