package login.com.scoreItem.model;

import lombok.Data;

import java.util.Date;

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
     * 类型 1指导教师评分  2评阅教师评分  3答辩教师评分
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
