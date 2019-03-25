package com.fhx.gdms.service.defence.model;

import lombok.Data;

import java.util.Date;

@Data
public class DefenceModel {

    Integer id;

    /**
     * 名称
     */
    String name;

    /**
     * 答辩地址
     */
    String address;

    /**
     * 答辩时间起
     */
    Date start_time;

    /**
     * 答辩时间止
     */
    Date end_time;

    /**
     * 创建时间
     */
    Date create_time;
}
