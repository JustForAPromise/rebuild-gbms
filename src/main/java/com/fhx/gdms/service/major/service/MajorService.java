package com.fhx.gdms.service.major.service;

import com.fhx.gdms.service.major.model.MajorModel;

import java.util.List;

public interface MajorService {
    /**
     * 根据系别id查找
     * @param departmentId
     * @return
     */
    List<MajorModel> findByDepartmentId(Integer departmentId);

    /**
     * 创建
     * @param model
     * @return
     */
    MajorModel saveMajor(MajorModel model);

    /**
     * 更新
     * @param model
     * @return
     */
    MajorModel updateMajor(MajorModel model);

    /**
     * 根据专业名查找
     * @param majorName
     * @return
     */
    MajorModel findOneByName(String majorName);

    /**
     * 更新（base）
     * @param model
     * @return
     */
    MajorModel update(MajorModel model);

    /**
     * id查询
     * @param id
     * @return
     */
    MajorModel findById(Integer id);

    /**
     * 创建（base）
     * @param model
     * @return
     */
    MajorModel save(MajorModel model);

    /**
     * 删除指定di 记录， 返回剩余同departmentId记录
     * @param id
     * @return
     */
    List<MajorModel> deleteMajorById(Integer id);

    /**
     * 删除（base）
     * @param id
     */
    void deleteById(Integer id);
}
