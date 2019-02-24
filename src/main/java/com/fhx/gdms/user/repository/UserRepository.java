package com.fhx.gdms.user.repository;


import com.fhx.gdms.user.model.UserModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.Date;

@Mapper
@Component
public interface UserRepository {

    @Select("SELECT * FROM tb_user WHERE no = #{no} AND department_id = #{departmentId}")
    @Results(id = "userMap", value = {
            @Result(column = "id", property = "id", javaType = Integer.class),
            @Result(column = "no", property = "no", javaType = String.class),
            @Result(column = "name", property = "name", javaType = String.class),
            @Result(column = "gender", property = "gender", javaType = Integer.class),
            @Result(column = "phone", property = "phone", javaType = String.class),
            @Result(column = "password", property = "password", javaType = String.class),
            @Result(column = "identify", property = "identify", javaType = Integer.class),

            @Result(column = "ordinary_teacher", property = "ordinaryTeacher", javaType = Boolean.class),
            @Result(column = "ordinary_student", property = "ordinaryStudent", javaType = Boolean.class),
            @Result(column = "senate_members", property = "senateMembers", javaType = Boolean.class),
            @Result(column = "system_administrator", property = "systemAdministrator", javaType = Boolean.class),

            @Result(column = "create_time", property = "createTime", javaType = Date.class),
            @Result(column = "update_time", property = "updateTime", javaType = Date.class),

            @Result(column = "department_id", property = "departmentId", javaType = Integer.class),
            @Result(column = "major_id", property = "majorId", javaType = Integer.class),
            @Result(column = "teacher_id", property = "teacherId", javaType = Integer.class),
            @Result(column = "power_id", property = "powerId", javaType = Integer.class)
    })
    UserModel findByNoAndDepartmentId(UserModel model);


    @Select("SELECT * FROM tb_user WHERE id = #{id}")
    @ResultMap(value = "userMap")
    UserModel findById(@Param("id") Integer id);

    @SelectProvider(type = UserProvider.class, method = "findOne")
    @ResultMap(value = "userMap")
    UserModel findOne(UserModel model);

    /********** 内部类 *********/
    class UserProvider {
        public String findOne(UserModel model) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("tb_user");
            if (model.getNo() != null && !"".equals(model.getNo())) {
                sql.WHERE("no = #{no}");
            }
            if (model.getIdentify() != null && !"".equals(model.getIdentify())) {
                sql.WHERE("identify = #{identify}");
            }
            if (model.getDepartmentId() != null && !"".equals(model.getDepartmentId())) {
                sql.WHERE("department_id = #{departmentId}");
            }
            return sql.toString();
        }
    }
}
