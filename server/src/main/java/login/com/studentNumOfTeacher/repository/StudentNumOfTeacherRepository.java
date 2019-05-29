package login.com.studentNumOfTeacher.repository;

import login.com.studentNumOfTeacher.model.StudentNumOfTeacherModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface StudentNumOfTeacherRepository {

    @InsertProvider(type = StudentNumProvider.class, method = "save")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(StudentNumOfTeacherModel model);

    @Select("SELECT * FROM tb_teacher_student_num WHERE id = #{id}")
    @Results(id = "studentNumMap", value = {
            @Result(column = "id", property = "id", javaType = Integer.class),
            @Result(column = "student_num", property = "studentNum", javaType = Integer.class),

            @Result(column = "create_time", property = "createTime", javaType = Date.class),
            @Result(column = "update_time", property = "updateTime", javaType = Date.class),

            @Result(column = "teacher_id", property = "teacherId", javaType = Integer.class)
    })
    StudentNumOfTeacherModel findById(@Param("id") Integer id);

    @Select("SELECT * FROM tb_teacher_student_num WHERE  teacher_id = #{teacherId} LIMIT 1")
    @ResultMap(value = "studentNumMap")
    StudentNumOfTeacherModel findByTeacherId(@Param("teacherId") Integer teacherId);

    @Select("SELECT * FROM tb_teacher_student_num ORDER BY create_time DESC")
    @ResultMap(value = "studentNumMap")
    List<StudentNumOfTeacherModel> listAll();

    @Update("UPDATE tb_teacher_student_num SET student_num = #{studentNum} WHERE id = #{id}")
    @ResultMap(value = "studentNumMap")
    void update(StudentNumOfTeacherModel model);


    /********** 内部类 *********/
    class StudentNumProvider {
        public String save(StudentNumOfTeacherModel model) {
            SQL sql = new SQL();
            sql.INSERT_INTO("tb_teacher_student_num");
            sql.VALUES("student_num", "10");

            sql.VALUES("update_time", "now()");
            sql.VALUES("create_time", "now()");

            sql.VALUES("teacher_id", "#{teacherId}");
            return sql.toString();
        }
    }
}
