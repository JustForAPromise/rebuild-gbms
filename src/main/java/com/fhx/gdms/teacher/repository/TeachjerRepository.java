package com.fhx.gdms.teacher.repository;

import com.fhx.gdms.admin.model.AdminModel;
import com.fhx.gdms.teacher.model.TeacherModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface TeachjerRepository {

    @Select("SELECT * FROM tb_teacher WHERE name = #{name} AND password = #{password}")
    @Results(id = "teacherMap", value = {
            @Result(column = "id", property = "id", javaType = Integer.class),
            @Result(column = "no", property = "no", javaType = String.class),
            @Result(column = "name", property = "name", javaType = String.class),
            @Result(column = "gender", property = "gender", javaType = Integer.class),
            @Result(column = "password", property = "password", javaType = String.class),
            @Result(column = "create_time", property = "createTime", javaType = Date.class),
            @Result(column = "update_time", property = "updateTime", javaType = Date.class)
    })
    TeacherModel findByNameAndPassword(TeacherModel model);


//    @Insert("INSERT INTO tb_department(id, department, create_time, update_time) VALUES(#{id}, #{department}, #{createTime}, #{updateTime});")
//    @Options(useGeneratedKeys = true, keyProperty = "id")
//    int save(DepartmentModel model);
//
//    @Select("SELECT * FROM tb_department where id = #{id}")
//    @Results(id = "departmentMap", value = {
//            @Result(column = "id", property = "id", javaType = Integer.class),
//            @Result(column = "department", property = "department", javaType = String.class),
//            @Result(column = "create_time", property = "createTime", javaType = Date.class),
//            @Result(column = "update_time", property = "updateTime", javaType = Date.class)
//    })
//    DepartmentModel findById(DepartmentModel model);
//
//    @Select("SELECT * FROM tb_department")
//    @ResultMap(value = "departmentMap")
//    List<DepartmentModel> findAll();
}
