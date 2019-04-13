package com.fhx.gdms.service.user.repository;

import com.fhx.gdms.service.user.model.UserModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface StudentRepository {

    @InsertProvider(type = StudentProvider.class, method = "save")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(UserModel model);

    @UpdateProvider(type = StudentProvider.class, method = "updateModel")
    void update(UserModel model);

    @Select("SELECT * FROM tb_user WHERE id = #{id} AND identify = 2")
    @Results(id = "studentMap", value = {
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

    @Select("SELECT * FROM tb_user WHERE no = #{no} AND password = #{password} AND identify = 2")
    @ResultMap(value = "studentMap")
    UserModel findByNoAndPassword(UserModel model);

    @Select("SELECT * FROM tb_user WHERE name = #{name} AND password = #{password} AND identify = 2")
    @ResultMap(value = "studentMap")
    UserModel findByNameAndPassword(UserModel model);

    @SelectProvider(type = StudentProvider.class, method = "findListByModel")
    @ResultMap(value = "studentMap")
    List<UserModel> findList(UserModel model);

    @Select("SELECT * FROM tb_user WHERE no = #{no}")
    @ResultMap(value = "studentMap")
    UserModel findByNo(@Param("no") String no);

    @Select("SELECT * FROM tb_user WHERE department_id = #{departmentId} AND major_id = #{majorId} AND identify = 2")
    @ResultMap(value = "studentMap")
    List<UserModel> findByMajorIdAndDepartmentId(@Param("departmentId") Integer departmentId, @Param("majorId") Integer majorId);

    @Delete("DELETE FROM tb_user WHERE id = #{id} AND identify = 2")
    void deleteById(Integer id);

    @Update("update tb_user set teacher_id = #{teacherId} where id = #{studentId} AND identify = 2")
    void updateTeacherId(@Param("studentId") Integer studentId, @Param("teacherId") Integer teacherId);

    @Select("SELECT id FROM tb_user WHERE no like #{no} AND name like #{name} AND teacher_id = #{teacherId} AND identify = 2")
    List<Integer> listStudentId(UserModel student);

    @Select("SELECT * FROM tb_user WHERE teacher_id = #{teacherId} AND identify = 2")
    @ResultMap(value = "studentMap")
    List<UserModel> findByTeacherId(@Param("teacherId") Integer teacherId);

    @SelectProvider(type = StudentProvider.class, method = "findOne")
    UserModel findOne(UserModel student);

    @SelectProvider(type = StudentProvider.class, method = "findTotal")
    Integer findTotal(UserModel model);


    /********** 内部类 *********/

    class StudentProvider {
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

            if (model.getPassword() != null && !"".equals(model.getPassword())) {
                sql.VALUES("password", "#{password}");
            } else {
                sql.VALUES("password", "#{no}");
            }
            if (model.getPhone() != null && !"".equals(model.getPhone())) {
                sql.VALUES("phone", "#{phone}");
            }
            if (model.getDepartmentId() != null && !"".equals(model.getDepartmentId())) {
                sql.VALUES("department_id", "#{departmentId}");
            }
            if (model.getMajorId() != null && !"".equals(model.getMajorId())) {
                sql.VALUES("major_id", "#{majorId}");
            }
            if (model.getTeacherId() != null && !"".equals(model.getTeacherId())) {
                sql.VALUES("teacher_id", "#{teacherId}");
            }
            sql.VALUES("identify", "2");
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
            if (model.getMajorId() != null && !"".equals(model.getMajorId())) {
                sql.WHERE("major_id = #{majorId}");
            }
            if (model.getTeacherId() != null && !"".equals(model.getTeacherId())) {
                sql.WHERE("teacher_id = #{teacherId}");
            }
            sql.WHERE("identify = 2");
            sql.ORDER_BY("no DESC");


            StringBuffer end = new StringBuffer();
            if (model.getPage() != null && !"".equals(model.getPage())) {
                if (model.getSize() != null && !"".equals(model.getSize()))
                    end.append(" LIMIT " + model.getPage()*model.getSize()+","+model.getSize());
            }
            return sql.toString()+end.toString();
        }

        public String findTotal(UserModel model) {
            SQL sql = new SQL();
            sql.SELECT("count(1)");
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
            if (model.getMajorId() != null && !"".equals(model.getMajorId())) {
                sql.WHERE("major_id = #{majorId}");
            }
            if (model.getTeacherId() != null && !"".equals(model.getTeacherId())) {
                sql.WHERE("teacher_id = #{teacherId}");
            }
            sql.WHERE("identify = 2");
            sql.ORDER_BY("update_time DESC");

            return sql.toString();
        }

        public String findOne(UserModel model) {
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
            if (model.getMajorId() != null && !"".equals(model.getMajorId())) {
                sql.WHERE("major_id = #{majorId}");
            }
            if (model.getTeacherId() != null && !"".equals(model.getTeacherId())) {
                sql.WHERE("teacher_id = #{teacherId}");
            }
            sql.WHERE("identify = 2");

            return sql.toString() + " LIMIT 1";
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
            if (model.getPassword() != null && !"".equals(model.getPassword())) {
                sql.SET("password = #{password}");
            }
            if (model.getDepartmentId() != null && !"".equals(model.getDepartmentId())) {
                sql.SET("department_id = #{departmentId}");
            }
            if (model.getMajorId() != null && !"".equals(model.getMajorId())) {
                sql.SET("major_id = #{majorId}");
            }
            if (model.getTeacherId() != null && !"".equals(model.getTeacherId())) {
                sql.SET("teacher_id = #{teacherId}");
            }
            sql.SET("update_time = now()");

            sql.WHERE("id = #{id}");
            return sql.toString();
        }
    }
}
