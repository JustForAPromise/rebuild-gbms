package com.fhx.gdms.studentScoreRecord.repository;

import com.fhx.gdms.studentScoreRecord.model.StudentScoreRecordModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface StudentScoreRecordRepository {

    @InsertProvider(type = StudentScoreRecordProvider.class, method = "save")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(StudentScoreRecordModel model);

    @InsertProvider(type = StudentScoreRecordProvider.class, method = "updateModel")
    void update(StudentScoreRecordModel model);

    @Select("SELECT * FROM tb_student_score_record WHERE id = #{id}")
    @Results(id = "scoreRecordMap", value = {
            @Result(column = "id", property = "id", javaType = Integer.class),
            @Result(column = "score_num", property = "scoreNum", javaType = Integer.class),

            @Result(column = "create_time", property = "createTime", javaType = Date.class),
            @Result(column = "update_time", property = "updateTime", javaType = Date.class),

            @Result(column = "student_id", property = "studentId", javaType = Integer.class),
            @Result(column = "score_item_id", property = "scoreItemId", javaType = Integer.class),
    })
    StudentScoreRecordModel findById(@Param("id") Integer id);

    @Select("SELECT * FROM tb_student_score_record WHERE student_id = #{studentId}")
    @ResultMap(value = "scoreRecordMap")
    List<StudentScoreRecordModel> findByStudentId(@Param("studentId") Integer studentId);

    @Select("SELECT * FROM tb_student_score_record WHERE  student_id= #{studentId} AND score_item_id = #{scoreItemId} LIMIT 1")
    @ResultMap(value = "scoreRecordMap")
    StudentScoreRecordModel findByStudentIdAndItemId(@Param("studentId") Integer studentId, @Param("scoreItemId") Integer scoreItemId);

    /********** 内部类 *********/

    class StudentScoreRecordProvider {
        public String save(StudentScoreRecordModel model) {
            SQL sql = new SQL();
            sql.INSERT_INTO("tb_student_score_record");
            sql.VALUES("score_num", "#{scoreNum}");

            sql.VALUES("update_time", "now()");
            sql.VALUES("create_time", "now()");

            sql.VALUES("student_id", "#{studentId}");
            sql.VALUES("score_item_id", "#{scoreItemId}");
            return sql.toString();
        }

        public String updateModel(StudentScoreRecordModel model) {
            SQL sql = new SQL();
            sql.UPDATE("tb_student_score_record");

            if (model.getScoreNum() != null && !"".equals(model.getScoreNum())) {
                sql.SET("score_num = #{scoreNum}");
            }

            sql.WHERE("id = #{id}");
            return sql.toString();
        }
    }
}