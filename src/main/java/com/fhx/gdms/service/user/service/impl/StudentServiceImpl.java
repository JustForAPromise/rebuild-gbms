package com.fhx.gdms.service.user.service.impl;

import login.com.department.service.DepartmentService;
import login.com.major.service.MajorService;
import login.com.projections.service.ProjectionService;
import login.com.selectRecord.model.SelectRecordModel;
import login.com.selectRecord.service.SelectRecordService;
import login.com.user.model.UserModel;
import login.com.user.repository.StudentRepository;
import login.com.user.service.StudentService;
import login.com.user.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ProjectionService projectionService;

    @Autowired
    private SelectRecordService selectRecordService;

    @Override
    public UserModel findByNoAndPasswd(String no, String password) {
        UserModel model = new UserModel();
        model.setNo(no);
        model.setPassword(password);
        model = studentRepository.findByNoAndPassword(model);

        return model;
    }

    @Override
    public void updateTeacherId(Integer studentId, Integer teacherId) {
        studentRepository.updateTeacherId(studentId, teacherId);
    }

    @Override
    public List<Integer> listStudentId(UserModel student) {
        return studentRepository.listStudentId(student);
    }

    @Override
    public List<UserModel> findByTeacherId(Integer teacherId) {
        List<UserModel> modelList = studentRepository.findByTeacherId(teacherId);

        modelList.stream().forEach(data ->{
            data.setProjectionModel(projectionService.findByUserIdAndTeacherId(data.getId(), data.getTeacherId()));
        });

        return modelList;
    }

    @Override
    public UserModel findOne(UserModel student) {
        return studentRepository.findOne(student);
    }

    @Override
    public Integer findTotal(UserModel model) {
        return studentRepository.findTotal(model);
    }

    @Override
    public UserModel findByNameAndPassword(String name, String password) {
        UserModel model = new UserModel();
        model.setName(name);
        model.setPassword(password);
        model = studentRepository.findByNameAndPassword(model);

        return model;
    }

    @Override
    public UserModel save(UserModel model) {
        studentRepository.save(model);

        return this.findById(model.getId());
    }

    @Override
    public UserModel update(UserModel model) {
        studentRepository.update(model);

        return this.findById(model.getId());
    }


    @Override
    public UserModel saveStudent(UserModel model) {
        UserModel existModel = this.findByNo(model.getNo());
        if (existModel != null){
            return null;
        }

        model.setIdentify(2);
        model = this.save(model);
        model = getMoreInfo(model);

        return model;
    }

    @Override
    public UserModel updateStudent(UserModel model) {
        UserModel existModelWithNo = this.findByNo(model.getNo());
        if (existModelWithNo == null){
            model = this.update(model);
            model = getMoreInfo(model);

            return model;
        }
        if (existModelWithNo.getId() == model.getId()){
            model = this.update(model);
            model = getMoreInfo(model);

            return model;
        }else{
            return null;
        }
    }

    @Override
    public List<UserModel> findStudent(UserModel model) {
        List<UserModel> results =  studentRepository.findList(model);

        results.stream().forEach(data ->{
            data = getMoreInfo(data);
        });

        return results;
    }

    @Override
    public UserModel findByNo(String no) {
        return studentRepository.findByNo(no);
    }

    @Override
    public UserModel findById(Integer id) {
        UserModel model =  studentRepository.findById(id);

        model = getMoreInfo(model);

        return model;
    }

    @Override
    public List<UserModel> findByMajorIdAndDepartmentId(Integer departmentId, Integer majorId) {
        List<UserModel> modelList = studentRepository.findByMajorIdAndDepartmentId(departmentId, majorId);

        modelList.stream().forEach(data ->{
            data = getMoreInfo(data);
        });

        return modelList;
    }

    @Override
    public void deleteById(Integer id) {
        studentRepository.deleteById(id);
    }

    private UserModel getMoreInfo(UserModel model){
        if (model.getTeacherId() != null) {
            model.setTeacherModel(teacherService.findById(model.getTeacherId()));
        }
        model.setDepartmentModel(departmentService.findById(model.getDepartmentId()));
        model.setMajorModel(majorService.findById(model.getMajorId()));

        SelectRecordModel selectRecordModel = new SelectRecordModel();
        selectRecordModel.setStudentId(model.getId());
        selectRecordModel.setAuditStatus(1);

        List<SelectRecordModel> results = selectRecordService.findList(selectRecordModel);
        if (results.size() == 1){
            model.setProjectionSelectModel(results.get(0));
            model.setProjectionModel(projectionService.findById(results.get(0).getProjectionId()));
        }

        return model;
    }
}
