package com.fhx.gdms.selectRecord.repository;

import com.fhx.gdms.selectRecord.model.SelectRecordModel;
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

    @Update("update stu_tea_pro_record SET audit_status = #{auditStatus} WHERE id = #{id}")
    void update(SelectRecordModel model);

    @Select("SELECT * FROM stu_tea_pro_record WHERE id = #{id}")
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

    @Delete("DELETE FROM stu_tea_pro_record WHERE id = #{id}")
    void deleteById(Integer id);

    @Select("SELECT * FROM stu_tea_pro_record WHERE  department_id= #{departmentId} AND student_id is null AND audit_status = 1")
    @ResultMap(value = "recordMap")
    List<SelectRecordModel> listAllProjection(SelectRecordModel model);

    @Select("SELECT * FROM stu_tea_pro_record WHERE  teacher_id= #{teacherId}")
    @ResultMap(value = "recordMap")
    List<SelectRecordModel> findByTeacherId(Integer teacherId);

    @Update("update stu_tea_pro_record SET audit_status = 2 WHERE id = #{id}")
    Integer refuceStudent(Integer id);

    @Update("update stu_tea_pro_record SET audit_status = 1 WHERE id = #{id}")
    Integer receiveStudent(Integer id);


    @Select("SELECT projection_id FROM stu_tea_pro_record WHERE  student_id= #{userId}")
    List<Integer> findByUserId(@Param("userId")Integer userId);


    @Select("SELECT count(*) FROM stu_tea_pro_record WHERE  student_id= #{studentId} AND audit_status = #{status} ")
    Integer findTotalByStudentId(@Param("studentId")Integer studentId, @Param("status")Integer status);

    @Delete("DELETE FROM stu_tea_pro_record WHERE student_id = #{studentId} AND  audit_status != 1")
    void refuseOtherRequestOfStudent(@Param("studentId")Integer studentId);

    @Select("SELECT * FROM stu_tea_pro_record WHERE  teacher_id= #{teacherId} AND audit_status = 1")
    @ResultMap(value = "recordMap")
    List<SelectRecordModel> listReceiveStudent(Integer teacherId);

    @Select("SELECT * FROM stu_tea_pro_record WHERE  student_id= #{teacherId} AND audit_status = 1")
    @ResultMap(value = "recordMap")
    SelectRecordModel findHavedSelectedRecordByStudentId(Integer studentId);

    /********** 内部类 *********/

    class SelectRecordProvider {
        public String save(SelectRecordModel model) {
            SQL sql = new SQL();
            sql.INSERT_INTO("stu_tea_pro_record");
            sql.VALUES("audit_status", "0");

            sql.VALUES("update_time", "now()");
            sql.VALUES("create_time", "now()");

            sql.VALUES("teacher_id", "#{teacherId}");
            sql.VALUES("projection_id", "#{projectionId}");
            sql.VALUES("student_id", "#{studentId}");
            return sql.toString();
        }

    }
}