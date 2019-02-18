package com.fhx.gdms.admin.repository;

import com.fhx.gdms.admin.model.AdminModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Mapper
@Component
public interface AdminRepository {

    @Select("SELECT * FROM tb_admin WHERE name = #{name} AND password = #{password}")
    @Results(id = "adminMap", value = {
            @Result(column = "id", property = "id", javaType = Integer.class),
            @Result(column = "no", property = "no", javaType = String.class),
            @Result(column = "name", property = "name", javaType = String.class),
            @Result(column = "image", property = "image", javaType = String.class),
            @Result(column = "gender", property = "gender", javaType = Integer.class),
            @Result(column = "password", property = "password", javaType = String.class),
            @Result(column = "create_time", property = "createTime", javaType = Date.class),
            @Result(column = "update_time", property = "updateTime", javaType = Date.class)
    })
    AdminModel findByNameAndPassword(AdminModel model);

    @Select("SELECT * FROM tb_admin WHERE id = #{id}")
    AdminModel findById(@Param("id") Integer id);
}
