package com.fhx.gdms.service.studentScoreRecord.model;

import com.fhx.gdms.service.scoreItem.model.ScoreItemModel;
import lombok.Data;

import java.util.Date;

@Data
public class StudentScoreRecordModel {

    private Integer id;

    /**
     * 分数
     */
    private Integer scoreNum;

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
     * 评分项目id
     */
    private Integer scoreItemId;

    private ScoreItemModel scoreItemModel;
}
