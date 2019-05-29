package login.com.user.service;

import login.com.user.model.UserModel;

import java.util.List;

public interface StudentService {

    /**
     * 根据用户名及密码查找用户
     * @param name
     * @param password
     * @return
     */
    UserModel findByNameAndPassword(String name, String password);

    /**
     *  保存（base）
     * @param model
     * @return
     */
    UserModel save(UserModel model);
    /**
     * 更新（base）
     * @param model
     * @return
     */
    UserModel update(UserModel model);


    /**
     * 创建
     * @param model
     * @return
     */
    UserModel saveStudent(UserModel model);

    /**
     * 更新
     * @param model
     * @return
     */
    UserModel updateStudent(UserModel model);

    /**
     * 条件查找
     * @param model
     * @return
     */
    List<UserModel> findStudent(UserModel model);

    /**
     * 学号查询
     * @param no
     * @return
     */
    UserModel findByNo(String no);

    /**
     * id查询
     * @param id
     * @return
     */
    UserModel findById(Integer id);

    /**
     * 系id + 专业id 查询
     * @param departmentId
     * @param majorId
     * @return
     */
    List<UserModel> findByMajorIdAndDepartmentId(Integer departmentId, Integer majorId);

    /**
     * 删除
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 登录（学工号 + 密码）
     * @param no
     * @param password
     * @return
     */
    UserModel findByNoAndPasswd(String no, String password);

    /**
     * 更新教师id
     * @param studentId
     * @param teacherId
     */
    void updateTeacherId(Integer studentId, Integer teacherId);

    /**
     * list student id  by no and name and id of teacher
     * @param student
     * @return
     */
    List<Integer> listStudentId(UserModel student);

    /**
     * 根据教师查找学生
     * @param teacherId
     * @return
     */
    List<UserModel> findByTeacherId(Integer teacherId);

    /**
     * find one
     * @param student
     */
    UserModel findOne(UserModel student);

    /**
     * find total number
     * @param model
     * @return
     */
    Integer findTotal(UserModel model);
}
