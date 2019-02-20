package com.fhx.gdms.projections.service;

import com.fhx.gdms.projections.model.ProjectionModel;

import java.util.List;

public interface ProjectionService {
    /**
     *  保存（base）
     * @param model
     * @return
     */
    ProjectionModel save(ProjectionModel model);
    /**
     * 更新（base）
     * @param model
     * @return
     */
    ProjectionModel update(ProjectionModel model);

    /**
     * 创建
     * @param model
     * @return
     */
    ProjectionModel saveTeacher(ProjectionModel model);

    /**
     * 更新
     * @param model
     * @return
     */
    ProjectionModel updateTeacher(ProjectionModel model);

    /**
     * 查询全部
     * @return
     */
    List<ProjectionModel> findAll();

    /**
     * 条件查询
     * @param model
     * @return
     */
    List<ProjectionModel> findTeacher(ProjectionModel model);
}
