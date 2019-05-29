package login.com.department.repository;

import login.com.department.model.DepartmentModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface DepartmentRepository {


    @Insert("INSERT INTO tb_department(department, create_time, update_time) VALUES(#{department}, now(), now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(DepartmentModel model);

    @Select("SELECT * FROM tb_department WHERE id = #{id}")
    @Results(id = "departmentMap", value = {
            @Result(column = "id", property = "id", javaType = Integer.class),
            @Result(column = "department", property = "department", javaType = String.class),
            @Result(column = "create_time", property = "createTime", javaType = Date.class),
            @Result(column = "update_time", property = "updateTime", javaType = Date.class)
    })
    DepartmentModel findById(DepartmentModel model);

    @Select("SELECT * FROM tb_department")
    @ResultMap(value = "departmentMap")
    List<DepartmentModel> findAll();

        @Select("SELECT * FROM tb_department WHERE department LIKE #{departmentName}")
    @ResultMap(value = "departmentMap")
    List<DepartmentModel> findByName(@Param("departmentName") String departmentName);

    @Update("UPDATE tb_department SET department = #{department}, update_time = now() WHERE id = #{id}")
    @ResultMap(value = "departmentMap")
    void update(DepartmentModel model);

    @Select("SELECT * FROM tb_department where department = #{department} ORDER BY create_time DESC LIMIT 1")
    @ResultMap(value = "departmentMap")
    DepartmentModel findOneByName(DepartmentModel model);

    @Delete("DELETE FROM tb_department WHERE id = #{id}")
    void deleteById(@Param("id") Integer id);
}
