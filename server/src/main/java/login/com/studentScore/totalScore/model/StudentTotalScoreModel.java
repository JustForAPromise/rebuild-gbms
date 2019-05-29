package login.com.studentScore.totalScore.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class StudentTotalScoreModel {

    private Integer id;

    /**
     * 学生总成绩
     */
    private BigDecimal scoreNum;

    /**
     * 成绩评级
     */
    private String level;

    /**
     * 成绩登记状态 O 指导老师已登记成绩  R 评阅老师已登记成绩  P 答辩组长已登记成绩
     * ORP 组合说明成绩登记完毕
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 学生ID
     */
    private Integer studentId;

    /**
     * 系别id
     */
    private Integer departmentId;

    /**
     * 专业Id
     */
    private Integer majorId;

    /**
     * 辅助查询参数 ： in查询参数
     */
    private List<Integer> studentIds;

    /**
     * 扩展模型：studentModel
     */

}
