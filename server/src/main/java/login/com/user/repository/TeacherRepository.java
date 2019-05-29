package login.com.user.repository;

import login.com.user.model.UserModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface TeacherRepository {

    @Select("SELECT * FROM tb_user where id = #{id}")
    @Results(id = "teacherMap", value = {
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
    UserModel findById(Integer id);

    @Select("SELECT * FROM tb_user WHERE no = #{no} AND password = #{password} AND identify = 1")
    @ResultMap(value = "teacherMap")
    UserModel findByNoAndPassword(UserModel model);

    @Select("SELECT * FROM tb_user WHERE name = #{name} AND password = #{password} AND identify = 1")
    @ResultMap(value = "teacherMap")
    UserModel findByNameAndPassword(UserModel model);

    @InsertProvider(type = TeacherProvider.class, method = "save")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(UserModel model);

    @UpdateProvider(type = TeacherProvider.class, method = "updateModel")
    void update(UserModel model);

    @SelectProvider(type = TeacherProvider.class, method = "findListByModel")
    @ResultMap(value = "teacherMap")
    List<UserModel> findList(UserModel model);

    @Select("SELECT * FROM tb_user WHERE no = #{no} AND identify = 1")
    @ResultMap(value = "teacherMap")
    UserModel findByNo(@Param("no") String no);

    @Select("SELECT * FROM tb_user WHERE department_id = #{departmentId} AND identify = 1")
    @ResultMap(value = "teacherMap")
    List<UserModel> findByDepartmentId(@Param("departmentId") Integer departmentId);

    @Delete("DELETE FROM tb_user WHERE id = #{id} AND identify = 1")
    void deleteById(Integer id);

    @SelectProvider(type = TeacherProvider.class, method = "findTotal")
    Integer findTotal(UserModel model);


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

            if (model.getDepartmentId() != null && !"".equals(model.getDepartmentId())) {
                sql.VALUES("department_id", "#{departmentId}");
            }

            sql.VALUES("password", "#{no}");
            sql.VALUES("identify", "1");
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
            sql.WHERE("identify = 1");

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
            sql.WHERE("identify = 1");
            sql.ORDER_BY("update_time DESC");

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
                sql.SET("phone = #{phone}");
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
