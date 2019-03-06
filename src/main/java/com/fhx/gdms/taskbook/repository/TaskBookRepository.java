package com.fhx.gdms.taskbook.repository;

import com.fhx.gdms.taskbook.model.TaskBookModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface TaskBookRepository {

    @Select("SELECT * FROM tb_task_book where id = #{id}")
    @Results(id = "taskBookMap", value = {
            @Result(column = "id", property = "id", javaType = Integer.class),
            @Result(column = "file_path", property = "filePath", javaType = String.class),
            @Result(column = "audit_status", property = "auditStatus", javaType = Integer.class),
            @Result(column = "audit_remark", property = "auditRemark", javaType = String.class),

            @Result(column = "create_time", property = "createTime", javaType = Date.class),
            @Result(column = "update_time", property = "updateTime", javaType = Date.class),

            @Result(column = "student_id", property = "studentId", javaType = Integer.class),
            @Result(column = "teacher_id", property = "teacherId", javaType = Integer.class),
            @Result(column = "projection_id", property = "projectionId", javaType = Integer.class)
    })
    TaskBookModel findById(Integer id);

    @InsertProvider(type = TaskBookProvider.class, method = "save")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(TaskBookModel model);

    @SelectProvider(type = TaskBookProvider.class, method = "findList")
    @ResultMap("taskBookMap")
    List<TaskBookModel> findList(TaskBookModel model);

    @Select("SELECT * FROM tb_task_book where student_id = #{studentId} AND " +
            "teacher_id = #{teacherId} AND projection_id = #{projectionId} AND audit_status = 0 LIMIT 1")
    @ResultMap("taskBookMap")
    TaskBookModel findOne(TaskBookModel taskBookModel);

    @Delete("DELETE FROM tb_task_book WHERE id = #{id}")
    void deleteById(@Param("id") Integer id);

    /********** 内部类 *********/
    class TaskBookProvider {
        public String save(TaskBookModel model) {
            SQL sql = new SQL();
            sql.INSERT_INTO("tb_task_book");
            sql.VALUES("file_path", "#{filePath}");
            sql.VALUES("audit_status", "#{auditStatus}");

            sql.VALUES("update_time", "now()");
            sql.VALUES("create_time", "now()");

            sql.VALUES("student_id", "#{studentId}");
            sql.VALUES("teacher_id", "#{teacherId}");
            sql.VALUES("projection_id", "#{projectionId}");

            return sql.toString();
        }

        public String updateModel(TaskBookModel model) {
            SQL sql = new SQL();
            sql.UPDATE("tb_task_book");

            sql.SET("audit_status = #{auditStatus}");
            sql.SET("audit_remark = #{auditRemark}");
            sql.SET("update_time = now()");

            sql.WHERE("id = #{id}");
            return sql.toString();
        }

        public String findList(TaskBookModel model) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("tb_task_book");

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
