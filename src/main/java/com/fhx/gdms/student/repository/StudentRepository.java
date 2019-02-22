package com.fhx.gdms.student.repository;

import com.fhx.gdms.student.model.StudentModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface StudentRepository {

    @Select("SELECT * FROM tb_student WHERE name = #{name} AND password = #{password}")
    @Results(id = "studentMap", value = {
            @Result(column = "id", property = "id", javaType = Integer.class),
            @Result(column = "no", property = "no", javaType = String.class),
            @Result(column = "name", property = "name", javaType = String.class),
            @Result(column = "gender", property = "gender", javaType = Integer.class),
            @Result(column = "password", property = "password", javaType = String.class),
            @Result(column = "create_time", property = "createTime", javaType = Date.class),
            @Result(column = "update_time", property = "updateTime", javaType = Date.class),
            @Result(column = "department_id", property = "departmentId", javaType = Integer.class),
            @Result(column = "major_id", property = "majorId", javaType = Integer.class),
            @Result(column = "teacher_id", property = "teacherId", javaType = Integer.class)
    })
    StudentModel findByNameAndPassword(StudentModel model);

    @Select("SELECT * FROM tb_student WHERE no = #{no}")
    @ResultMap(value = "studentMap")
    StudentModel findByNo(@Param("no") String no);

    @Select("SELECT * FROM tb_student WHERE id = #{id}")
    @ResultMap(value = "studentMap")
    StudentModel findById(@Param("id")Integer id);

    @InsertProvider(type = StudentProvider.class, method = "save")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(StudentModel model);

    @Select("SELECT * FROM tb_student WHERE department_id = #{departmentId} AND major_id = #{majorId}")
    @ResultMap(value = "studentMap")
    List<StudentModel> findByMajorIdAndDepartmentId(@Param("departmentId")Integer departmentId, @Param("majorId")Integer majorId);

    class StudentProvider {
        public String save(StudentModel model) {
            SQL sql =  new SQL();
            sql.INSERT_INTO("tb_student");
            if (model.getNo() != null && !"".equals(model.getNo())){
                sql.VALUES("no",  "#{no}");
            }
            if (model.getName() != null && !"".equals(model.getName())){
                sql.VALUES("name",  "#{name}");
            }
            if (model.getGender() != null && !"".equals(model.getGender())){
                sql.VALUES("gender",  "#{gender}");
            }
            if (model.getImage() != null && !"".equals(model.getImage())){
                sql.VALUES("image", "#{image}");
            }
            if (model.getIdentify() != null && !"".equals(model.getIdentify())){
                sql.VALUES("identify", "#{identify}");
            }
            if (model.getIntroduce() != null && !"".equals(model.getIntroduce())){
                sql.VALUES("introduce", "#{introduce}");
            }
            if (model.getRemark() != null && !"".equals(model.getRemark())){
                sql.VALUES("remark", "#{remark}");
            }
            if (model.getPassword() != null && !"".equals(model.getPassword())){
                sql.VALUES("password", "#{password}");
            }else{
                sql.VALUES("password", "#{no}");
            }
            if (model.getDepartmentId() != null && !"".equals(model.getDepartmentId())){
                sql.VALUES("department_id", "#{departmentId}");
            }
            if (model.getMajorId() != null && !"".equals(model.getMajorId())){
                sql.VALUES("major_id", "#{majorId}");
            }
            if (model.getTeacherId() != null && !"".equals(model.getTeacherId())){
                sql.VALUES("teacher_id", "#{teacherId}");
            }
            sql.VALUES("update_time", "now()");
            sql.VALUES("create_time", "now()");

            return sql.toString();
        }

        public String findListByModel(StudentModel model) {
            SQL sql =  new SQL();
            sql.SELECT("*");
            sql.FROM("tb_student");
            if (model.getNo() != null && !"".equals(model.getNo())){
                sql.WHERE("no = #{no}");
            }
            if (model.getName() != null && !"".equals(model.getName())){
                sql. WHERE("name like #{name}");
            }
            if (model.getGender() != null && !"".equals(model.getGender())){
                sql. WHERE("gender = #{gender}");
            }
            if (model.getIdentify() != null && !"".equals(model.getIdentify())){
                sql. WHERE("identify like #{identify}");
            }
            if (model.getPassword() != null && !"".equals(model.getPassword())){
                sql. WHERE("password = #{password}");
            }
            if (model.getDepartmentId() != null && !"".equals(model.getDepartmentId())){
                sql. WHERE("department_id = #{departmentId}");
            }
            if (model.getMajorId() != null && !"".equals(model.getMajorId())){
                sql. WHERE("major_id = #{majorId}");
            }
            if (model.getTeacherId() != null && !"".equals(model.getTeacherId())){
                sql. WHERE("teacher_id = #{teacherId}");
            }
            return sql.toString();
        }
    }

}
