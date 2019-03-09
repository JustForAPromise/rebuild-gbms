package com.fhx.gdms.material.service;

import com.fhx.gdms.material.model.MaterialModel;
import com.fhx.gdms.user.model.UserModel;

import java.util.List;

public interface TaskBookService {
    /**
     *  保存（base）
     * @param model
     * @return
     */
    MaterialModel save(MaterialModel model);
    /**
     * 更新（base）
     * @param model
     * @return
     */
    MaterialModel update(MaterialModel model);

    /**
     * 条件查询
     * @param model
     * @return
     */
    List<MaterialModel> findList(MaterialModel model);

    /**
     * 创建
     * @param taskBookModel
     */
    void saveTaskBook(MaterialModel taskBookModel);

    /**
     * id 查询
     * @param id
     * @return
     */
    MaterialModel findById(Integer id);

    /**
     * 删除提交记录(包括文件)
     * @param existModel
     */
    void deleteTackBookRecord(MaterialModel existModel);

    /**
     * 删除记录
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据学生信息 + 教师信息查询
     * @param teacher
     * @param student
     * @return
     */
    List<MaterialModel> listTaskBook(UserModel teacher, UserModel student);

    /**
     * 材料审批
     * @param id
     * @param status
     * @param remark
     */
    MaterialModel updateAudit(Integer id, Integer status, String remark);

    /**
     * @param taskBookModel
     * @return
     */
    MaterialModel findOne(MaterialModel taskBookModel);
}
