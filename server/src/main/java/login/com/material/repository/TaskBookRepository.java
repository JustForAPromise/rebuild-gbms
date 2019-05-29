package login.com.material.repository;

import login.com.material.model.MaterialModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface TaskBookRepository {

    @Select("SELECT * FROM tb_material where id = #{id} AND file_type = 1")
    @Results(id = "taskBookMap", value = {
            @Result(column = "id", property = "id", javaType = Integer.class),
            @Result(column = "file_type", property = "fileType", javaType = Integer.class),
            @Result(column = "file_path", property = "filePath", javaType = String.class),
            @Result(column = "audit_status", property = "auditStatus", javaType = Integer.class),
            @Result(column = "audit_remark", property = "auditRemark", javaType = String.class),

            @Result(column = "create_time", property = "createTime", javaType = Date.class),
            @Result(column = "update_time", property = "updateTime", javaType = Date.class),

            @Result(column = "student_id", property = "studentId", javaType = Integer.class),
            @Result(column = "teacher_id", property = "teacherId", javaType = Integer.class),
            @Result(column = "projection_id", property = "projectionId", javaType = Integer.class)
    })
    MaterialModel findById(Integer id);

    @InsertProvider(type = TaskBookProvider.class, method = "save")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(MaterialModel model);

    @SelectProvider(type = TaskBookProvider.class, method = "findList")
    @ResultMap("taskBookMap")
    List<MaterialModel> findList(MaterialModel model);

    @Select("SELECT * FROM tb_material " +
            "where student_id = #{studentId} AND  teacher_id = #{teacherId} " +
            "AND projection_id = #{projectionId} AND audit_status = #{auditStatus} " +
            "AND file_type = 1 " +
            "LIMIT 1")
    @ResultMap("taskBookMap")
    MaterialModel findOne(MaterialModel taskBookModel);

    @Delete("DELETE FROM tb_material WHERE id = #{id} AND file_type = 1")
    void deleteById(@Param("id") Integer id);

    @UpdateProvider(type = TaskBookProvider.class, method = "updateModel")
    void update(MaterialModel model);

    /********** 内部类 *********/
    class TaskBookProvider {
        public String save(MaterialModel model) {
            SQL sql = new SQL();
            sql.INSERT_INTO("tb_material");
            sql.VALUES("file_type", "1");
            sql.VALUES("file_path", "#{filePath}");
            sql.VALUES("audit_status", "#{auditStatus}");

            sql.VALUES("update_time", "now()");
            sql.VALUES("create_time", "now()");

            sql.VALUES("student_id", "#{studentId}");
            sql.VALUES("teacher_id", "#{teacherId}");
            sql.VALUES("projection_id", "#{projectionId}");

            return sql.toString();
        }

        public String updateModel(MaterialModel model) {
            SQL sql = new SQL();
            sql.UPDATE("tb_material");

            sql.SET("audit_status = #{auditStatus}");
            sql.SET("audit_remark = #{auditRemark}");
            sql.SET("update_time = now()");

            sql.WHERE("id = #{id}");
            sql.WHERE("file_type = 1");
            return sql.toString();
        }

        public String findList(MaterialModel model) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("tb_material ");

            if (model.getStudentId() != null) {
                sql.WHERE("student_id = #{studentId}");
            }
            if (model.getTeacherId() != null) {
                sql.WHERE("teacher_id = #{teacherId}");
            }
            if (model.getProjectionId() != null) {
                sql.WHERE("projection_id = #{projectionId}");
            }
            if (model.getStudentIds() != null) {
                if (model.getStudentIds().size() > 0) {
                    StringBuffer inSQl = new StringBuffer("student_id in(");
                    for (int i = 0, j = model.getStudentIds().size(); i < j; i++) {
                        inSQl.append(model.getStudentIds().get(i));
                        if (i == j - 1) {
                            inSQl.append(")");
                        } else {
                            inSQl.append(",");
                        }
                    }
                    sql.WHERE(inSQl.toString());
                }
            }

            sql.WHERE("file_type = 1");
            sql.ORDER_BY("audit_status","update_time DESC");
            return sql.toString();
        }
    }
}
