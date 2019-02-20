package com.fhx.gdms.taskbook.service;

import com.fhx.gdms.taskbook.model.TaskBookModel;

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
     * 创建
     * @param model
     * @return
     */
    TaskBookModel saveTeacher(TaskBookModel model);

    /**
     * 更新
     * @param model
     * @return
     */
    TaskBookModel updateTeacher(TaskBookModel model);

    /**
     * 查询全部
     * @return
     */
    List<TaskBookModel> findAll();

    /**
     * 条件查询
     * @param model
     * @return
     */
    List<TaskBookModel> findTeacher(TaskBookModel model);
}
