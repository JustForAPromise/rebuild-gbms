package com.fhx.gdms.service.studentScore.itemScore.repository;

import login.com.studentScore.itemScore.model.StudentItemScoreModel;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface StudentItemScoreRepository {

    @InsertProvider(type = StudentScoreRecordProvider.class, method = "save")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(StudentItemScoreModel model);

    @UpdateProvider(type = StudentScoreRecordProvider.class, method = "updateModel")
    void update(StudentItemScoreModel model);

    @Select("SELECT * FROM tb_student_item_score WHERE id = #{id}")
    @Results(id = "scoreRecordMap", value = {
            @Result(column = "id", property = "id", javaType = Integer.class),
            @Result(column = "score_num", property = "scoreNum", javaType = Integer.class),

            @Result(column = "create_time", property = "createTime", javaType = Date.class),
            @Result(column = "update_time", property = "updateTime", javaType = Date.class),

            @Result(column = "student_id", property = "studentId", javaType = Integer.class),
            @Result(column = "score_item_id", property = "scoreItemId", javaType = Integer.class),
    })
    StudentItemScoreModel findById(@Param("id") Integer id);

    @Select("SELECT * FROM tb_student_item_score WHERE student_id = #{studentId}")
    @ResultMap(value = "scoreRecordMap")
    List<StudentItemScoreModel> findByStudentId(@Param("studentId") Integer studentId);

    @Select("SELECT * FROM tb_student_item_score WHERE  student_id= #{studentId} AND score_item_id = #{scoreItemId} LIMIT 1")
    @ResultMap(value = "scoreRecordMap")
    StudentItemScoreModel findByStudentIdAndItemId(@Param("studentId") Integer studentId, @Param("scoreItemId") Integer scoreItemId);

    @SelectProvider(type = StudentScoreRecordProvider.class, method = "findOne")
    @ResultMap(value = "scoreRecordMap")
    StudentItemScoreModel findOne(StudentItemScoreModel itemScoreModel);

    /********** 内部类 *********/

    class StudentScoreRecordProvider {
        public String save(StudentItemScoreModel model) {
            SQL sql = new SQL();
            sql.INSERT_INTO("tb_student_item_score");
            sql.VALUES("score_num", "#{scoreNum}");

            sql.VALUES("update_time", "now()");
            sql.VALUES("create_time", "now()");

            sql.VALUES("student_id", "#{studentId}");
            sql.VALUES("score_item_id", "#{scoreItemId}");
            return sql.toString();
        }

        public String findOne(StudentItemScoreModel model) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("tb_student_item_score");
            if (model.getId() != null && !"".equals(model.getId())) {
                sql.WHERE("id = #{id}");
            }
            if (model.getStudentId() != null && !"".equals(model.getStudentId())) {
                sql.WHERE("student_id = #{studentId}");
            }
            if (model.getScoreItemId() != null && !"".equals(model.getScoreItemId())) {
                sql.WHERE("score_item_id = #{scoreItemId}");
            }

            return sql.toString()+"LIMIT 1";
        }

        public String updateModel(StudentItemScoreModel model) {
            SQL sql = new SQL();
            sql.UPDATE("tb_student_item_score");

            if (model.getScoreNum() != null && !"".equals(model.getScoreNum())) {
                sql.SET("score_num = #{scoreNum}");
            }

            sql.WHERE("id = #{id}");
            return sql.toString();
        }
    }
}