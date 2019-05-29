package login.com.major.repository;

import login.com.major.model.MajorModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface MajorRepository {


    @Insert("INSERT INTO tb_major(major, introduce, department_id, create_time, update_time) VALUES(#{major}, #{introduce}, #{departmentId}, now(), now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(MajorModel model);

    @Select("SELECT * FROM tb_major WHERE department_id = #{departmentId}")
    @Results(id = "majorMap", value = {
            @Result(column = "id", property = "id", javaType = Integer.class),
            @Result(column = "major", property = "major", javaType = String.class),
            @Result(column = "introduce", property = "introduce", javaType = String.class),
            @Result(column = "create_time", property = "createTime", javaType = Date.class),
            @Result(column = "update_time", property = "updateTime", javaType = Date.class),
            @Result(column = "department_id", property = "departmentId", javaType = Integer.class)
    })
    List<MajorModel> findByDepartmentId(@Param("departmentId") Integer departmentId);

    @Select("SELECT * FROM tb_major where major = #{major} ORDER BY create_time DESC LIMIT 1")
    @ResultMap(value = "majorMap")
    MajorModel findByName(@Param("major") String majorName);

    @Update("UPDATE tb_major SET major = #{major}, introduce = #{introduce}, update_time = now() WHERE id = #{id}")
    @ResultMap(value = "majorMap")
    void update(MajorModel model);

    @Select("SELECT * FROM tb_major where id = #{id} LIMIT 1")
    @ResultMap(value = "majorMap")
    MajorModel findById(@Param("id") Integer id);

    @Delete("DELETE FROM tb_major WHERE id = #{id}")
    void deleteById(@Param("id") Integer id);
}
