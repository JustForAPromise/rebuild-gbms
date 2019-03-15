package com.fhx.gdms.user.repository;


import com.fhx.gdms.user.model.UserModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.Date;

@Mapper
@Component
public interface UserRepository {

    @InsertProvider(type = UserModelProvider.class, method = "save")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(UserModel model);

    @Select("SELECT * FROM tb_user WHERE id = #{id}")
    @Results(id = "userMap", value = {
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

    @Select("SELECT * FROM tb_user WHERE no = #{no} AND department_id = #{departmentId}")
    @ResultMap(value = "userMap")
    UserModel findByNoAndDepartmentId(UserModel model);


    @SelectProvider(type = UserModelProvider.class, method = "findOne")
    @ResultMap(value = "userMap")
    UserModel findOne(UserModel model);

    @Update("update tb_user set power_id = #{powerId} where id = #{id}")
    void updatePowerById(@Param("id") Integer id, @Param("powerId") Integer powerId);

    @Update("update tb_user set password = #{password} where id = #{id}")
    void updatePwd(@Param("id") Integer id, @Param("password") String password);

    /********** 内部类 *********/
    class UserModelProvider {
        public String save(UserModel model) {
            SQL sql = new SQL();
            sql.INSERT_INTO("tb_user");
            if (model.getNo() != null && !"".equals(model.getNo())) {
                sql.VALUES("no", "#{no}");
            }
            if (model.getName() != null && !"".equals(model.getName())) {
                sql.VALUES("name", "#{name}");
            }
            if (model.getIntroduce() != null && !"".equals(model.getIntroduce())) {
                sql.VALUES("introduce", "#{introduce}");
            }
            if (model.getPassword() != null && !"".equals(model.getPassword())) {
                sql.VALUES("password", "#{password}");
            } else {
                sql.VALUES("password", "#{no}");
            }
            if (model.getDepartmentId() != null && !"".equals(model.getDepartmentId())) {
                sql.VALUES("department_id", "#{departmentId}");
            }
            if (model.getTeacherId() != null && !"".equals(model.getTeacherId())) {
                sql.VALUES("teacher_id", "#{teacherId}");
            }
            if (model.getIdentify() != null && !"".equals(model.getIdentify())) {
                sql.VALUES("identify", "#{identify}");
            }
            sql.VALUES("update_time", "now()");
            sql.VALUES("create_time", "now()");

            return sql.toString();
        }

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
