package com.fhx.gdms.selectRecord.service;

import com.fhx.gdms.projections.model.ProjectionModel;
import com.fhx.gdms.selectRecord.model.SelectRecordModel;
import com.fhx.gdms.user.model.UserModel;

import java.util.List;

public interface SelectRecordService {

    /**
     * 创建
     * @param model
     * @return
     */
    SelectRecordModel saveSelect(SelectRecordModel model);

    /**
     * 创建（base）
     * @param model
     * @return
     */
    SelectRecordModel save(SelectRecordModel model);

    /**
     * id查询
     * @param id
     * @return
     */
    SelectRecordModel findById(Integer id);

    /**
     * 更新
     * @param model
     * @return
     */
    SelectRecordModel updateSelect(SelectRecordModel model);

    /**
     * 更新（base）
     * @param model
     * @return
     */
    SelectRecordModel update(SelectRecordModel model);

    /**
     * 列出学生
     * @param id
     * @return
     */
    List<SelectRecordModel> listStudent(Integer id);

    /**
     * 拒绝学生
     * @param id
     * @return
     */
    Integer refuceStudent(Integer id);

    /**
     * 接收学生
     * @param id
     * @return
     */
    Integer receiveStudent(Integer id);

    /**
     * 列出学生已选课题id
     * @param userId
     * @return
     */
    List<Integer> findByUserId(Integer userId);

    /**
     * 查询学生已选课题数据
     * @param studentId
     * @param status        0未处理 1接收 2拒绝
     * @return
     */
    Integer findTotalByUserId(Integer studentId, Integer status);

    /**
     * 列出已接收的学生
     * @param teacherId
     * @return
     */
    List<SelectRecordModel> listReceiveStudent(Integer teacherId);

    /**
     * 查询已确定课题信息
     * @param studentId
     * @return
     */
    SelectRecordModel findHavedSelectedRecordByStudentId(Integer studentId);
}
