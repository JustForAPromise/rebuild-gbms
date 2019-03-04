package com.fhx.gdms.user.repository;

import com.fhx.gdms.user.model.UserModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface HelperRepository {

    @Select("SELECT * FROM tb_user where id = #{id}")
    @Results(id = "teacherMap", value = {
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
    UserModel findById(Integer id);

    @Select("SELECT * FROM tb_user WHERE name = #{name} AND password = #{password} AND senate_members = #{senateMembers}")
    @ResultMap(value = "teacherMap")
    UserModel findByNameAndPassword(UserModel model);

    @Select("SELECT * FROM tb_user WHERE no = #{no} AND password = #{password} AND system_administrator = #{ordinaryTeacher}")
    @ResultMap("teacherMap")
    UserModel findByNoAndPassword(UserModel model);

    @InsertProvider(type = TeacherProvider.class, method = "save")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(UserModel model);

    @UpdateProvider(type = TeacherProvider.class, method = "updateModel")
    void update(UserModel model);

    @SelectProvider(type = TeacherProvider.class, method = "findListByModel")
    @ResultMap(value = "teacherMap")
    List<UserModel> findList(UserModel model);

    @Select("SELECT * FROM tb_user WHERE no = #{no}")
    @ResultMap(value = "teacherMap")
    UserModel findByNo(@Param("no") String no);

    @Select("SELECT * FROM tb_user WHERE department_id = #{departmentId}")
    @ResultMap(value = "teacherMap")
    List<UserModel> findByDepartmentId(@Param("departmentId") Integer departmentId);

    @Delete("DELETE FROM tb_user WHERE id = #{id}")
    void deleteById(Integer id);

    /********** 内部类 *********/
    class TeacherProvider {
        public String save(UserModel model) {
            SQL sql = new SQL();
            sql.INSERT_INTO("tb_user");
            if (model.getNo() != null && !"".equals(model.getNo())) {
                sql.VALUES("no", "#{no}");
            }
            if (model.getName() != null && !"".equals(model.getName())) {
                sql.VALUES("name", "#{name}");
            }
            if (model.getGender() != null && !"".equals(model.getGender())) {
                sql.VALUES("gender", "#{gender}");
            }
            if (model.getPhone() != null && !"".equals(model.getPhone())) {
                sql.VALUES("phone", "#{phone}");
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
            sql.VALUES("update_time", "now()");
            sql.VALUES("create_time", "now()");

            return sql.toString();
        }

        public String findListByModel(UserModel model) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("tb_user");
            if (model.getNo() != null && !"".equals(model.getNo())) {
                sql.WHERE("no like #{no}");
            }
            if (model.getName() != null && !"".equals(model.getName())) {
                sql.WHERE("name like #{name}");
            }
            if (model.getGender() != null && !"".equals(model.getGender())) {
                sql.WHERE("gender = #{gender}");
            }
            if (model.getPassword() != null && !"".equals(model.getPassword())) {
                sql.WHERE("password = #{password}");
            }
            if (model.getDepartmentId() != null && !"".equals(model.getDepartmentId())) {
                sql.WHERE("department_id = #{departmentId}");
            }
            return sql.toString();
        }

        public String updateModel(UserModel model) {
            SQL sql = new SQL();
            sql.UPDATE("tb_user");
            if (model.getNo() != null && !"".equals(model.getNo())) {
                sql.SET("no = #{no}");
            }
            if (model.getName() != null && !"".equals(model.getName())) {
                sql.SET("name = #{name}");
            }
            if (model.getGender() != null && !"".equals(model.getGender())) {
                sql.SET("gender = #{gender}");
            }
            if (model.getPhone() != null && !"".equals(model.getPhone())) {
                sql.VALUES("phone", "#{phone}");
            }
            if (model.getPassword() != null && !"".equals(model.getPassword())) {
                sql.SET("password = #{password}");
            }
            if (model.getDepartmentId() != null && !"".equals(model.getDepartmentId())) {
                sql.SET("department_id = #{departmentId}");
            }
            sql.SET("update_time = now()");

            sql.WHERE("id = #{id}");
            return sql.toString();
        }
    }
}
