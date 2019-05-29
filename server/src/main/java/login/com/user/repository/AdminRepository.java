package login.com.user.repository;

import login.com.user.model.UserModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Mapper
@Component
public interface AdminRepository {

    @Select("SELECT * FROM tb_user WHERE id = #{id} AND identify = 4")
    @Results(id = "adminMap", value = {
            @Result(column = "id", property = "id", javaType = Integer.class),
            @Result(column = "no", property = "no", javaType = String.class),
            @Result(column = "name", property = "name", javaType = String.class),
            @Result(column = "gender", property = "gender", javaType = Integer.class),
            @Result(column = "phone", property = "phone", javaType = String.class),
            @Result(column = "password", property = "password", javaType = String.class),
            @Result(column = "identify", property = "identify", javaType = Integer.class),

            @Result(column = "create_time", property = "createTime", javaType = Date.class),
            @Result(column = "update_time", property = "updateTime", javaType = Date.class),

            @Result(column = "department_id", property = "departmentId", javaType = Integer.class),
            @Result(column = "major_id", property = "majorId", javaType = Integer.class),
            @Result(column = "teacher_id", property = "teacherId", javaType = Integer.class),
            @Result(column = "power_id", property = "powerId", javaType = Integer.class)
    })
    UserModel findById(@Param("id") Integer id);

    @Select("SELECT * FROM tb_user WHERE name = #{name} AND password = #{password} AND identify = 4")
    @ResultMap("adminMap")
    UserModel findByNameAndPassword(UserModel model);

    @Select("SELECT * FROM tb_user WHERE no = #{no} AND password = #{password} AND identify = 4")
    @ResultMap("adminMap")
    UserModel findByNoAndPassword(UserModel model);
}
