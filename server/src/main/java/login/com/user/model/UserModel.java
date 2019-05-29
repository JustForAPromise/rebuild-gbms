package login.com.user.model;

import login.com.supportUtil.PageBaseModel;
import login.com.department.model.DepartmentModel;
import login.com.major.model.MajorModel;
import login.com.power.model.PowerModel;
import login.com.projections.model.ProjectionModel;
import login.com.selectRecord.model.SelectRecordModel;
import lombok.Data;

import java.util.Date;

@Data
public class UserModel extends PageBaseModel {

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
     * 性别 1男 2女
     */
    Integer gender;

    /**
     * 密码
     */
    String password;

    /**
     * 身份 1教师 2学生 3教务员 4管理员
     */
    Integer identify;

    /**
     * 联系电话
     */
    private String phone;

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

    private ProjectionModel projectionModel;

    private SelectRecordModel projectionSelectModel;

    /**
     * 辅助参数：教务员  是否将未选题的提前筛选出
     */
    private Boolean withoutProjection;
}
