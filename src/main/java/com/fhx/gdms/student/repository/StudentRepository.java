package com.fhx.gdms.student.repository;

import com.fhx.gdms.student.model.StudentModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Date;

@Mapper
@Component
public interface StudentRepository {

    @Select("SELECT * FROM tb_student WHERE name = #{name} AND password = #{password}")
    @Results(id = "adminMap", value = {
            @Result(column = "id", property = "id", javaType = Integer.class),
            @Result(column = "no", property = "no", javaType = String.class),
            @Result(column = "name", property = "name", javaType = String.class),
            @Result(column = "gender", property = "gender", javaType = Integer.class),
            @Result(column = "password", property = "password", javaType = String.class),
            @Result(column = "create_time", property = "createTime", javaType = Date.class),
            @Result(column = "update_time", property = "updateTime", javaType = Date.class)
    })
    StudentModel findByNameAndPassword(StudentModel model);
}
