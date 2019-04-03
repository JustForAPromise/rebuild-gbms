package com.fhx.gdms.service.selectRecord.repository;

import com.fhx.gdms.service.selectRecord.model.SelectRecordModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface SelectRecordRepository {

    @InsertProvider(type = SelectRecordProvider.class, method = "save")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(SelectRecordModel model);

    @Update("update tb_stu_tea_pro_record SET audit_status = #{auditStatus} WHERE id = #{id}")
    void update(SelectRecordModel model);

    @Select("SELECT * FROM tb_stu_tea_pro_record WHERE id = #{id}")
    @Results(id = "recordMap", value = {
            @Result(column = "id", property = "id", javaType = Integer.class),
            @Result(column = "audit_status", property = "auditStatus", javaType = Integer.class),

            @Result(column = "create_time", property = "createTime", javaType = Date.class),
            @Result(column = "update_time", property = "updateTime", javaType = Date.class),

            @Result(column = "teacher_id", property = "teacherId", javaType = Integer.class),
            @Result(column = "student_id", property = "studentId", javaType = Integer.class),
            @Result(column = "projection_id", property = "projectionId", javaType = Integer.class)
    })
    SelectRecordModel findById(@Param("id") Integer id);

    @Delete("DELETE FROM tb_stu_tea_pro_record WHERE id = #{id}")
    void deleteById(Integer id);

    @Select("SELECT * FROM tb_stu_tea_pro_record WHERE  department_id= #{departmentId} AND student_id is null AND audit_status = 1")
    @ResultMap(value = "recordMap")
    List<SelectRecordModel> listAllProjection(SelectRecordModel model);

    @Select("SELECT * FROM tb_stu_tea_pro_record WHERE  teacher_id= #{teacherId}")
    @ResultMap(value = "recordMap")
    List<SelectRecordModel> findByTeacherId(Integer teacherId);

    @Update("update tb_stu_tea_pro_record SET audit_status = 2 WHERE id = #{id}")
    Integer refuceStudent(Integer id);

    @Update("update tb_stu_tea_pro_record SET audit_status = 1 WHERE id = #{id}")
    Integer receiveStudent(Integer id);


    @Select("SELECT projection_id FROM tb_stu_tea_pro_record WHERE  student_id= #{userId}")
    List<Integer> findByUserId(@Param("userId")Integer userId);


    @Select("SELECT count(*) FROM tb_stu_tea_pro_record WHERE  student_id= #{studentId} AND audit_status = #{status} ")
    Integer findTotalByStudentId(@Param("studentId")Integer studentId, @Param("status")Integer status);

    @Delete("DELETE FROM tb_stu_tea_pro_record WHERE student_id = #{studentId} AND  audit_status != 1")
    void refuseOtherRequestOfStudent(@Param("studentId")Integer studentId);

    @Select("SELECT * FROM tb_stu_tea_pro_record WHERE  teacher_id= #{teacherId} AND audit_status = 1")
    @ResultMap(value = "recordMap")
    List<SelectRecordModel> listReceiveStudent(Integer teacherId);

    @Select("SELECT * FROM tb_stu_tea_pro_record WHERE  student_id= #{teacherId} AND audit_status = 1")
    @ResultMap(value = "recordMap")
    SelectRecordModel findHavedSelectedRecordByStudentId(Integer studentId);

    @Select("SELECT count(1) FROM tb_stu_tea_pro_record WHERE  teacher_id= #{teacherId} AND audit_status = 1")
    Integer findTotalByTeacherId(@Param("teacherId")Integer teacherId);

    @SelectProvider(type = SelectRecordProvider.class, method = "findList")
    @ResultMap(value = "recordMap")
    List<SelectRecordModel> findList(SelectRecordModel model);

    @Delete("DELETE FROM tb_stu_tea_pro_record WHERE student_id = #{studentId}")
    void deleteByStudentId(Integer studentId);

    @SelectProvider(type = SelectRecordProvider.class, method = "findOne")
    @ResultMap(value = "recordMap")
    SelectRecordModel findOne(SelectRecordModel selectRecordModel);

    /********** 内部类 *********/

    class SelectRecordProvider {
        public String save(SelectRecordModel model) {
            SQL sql = new SQL();
            sql.INSERT_INTO("tb_stu_tea_pro_record");
            sql.VALUES("audit_status", "#{auditStatus}");

            sql.VALUES("update_time", "now()");
            sql.VALUES("create_time", "now()");

            sql.VALUES("teacher_id", "#{teacherId}");
            sql.VALUES("projection_id", "#{projectionId}");
            sql.VALUES("student_id", "#{studentId}");
            return sql.toString();
        }

        public String findList(SelectRecordModel model) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("tb_stu_tea_pro_record");
            if (model.getAuditStatus() != null && !"".equals(model.getAuditStatus())) {
                sql.WHERE("audit_status = #{auditStatus}");
            }
            if (model.getTeacherId() != null && !"".equals(model.getTeacherId())) {
                sql.WHERE("teacher_id = #{teacherId}");
            }
            if (model.getStudentId() != null && !"".equals(model.getStudentId())) {
                sql.WHERE("student_id = #{studentId}");
            }
            if (model.getProjectionId() != null && !"".equals(model.getProjectionId())) {
                sql.WHERE("projection_id = #{projectionId}");
            }
            return sql.toString();
        }

        public String findOne(SelectRecordModel model) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("tb_stu_tea_pro_record");
            if (model.getAuditStatus() != null && !"".equals(model.getAuditStatus())) {
                sql.WHERE("audit_status = #{auditStatus}");
            }
            if (model.getTeacherId() != null && !"".equals(model.getTeacherId())) {
                sql.WHERE("teacher_id = #{teacherId}");
            }
            if (model.getStudentId() != null && !"".equals(model.getStudentId())) {
                sql.WHERE("student_id = #{studentId}");
            }
            if (model.getProjectionId() != null && !"".equals(model.getProjectionId())) {
                sql.WHERE("projection_id = #{projectionId}");
            }
            return sql.toString()+"LIMIT 1";
        }
    }
}