package com.fhx.gdms.service.material.service.impl;

import com.fhx.gdms.service.material.model.MaterialModel;
import com.fhx.gdms.service.material.repository.TaskBookRepository;
import com.fhx.gdms.service.material.service.TaskBookService;
import com.fhx.gdms.service.materialStatus.model.MaterialStatusModel;
import com.fhx.gdms.service.materialStatus.service.MaterialStatusService;
import com.fhx.gdms.service.projections.service.ProjectionService;
import com.fhx.gdms.service.user.model.UserModel;
import com.fhx.gdms.service.user.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Service
public class TaskBookServiceImpl implements TaskBookService {
    @Autowired
    private TaskBookRepository taskBookRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ProjectionService projectionService;

    @Autowired
    private MaterialStatusService materialStatusService;


    @Override
    public MaterialModel save(MaterialModel model) {
        taskBookRepository.save(model);

        return this.findById(model.getId());
    }

    @Override
    public MaterialModel update(MaterialModel model) {
        taskBookRepository.update(model);

        return this.findById(model.getId());
    }

    @Override
    public List<MaterialModel> findList(MaterialModel model) {
        return taskBookRepository.findList(model);
    }

    @Transactional
    @Override
    public void saveTaskBook(MaterialModel taskBookModel) {
        taskBookModel.setAuditStatus(0);
        MaterialModel existModel = taskBookRepository.findOne(taskBookModel);
        if (existModel != null){
            this.deleteTackBookRecord(existModel);
        }

        taskBookModel = this.save(taskBookModel);

        MaterialStatusModel materialStatusModel = new MaterialStatusModel();
        materialStatusModel.setStudentId(taskBookModel.getStudentId());
        materialStatusModel.setTeacherId(taskBookModel.getTeacherId());
        materialStatusModel.setProjectionId(taskBookModel.getProjectionId());
        materialStatusModel.setTaskSubmitStatus(1);
        materialStatusModel.setTaskAuditStatus(0);

        materialStatusService.saveStatus(materialStatusModel);
    }

    @Override
    public MaterialModel findById(Integer id) {
        return taskBookRepository.findById(id);
    }

    @Override
    public void deleteTackBookRecord(MaterialModel existModel) {
        File file = new File(existModel.getFilePath());
        file.delete();

        this.deleteById(existModel.getId());
    }

    @Override
    public void deleteById(Integer id) {
        taskBookRepository.deleteById(id);
    }

    @Override
    public List<MaterialModel> listTaskBook(UserModel teacher, UserModel student) {

        if (student.getNo() != null) {
            student.setNo("%" + student.getNo() + "%");
        }
        if (student.getName() != null) {
            student.setName("%" + student.getName() + "%");
        }
        student.setTeacherId(teacher.getId());
        List<Integer> studentIds = studentService.listStudentId(student);
        if (!(studentIds.size() > 0)){
            return null;
        }

        MaterialModel taskBookModel = new MaterialModel();
        taskBookModel.setStudentIds(studentIds);
        taskBookModel.setTeacherId(teacher.getId());

        List<MaterialModel> taskBookModelList = taskBookRepository.findList(taskBookModel);

        taskBookModelList.stream().forEach(data ->{
            data.setStudent(studentService.findById(data.getStudentId()));
            data.setProjection(projectionService.findById(data.getProjectionId()));
        });
        return taskBookModelList;
    }

    @Transactional
    @Override
    public MaterialModel updateAudit(Integer id, Integer status, String remark) {
        MaterialModel model = this.findById(id);
        if (model == null){
            return null;
        }

        model.setAuditStatus(status);
        model.setAuditRemark(remark);
        model =  this.update(model);

        //状态更新
        MaterialStatusModel materialStatusModel = new MaterialStatusModel();
        materialStatusModel.setStudentId(model.getStudentId());
        materialStatusModel.setTeacherId(model.getTeacherId());
        materialStatusModel.setProjectionId(model.getProjectionId());
        materialStatusModel.setTaskAuditStatus(status);

        materialStatusService.updateStatus(materialStatusModel);

        return model;
    }

    @Override
    public MaterialModel findOne(MaterialModel taskBookModel) {
        return taskBookRepository.findOne(taskBookModel);
    }

}
