package com.fhx.gdms.projections.repository;

import com.fhx.gdms.projections.model.ProjectionModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface ProjectionRepository {

    @InsertProvider(type = ProjectionProvider.class, method = "save")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(ProjectionModel model);

    @UpdateProvider(type = ProjectionProvider.class, method = "updateModel")
    void update(ProjectionModel model);

    @Select("SELECT * FROM tb_projection WHERE id = #{id}")
    @Results(id = "projectionMap", value = {
            @Result(column = "id", property = "id", javaType = Integer.class),
            @Result(column = "title", property = "title", javaType = String.class),
            @Result(column = "introduce", property = "introduce", javaType = String.class),
            @Result(column = "audit_status", property = "auditStatus", javaType = Integer.class),
            @Result(column = "demand", property = "demand", javaType = String.class),

            @Result(column = "create_time", property = "createTime", javaType = Date.class),
            @Result(column = "update_time", property = "updateTime", javaType = Date.class),

            @Result(column = "teacher_id", property = "teacherId", javaType = Integer.class),
            @Result(column = "student_id", property = "studentId", javaType = Integer.class),
            @Result(column = "department_id", property = "departmentId", javaType = Integer.class)
    })
    ProjectionModel findById(@Param("id") Integer id);

    @SelectProvider(type = ProjectionProvider.class, method = "findListByModel")
    @ResultMap(value = "projectionMap")
    List<ProjectionModel> findList(ProjectionModel model);

    @Delete("DELETE FROM tb_projection WHERE id = #{id}")
    void deleteById(Integer id);

    @Select("SELECT * FROM tb_projection WHERE  department_id= #{departmentId} AND student_id is null AND audit_status = 1")
    @ResultMap(value = "projectionMap")
    List<ProjectionModel> listAllProjection(ProjectionModel model);

    @Update("update tb_projection set student_id = #{studentId} where id = #{projectionId}")
    void updateStudentId(@Param("projectionId")Integer projectionId, @Param("studentId")Integer studentId);

    @Select("SELECT * FROM tb_projection WHERE student_id= #{studentId} AND teacher_id = #{teacherId} LIMIT 1")
    @ResultMap(value = "projectionMap")
    ProjectionModel findByUserIdAndTeacherId(@Param("studentId")Integer studentId, @Param("teacherId")Integer teacherId);

    /********** 内部类 *********/

    class ProjectionProvider {
        public String save(ProjectionModel model) {
            SQL sql = new SQL();
            sql.INSERT_INTO("tb_projection");
            if (model.getTitle() != null && !"".equals(model.getTitle())) {
                sql.VALUES("title", "#{title}");
            }
            if (model.getIntroduce() != null && !"".equals(model.getIntroduce())) {
                sql.VALUES("introduce", "#{introduce}");
            }
            if (model.getDemand() != null && !"".equals(model.getDemand())) {
                sql.VALUES("demand", "#{demand}");
            }

            sql.VALUES("audit_status", "0");
            sql.VALUES("teacher_id", "#{teacherId}");
            sql.VALUES("department_id", "#{departmentId}");
            sql.VALUES("update_time", "now()");
            sql.VALUES("create_time", "now()");
            return sql.toString();
        }

        public String findListByModel(ProjectionModel model) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("tb_projection");
            if (model.getTitle() != null && !"".equals(model.getTitle())) {
                sql.WHERE("title like #{title}");
            }
            if (model.getAuditStatus() != null && !"".equals(model.getAuditStatus())) {
                sql.WHERE("audit_status = #{auditStatus}");
            }
            if (model.getTeacherId() != null && !"".equals(model.getTeacherId())) {
                sql.WHERE("teacher_id = #{teacherId}");
            }
            if (model.getDepartmentId() != null && !"".equals(model.getDepartmentId())) {
                sql.WHERE("department_id = #{departmentId}");
            }
            if (model.getProjectionIdIn() != null && model.getProjectionIdIn().size() > 0) {
                StringBuffer inSQl = new StringBuffer("id in(");
                for (int i = 0, j = model.getProjectionIdIn().size(); i < j; i++){
                    inSQl.append(model.getProjectionIdIn().get(i));
                    if (i == j-1){
                        inSQl.append(")");
                    }else{
                        inSQl.append(",");
                    }
                }
                sql.WHERE(inSQl.toString());
            }
            if (model.getProjectionIdNotIn() != null && model.getProjectionIdNotIn().size() > 0) {
                StringBuffer inSQl = new StringBuffer("id not in(");
                for (int i = 0, j = model.getProjectionIdNotIn().size(); i < j; i++){
                    inSQl.append(model.getProjectionIdNotIn().get(i));
                    if (i == j-1){
                        inSQl.append(")");
                    }else{
                        inSQl.append(",");
                    }
                }
                sql.WHERE(inSQl.toString());
                sql.WHERE("student_id is NULL");
            }

            return sql.toString();
        }

        public String updateModel(ProjectionModel model) {
            SQL sql = new SQL();
            sql.UPDATE("tb_projection");
            if (model.getTitle() != null && !"".equals(model.getTitle())) {
                sql.SET("title = #{title}");
            }
            if (model.getIntroduce() != null && !"".equals(model.getIntroduce())) {
                sql.SET("introduce = #{introduce}");
            }
            if (model.getDemand() != null && !"".equals(model.getDemand())) {
                sql.SET("demand = #{demand}");
            }
            if (model.getStudentId() != null && !"".equals(model.getStudentId())) {
                sql.SET("student_id = #{studentId}");
            }

            sql.SET("audit_status = 0");
            sql.SET("update_time = now()");
            sql.WHERE("id = #{id}");
            return sql.toString();
        }
    }
}
