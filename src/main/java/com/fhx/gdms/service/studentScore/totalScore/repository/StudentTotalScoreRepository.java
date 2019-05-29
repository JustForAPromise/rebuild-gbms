package com.fhx.gdms.service.studentScore.totalScore.repository;

import login.com.studentScore.totalScore.model.StudentTotalScoreModel;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface StudentTotalScoreRepository {

    @InsertProvider(type = ScoreItemProvider.class, method = "save")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(StudentTotalScoreModel model);

    @Select("SELECT * FROM tb_student_total_score WHERE id = #{id}")
    @Results(id = "scoreTotalMap", value = {
            @Result(column = "id", property = "id", javaType = Integer.class),
            @Result(column = "score_num", property = "scoreNum", javaType = BigDecimal.class),
            @Result(column = "level", property = "level", javaType = String.class),
            @Result(column = "status", property = "status", javaType = String.class),

            @Result(column = "create_time", property = "createTime", javaType = Date.class),
            @Result(column = "update_time", property = "updateTime", javaType = Date.class),

            @Result(column = "student_id", property = "studentId", javaType = Integer.class),
            @Result(column = "department_id", property = "departmentId", javaType = Integer.class),
            @Result(column = "major_id", property = "majorId", javaType = Integer.class)
    })
    StudentTotalScoreModel findById(@Param("id") Integer id);

    @SelectProvider(type = ScoreItemProvider.class, method = "findByModel")
    @ResultMap(value = "scoreTotalMap")
    StudentTotalScoreModel findOne(StudentTotalScoreModel model);

    @UpdateProvider(type = ScoreItemProvider.class, method = "updateModel")
    void updateModel(StudentTotalScoreModel model);

    @Select("SELECT * FROM tb_student_total_score WHERE student_id = #{studentId} LIMIT 1")
    @ResultMap(value = "scoreTotalMap")
    StudentTotalScoreModel findByStudentId(@Param("studentId") Integer studentId);

    @SelectProvider(type = ScoreItemProvider.class, method = "findByModel")
    @ResultMap(value = "scoreTotalMap")
    List<StudentTotalScoreModel> listByModel(StudentTotalScoreModel studentTotalScoreModel);


    /********** 内部类 *********/
    class ScoreItemProvider {
        public String save(StudentTotalScoreModel model) {
            SQL sql = new SQL();
            sql.INSERT_INTO("tb_student_total_score");
            sql.VALUES("score_num", "#{scoreNum}");
            sql.VALUES("level", "#{level}");
            sql.VALUES("status", "#{status}");

            sql.VALUES("update_time", "now()");
            sql.VALUES("create_time", "now()");

            sql.VALUES("student_id", "#{studentId}");
            sql.VALUES("department_id", "#{departmentId}");
            sql.VALUES("major_id", "#{majorId}");
            return sql.toString();
        }

        public String findByModel(StudentTotalScoreModel model) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("tb_student_total_score");
            if (model.getStudentId() != null && !"".equals(model.getStudentId())) {
                sql.WHERE("student_id = #{studentId}");
            }
            if (model.getDepartmentId() != null && !"".equals(model.getDepartmentId())) {
                sql.WHERE("department_id = #{departmentId}");
            }
            if (model.getMajorId() != null && !"".equals(model.getMajorId())) {
                sql.WHERE("major_id = #{majorId}");
            }

            if (model.getStudentIds() != null && model.getStudentIds().size() > 0) {
                StringBuffer inSQl = new StringBuffer("student_id in(");
                for (int i = 0, j = model.getStudentIds().size(); i < j; i++){
                    inSQl.append(model.getStudentIds().get(i));
                    if (i == j-1){
                        inSQl.append(")");
                    }else{
                        inSQl.append(",");
                    }
                }
                sql.WHERE(inSQl.toString());
            }

            sql.ORDER_BY("score_num DESC");
            return sql.toString();
        }

        public String updateModel(StudentTotalScoreModel model) {
            SQL sql = new SQL();
            sql.UPDATE("tb_student_total_score");
            if (model.getScoreNum() != null && !"".equals(model.getScoreNum())) {
                sql.SET("score_num = #{scoreNum}");
            }
            if (model.getLevel() != null && !"".equals(model.getLevel())) {
                sql.SET("level = #{level}");
            }
            if (model.getStatus() != null && !"".equals(model.getStatus())) {
                sql.SET("status = #{status}");
            }

            sql.WHERE("id = #{id}");
            return sql.toString();
        }
    }
}
