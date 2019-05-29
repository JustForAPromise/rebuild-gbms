package login.com.user.service;

import login.com.user.model.UserModel;

import java.util.List;

public interface UserService {

    /**
     *  保存（base）
     * @param model
     * @return
     */
    UserModel save(UserModel model);

    /**
     * no + departmentId 查询
     * @param no
     * @param departmentId
     * @return
     */
    UserModel findByNoAndDepartmentId(String no, Integer departmentId);

    /**
     * id 查询
     * @param id
     * @return
     */
    UserModel findById(Integer id);

    /**
     * findOne
     * @param model
     * @return
     */
    UserModel findOne(UserModel model);

    /**
     * 更新用户 权限id
     * @param id
     * @param powerId
     */
    void updatePowerById(Integer id, Integer powerId);

    /**
     * 密码修改
     * @param id
     * @param password
     * @return
     */
    UserModel updatePwd(Integer id, String password);

    /**
     * 添加教务员
     * @param model
     * @return
     */
    UserModel addSupports(UserModel model);

    /**
     * list 教务员
     * @param departmentId
     * @return
     */
    List<UserModel> findSupportsByDepartmentId(Integer departmentId);

    /**
     * update 教务员
     * @param model
     * @return
     */
    UserModel updateSupports(UserModel model);

    /**
     * update (base)
     * @param model
     * @return
     */
    UserModel update(UserModel model);

    /**
     * id 删除教务员
     * @param id
     */
    void deleteById(Integer id);
}
