package com.fhx.gdms.materialStatus.repository;

import com.fhx.gdms.materialStatus.model.MaterialStatusModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface MaterialStatusRepository {

    @Select("SELECT * FROM tb_material_status where id = #{id}")
    @Results(id = "materialStatusMap", value = {
            @Result(column = "id", property = "id", javaType = Integer.class),
            @Result(column = "task_submit_status", property = "taskSubmitStatus", javaType = Integer.class),
            @Result(column = "task_audit_status", property = "taskAuditStatus", javaType = Integer.class),
            @Result(column = "theses_submit_status", property = "thesesSubmitStatus", javaType = Integer.class),
            @Result(column = "theses_audit_status", property = "thesesAuditStatus", javaType = Integer.class),

            @Result(column = "create_time", property = "createTime", javaType = Date.class),
            @Result(column = "update_time", property = "updateTime", javaType = Date.class),

            @Result(column = "student_id", property = "studentId", javaType = Integer.class),
            @Result(column = "teacher_id", property = "teacherId", javaType = Integer.class),
            @Result(column = "projection_id", property = "projectionId", javaType = Integer.class)
    })
    MaterialStatusModel findById(Integer id);

    @InsertProvider(type = MaterialStatusProvider.class, method = "save")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(MaterialStatusModel model);

    @SelectProvider(type = MaterialStatusProvider.class, method = "findList")
    @ResultMap("materialStatusMap")
    List<MaterialStatusModel> findList(MaterialStatusModel model);

    @Select("SELECT * FROM tb_material_status where student_id = #{studentId} AND  teacher_id = #{teacherId} AND projection_id = #{projectionId} LIMIT 1")
    @ResultMap("materialStatusMap")
    MaterialStatusModel findOne(MaterialStatusModel taskBookModel);

    @UpdateProvider(type = MaterialStatusProvider.class, method = "updateModel")
    void update(MaterialStatusModel model);

    /********** 内部类 *********/
    class MaterialStatusProvider {
        public String save(MaterialStatusModel model) {
            SQL sql = new SQL();
            sql.INSERT_INTO("tb_material_status");

            if (model.getTaskSubmitStatus() != null) {
                sql.VALUES("task_submit_status ", " #{taskSubmitStatus}");
            }
            if (model.getTaskAuditStatus() != null) {
                sql.VALUES("task_audit_status ", " #{taskAuditStatus}");
            }
            if (model.getThesesSubmitStatus() != null) {
                sql.VALUES("theses_submit_status ", " #{thesesSubmitStatus}");
            }
            if (model.getThesesAuditStatus() != null) {
                sql.VALUES("theses_audit_status ", " #{thesesAuditStatus}");
            }

            sql.VALUES("update_time", "now()");
            sql.VALUES("create_time", "now()");

            sql.VALUES("student_id", "#{studentId}");
            sql.VALUES("teacher_id", "#{teacherId}");
            sql.VALUES("projection_id", "#{projectionId}");

            return sql.toString();
        }

        public String updateModel(MaterialStatusModel model) {
            SQL sql = new SQL();
            sql.UPDATE("tb_material_status");
            if (model.getTaskSubmitStatus() != null) {
                sql.SET("task_submit_status = #{taskSubmitStatus}");
            }
            if (model.getTaskAuditStatus() != null) {
                sql.SET("task_audit_status = #{taskAuditStatus}");
            }
            if (model.getThesesSubmitStatus() != null) {
                sql.SET("theses_submit_status = #{thesesSubmitStatus}");
            }
            if (model.getThesesAuditStatus() != null) {
                sql.SET("theses_audit_status = #{thesesAuditStatus}");
            }
            sql.SET("update_time = now()");

            sql.WHERE("student_id = #{studentId}");
            sql.WHERE("teacher_id = #{teacherId}");
            sql.WHERE("projection_id = #{projectionId}");
            return sql.toString();
        }

        public String findList(MaterialStatusModel model) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("tb_material_status ");

            if (model.getTaskSubmitStatus() != null) {
                sql.WHERE("task_submit_status = #{taskSubmitStatus}");
            }
            if (model.getTaskAuditStatus() != null) {
                sql.WHERE("task_audit_status = #{taskAuditStatus}");
            }
            if (model.getThesesSubmitStatus() != null) {
                sql.WHERE("theses_submit_status = #{thesesSubmitStatus}");
            }
            if (model.getThesesAuditStatus() != null) {
                sql.WHERE("theses_audit_status = #{thesesAuditStatus}");
            }
            if (model.getStudentId() != null) {
                sql.WHERE("student_id = #{studentId}");
            }
            if (model.getTeacherId() != null) {
                sql.WHERE("teacher_id = #{teacherId}");
            }
            if (model.getProjectionId() != null) {
                sql.WHERE("projection_id = #{projectionId}");
            }
            return sql.toString();
        }
    }
}
