package com.fhx.gdms.department.service;

import com.fhx.gdms.department.model.DepartmentModel;

import java.util.List;

public interface DepartmentService {
    /**
     *  保存（base）
     * @param model
     * @return
     */
    DepartmentModel save(DepartmentModel model);

    /**
     * 根据id查找记录
     * @param id
     * @return
     */
    DepartmentModel findById(Integer id);

    /**
     * 添加系别
     * @param model
     * @return
     */
    DepartmentModel saveDepartment(DepartmentModel model);

    /**
     * 查找所有记录
     * @return
     */
    List<DepartmentModel> findAll();

    /**
     * 根据系别名称查找
     * @param departmentName
     * @return
     */
    List<DepartmentModel> findByName(String departmentName);

    /**
     * 更新
     * @param model
     * @return
     */
    DepartmentModel updateDepartment(DepartmentModel model);

    /**
     * 更新（base）
     * @param model
     * @return
     */
    DepartmentModel update(DepartmentModel model);

    /**
     * 根据系名查找
     * @param model
     * @return
     */
    DepartmentModel findOneByName(DepartmentModel model);
}
