package com.fhx.gdms.admin.model;

import lombok.Data;

import java.util.Date;

@Data
public class AdminModel {

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
     * 备注
     */
    String remark;

    /**
     * 创建时间
     */
    Date createTime;

    /**
     * 更新时间
     */
    Date updateTime;
}
