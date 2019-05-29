package login.com.studentScore.itemScore.service.impl;

import login.com.scoreItem.model.ScoreItemModel;
import login.com.scoreItem.service.ScoreItemService;
import login.com.studentScore.itemScore.model.StudentItemScoreModel;
import login.com.studentScore.itemScore.repository.StudentItemScoreRepository;
import login.com.studentScore.itemScore.service.StudentItemScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentItemScoreServiceImpl implements StudentItemScoreService {

    @Autowired
    private StudentItemScoreRepository studentItemScoreRepository;

    @Autowired
    private ScoreItemService scoreItemService;

    @Transactional
    @Override
    public List<StudentItemScoreModel> listByStudentIdAndTeacherType(Integer studentId, Integer type) {
        List<StudentItemScoreModel> results =  new ArrayList<>();

        List<ScoreItemModel> scoreItemModels = scoreItemService.findAlive(type);

        scoreItemModels.stream().forEach(data ->{
            StudentItemScoreModel model = studentItemScoreRepository.findByStudentIdAndItemId(studentId, data.getId());
            if ( null == model){
                model = new StudentItemScoreModel();
                model.setStudentId(studentId);
                model.setScoreItemId(data.getId());

                model = this.createRecord(model);
            }
            model.setScoreItemModel(data);

            results.add(model);
        });

        return results;
    }

    @Override
    public List<StudentItemScoreModel> listByStudentId(Integer studentId) {
        List<StudentItemScoreModel> results = studentItemScoreRepository.findByStudentId(studentId);
        results.stream().forEach(data ->{
            data.setScoreItemModel(scoreItemService.findById(data.getScoreItemId()));
        });

        return results;
    }

    @Override
    public StudentItemScoreModel findOne(StudentItemScoreModel itemScoreModel) {
        return studentItemScoreRepository.findOne(itemScoreModel);
    }

    @Override
    public StudentItemScoreModel save(StudentItemScoreModel model) {
        studentItemScoreRepository.save(model);

        return this.findById(model.getId());
    }

    @Override
    public StudentItemScoreModel findById(Integer id) {

        return studentItemScoreRepository.findById(id);
    }

    @Override
    public StudentItemScoreModel update(StudentItemScoreModel model) {
        studentItemScoreRepository.update(model);

        return this.findById(model.getId());
    }

    @Override
    public StudentItemScoreModel createRecord(StudentItemScoreModel model) {
        model.setScoreNum(0);

        return this.save(model);
    }
}
