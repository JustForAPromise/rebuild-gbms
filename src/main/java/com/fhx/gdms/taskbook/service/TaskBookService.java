package com.fhx.gdms.taskbook.service;

import com.fhx.gdms.taskbook.model.TaskBookModel;
import com.fhx.gdms.user.model.UserModel;

import java.util.List;

public interface TaskBookService {
    /**
     *  保存（base）
     * @param model
     * @return
     */
    TaskBookModel save(TaskBookModel model);
    /**
     * 更新（base）
     * @param model
     * @return
     */
    TaskBookModel update(TaskBookModel model);

    /**
     * 条件查询
     * @param model
     * @return
     */
    List<TaskBookModel> findList(TaskBookModel model);

    /**
     * 创建
     * @param taskBookModel
     */
    void saveTaskBook(TaskBookModel taskBookModel);

    /**
     * id 查询
     * @param id
     * @return
     */
    TaskBookModel findById(Integer id);

    /**
     * 删除提交记录(包括文件)
     * @param existModel
     */
    void deleteTackBookRecord(TaskBookModel existModel);

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
    List<TaskBookModel> listTaskBook(UserModel teacher, UserModel student);
}
