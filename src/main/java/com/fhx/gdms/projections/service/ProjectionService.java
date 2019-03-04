package com.fhx.gdms.projections.service;

import com.fhx.gdms.projections.model.ProjectionModel;
import com.fhx.gdms.user.model.UserModel;

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
     * 查询全部
     * @return
     */
    List<ProjectionModel> findAll();

    /**
     * id查询
     * @param id
     * @return
     */
    ProjectionModel findById(Integer id);

    /**
     * 更新
     * @param model
     * @return
     */
    ProjectionModel updateProjection(ProjectionModel model);

    /**
     * id 删除
     * @param id
     */
    void deleteById(Integer id);

    /**
     *
     * @param title
     * @return
     */
    List<ProjectionModel> findByTitle(String title);

    /**
     * 创建
     * @param model
     * @param userInfo
     * @return
     */
    ProjectionModel saveProjection(ProjectionModel model, UserModel userInfo);

    /**
     * 查询
     * @param model
     * @return
     */
    List<ProjectionModel> findList(ProjectionModel model);

    /**
     * 学生 可选课题（待废弃）
     * @param model
     * @return
     */
    List<ProjectionModel> listAllProjection(ProjectionModel model);

    /**
     * 学生可选课题（重构）
     * @param projectionModel
     * @param student
     * @return
     */
    List<ProjectionModel> listProjectionToStudent(ProjectionModel projectionModel, UserModel student, Integer status);
}
