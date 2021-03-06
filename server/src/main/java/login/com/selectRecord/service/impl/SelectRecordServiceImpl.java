package login.com.selectRecord.service.impl;

import login.com.materialStatus.model.MaterialStatusModel;
import login.com.materialStatus.service.MaterialStatusService;
import login.com.projections.service.ProjectionService;
import login.com.selectRecord.model.SelectRecordModel;
import login.com.selectRecord.repository.SelectRecordRepository;
import login.com.selectRecord.service.SelectRecordService;
import login.com.user.model.UserModel;
import login.com.user.service.StudentService;
import login.com.user.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SelectRecordServiceImpl implements SelectRecordService {
    @Autowired
    private SelectRecordRepository selectRecordRepository;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ProjectionService projectionService;

    @Autowired
    private MaterialStatusService materialStatusService;

    @Override
    public SelectRecordModel saveSelect(SelectRecordModel model) {
        model.setAuditStatus(0);
        model = this.save(model);

        return model;
    }

    @Override
    public SelectRecordModel save(SelectRecordModel model) {
        selectRecordRepository.save(model);

        return this.findById(model.getId());
    }

    @Override
    public SelectRecordModel findById(Integer id) {

        return selectRecordRepository.findById(id);
    }

    @Override
    public SelectRecordModel updateSelect(SelectRecordModel model) {
        model = this.update(model);

        return model;
    }

    @Override
    public SelectRecordModel update(SelectRecordModel model) {
        selectRecordRepository.update(model);

        return this.findById(model.getId());
    }

    @Override
    public List<SelectRecordModel> listStudent(Integer teacherId) {
        List<SelectRecordModel> modelList = selectRecordRepository.findByTeacherId(teacherId);
        modelList.stream().forEach(data->{
            data.setStudentModel(studentService.findById(data.getStudentId()));
            data.setTeacherModel(teacherService.findById(data.getTeacherId()));
            data.setProjectionModel(projectionService.findById(data.getProjectionId()));
        });

        return modelList;
    }

    @Override
    public Integer refuceStudent(Integer id) {
        return selectRecordRepository.refuceStudent(id);
    }

    @Transactional
    @Override
    public Integer receiveStudent(Integer id) {
        SelectRecordModel selectRecordModel =  this.findById(id);
        Integer flag =  selectRecordRepository.receiveStudent(id);

        studentService.updateTeacherId(selectRecordModel.getStudentId(), selectRecordModel.getTeacherId());

        selectRecordRepository.refuseOtherRequestOfStudent(selectRecordModel.getStudentId());

        MaterialStatusModel materialStatusModel = new MaterialStatusModel();
        materialStatusModel.setTeacherId(selectRecordModel.getTeacherId());
        materialStatusModel.setStudentId(selectRecordModel.getStudentId());
        materialStatusModel.setProjectionId(selectRecordModel.getProjectionId());
        materialStatusModel.setTaskSubmitStatus(0);
        materialStatusModel.setThesesSubmitStatus(0);
        materialStatusService.saveStatus(materialStatusModel);

        return flag;
    }

    @Override
    public List<Integer> findByUserId(Integer userId) {
        List<Integer> projectionId = selectRecordRepository.findByUserId(userId);

        return projectionId;
    }

    @Override
    public Integer findTotalByUserId(Integer studentId, Integer status) {
        return selectRecordRepository.findTotalByStudentId(studentId, status);
    }

    @Override
    public List<SelectRecordModel> listReceiveStudent(Integer teacherId) {

        List<SelectRecordModel> modelList = selectRecordRepository.listReceiveStudent(teacherId);
        modelList.stream().forEach(data->{
            data.setStudentModel(studentService.findById(data.getStudentId()));
            data.setTeacherModel(teacherService.findById(data.getTeacherId()));
            data.setProjectionModel(projectionService.findById(data.getProjectionId()));
        });

        return modelList;
    }

    @Override
    public SelectRecordModel findHavedSelectedRecordByStudentId(Integer studentId) {
        return selectRecordRepository.findHavedSelectedRecordByStudentId(studentId);
    }

    @Override
    public Integer findTotalByTeacherId(Integer teacherId) {
        return selectRecordRepository.findTotalByTeacherId(teacherId);
    }

    @Override
    public SelectRecordModel updateRecord(SelectRecordModel model) {
        SelectRecordModel exitModel = new SelectRecordModel();
        exitModel.setStudentId(model.getStudentId());
        List<SelectRecordModel> existRecord = selectRecordRepository.findList(model);
        if (existRecord.size()>0){
            selectRecordRepository.deleteByStudentId(model.getStudentId());
        }
        model.setAuditStatus(1);

       model =  this.save(model);

        UserModel student = studentService.findById(model.getStudentId());
        student.setTeacherId(model.getTeacherId());
        studentService.update(student);

        return model;
     }

    @Override
    public List<SelectRecordModel> findList(SelectRecordModel selectRecordModel) {
        return selectRecordRepository.findList(selectRecordModel);
    }

    @Override
    public SelectRecordModel findOne(SelectRecordModel selectRecordModel) {
        return selectRecordRepository.findOne(selectRecordModel);
    }
}
