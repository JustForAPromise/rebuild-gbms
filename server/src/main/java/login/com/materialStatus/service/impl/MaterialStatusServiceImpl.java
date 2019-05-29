package login.com.materialStatus.service.impl;

import login.com.materialStatus.model.MaterialStatusModel;
import login.com.materialStatus.repository.MaterialStatusRepository;
import login.com.materialStatus.service.MaterialStatusService;
import login.com.projections.service.ProjectionService;
import login.com.user.service.StudentService;
import login.com.user.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialStatusServiceImpl implements MaterialStatusService {
    @Autowired
    private MaterialStatusRepository materialStatusRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ProjectionService projectionService;

    @Override
    public List<MaterialStatusModel> findList(MaterialStatusModel model) {
        List<MaterialStatusModel> list =  materialStatusRepository.findList(model);
        list.stream().forEach(data ->{
            data.setStudent(studentService.findById(data.getStudentId()));
            data.setTeacher(teacherService.findById(data.getTeacherId()));
            data.setProjection(projectionService.findById(data.getProjectionId()));
        });

        return list;
    }

    @Override
    public MaterialStatusModel findOne(MaterialStatusModel materialStatus) {
        return materialStatusRepository.findOne(materialStatus);
    }

    @Override
    public void saveStatus(MaterialStatusModel materialStatusModel) {

        MaterialStatusModel existModel = materialStatusRepository.findOne(materialStatusModel);
        if (existModel == null){
            materialStatusRepository.save(materialStatusModel);
        }else{
            materialStatusRepository.update(materialStatusModel);
        }
    }

    @Override
    public void updateStatus(MaterialStatusModel materialStatusModel) {
        materialStatusRepository.update(materialStatusModel);
    }

}
