package com.fhx.gdms.power.repository;

import com.fhx.gdms.power.model.PowerModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
@Mapper
@Component
public interface PowerRepository {

    @Insert("INSERT INTO tb_power(user_id, department_id, create_time, update_time) VALUES(#{userId}, #{departmentId}, now(), now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(PowerModel model);

    @UpdateProvider(type = PowerProvider.class, method = "updateModel")
    void update(PowerModel model);

    @Select("SELECT * FROM tb_power WHERE id = #{id}")
    @Results(id = "powerMap", value = {
            @Result(column = "id", property = "id", javaType = Integer.class),
            @Result(column = "department_leader", property = "departmentLeader", javaType = Boolean.class),
            @Result(column = "senate_members", property = "senateMembers", javaType = Boolean.class),
            @Result(column = "project_auditor", property = "projectAuditor", javaType = Boolean.class),
            @Result(column = "answer_auditor", property = "answerAuditor", javaType = Boolean.class),
            @Result(column = "system_administrator", property = "systemAdministrator", javaType = Boolean.class),

            @Result(column = "create_time", property = "createTime", javaType = Date.class),
            @Result(column = "update_time", property = "updateTime", javaType = Date.class),

            @Result(column = "user_id", property = "userId", javaType = Integer.class),
            @Result(column = "department_id", property = "departmentId", javaType = Integer.class)
    })
    PowerModel findById(PowerModel model);

    @Select("SELECT * FROM tb_power WHERE department_id = #{departmentId}")
    @ResultMap(value = "powerMap")
    List<PowerModel> findByDepartmentId(@Param("departmentId") Integer departmentId);


    @Select("SELECT * FROM tb_power WHERE user_id = #{userId} LIMIT 1")
    @ResultMap(value = "powerMap")
    PowerModel findByUserId(@Param("userId") Integer userId);

    @Delete("DELETE FROM tb_power WHERE id = #{id}")
    void deleteById(@Param("id") Integer id);

    /********** 内部类 *********/
    class PowerProvider {
        public String updateModel(PowerModel model) {
            SQL sql = new SQL();
            sql.UPDATE("tb_power");
            if (model.getAnswerAuditor() != null && !"".equals(model.getAnswerAuditor())) {
                sql.SET("answer_auditor = #{answerAuditor}");
            }
            if (model.getDepartmentLeader() != null && !"".equals(model.getDepartmentLeader())) {
                sql.SET("department_leader = #{departmentLeader}");
            }
            if (model.getProjectAuditor() != null && !"".equals(model.getProjectAuditor())) {
                sql.SET("project_auditor = #{projectAuditor}");
            }
            if (model.getSenateMembers() != null && !"".equals(model.getSenateMembers())) {
                sql.SET("senate_members = #{senateMembers}");
            }
            if (model.getSystemAdministrator() != null && !"".equals(model.getSystemAdministrator())) {
                sql.SET("system_administrator = #{systemAdministrator}");
            }
            sql.SET("update_time = now()");

            sql.WHERE("id = #{id}");
            return sql.toString();
        }

//        public String save(PowerModel model) {
//            SQL sql = new SQL();
//            sql.INSERT_INTO("tb_user");
//            if (model.getNo() != null && !"".equals(model.getNo())) {
//                sql.VALUES("no", "#{no}");
//            }
//            if (model.getName() != null && !"".equals(model.getName())) {
//                sql.VALUES("name", "#{name}");
//            }
//            if (model.getGender() != null && !"".equals(model.getGender())) {
//                sql.VALUES("gender", "#{gender}");
//            }
//            if (model.getPhone() != null && !"".equals(model.getPhone())) {
//                sql.VALUES("phone", "#{phone}");
//            }
//            if (model.getIntroduce() != null && !"".equals(model.getIntroduce())) {
//                sql.VALUES("introduce", "#{introduce}");
//            }
//            if (model.getPassword() != null && !"".equals(model.getPassword())) {
//                sql.VALUES("password", "#{password}");
//            } else {
//                sql.VALUES("password", "#{no}");
//            }
//            if (model.getDepartmentId() != null && !"".equals(model.getDepartmentId())) {
//                sql.VALUES("department_id", "#{departmentId}");
//            }
//            sql.VALUES("update_time", "now()");
//            sql.VALUES("create_time", "now()");
//
//            return sql.toString();
//        }
//
//        public String findListByModel(PowerModel model) {
//            SQL sql = new SQL();
//            sql.SELECT("*");
//            sql.FROM("tb_user");
//            if (model.getNo() != null && !"".equals(model.getNo())) {
//                sql.WHERE("no like #{no}");
//            }
//            if (model.getName() != null && !"".equals(model.getName())) {
//                sql.WHERE("name like #{name}");
//            }
//            if (model.getGender() != null && !"".equals(model.getGender())) {
//                sql.WHERE("gender = #{gender}");
//            }
//            if (model.getPassword() != null && !"".equals(model.getPassword())) {
//                sql.WHERE("password = #{password}");
//            }
//            if (model.getDepartmentId() != null && !"".equals(model.getDepartmentId())) {
//                sql.WHERE("department_id = #{departmentId}");
//            }
//            return sql.toString();
//        }

    }
}
