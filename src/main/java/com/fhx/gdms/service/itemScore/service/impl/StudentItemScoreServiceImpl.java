package com.fhx.gdms.service.itemScore.service.impl;

import com.fhx.gdms.service.scoreItem.model.ScoreItemModel;
import com.fhx.gdms.service.scoreItem.service.ScoreItemService;
import com.fhx.gdms.service.itemScore.model.StudentItemScoreModel;
import com.fhx.gdms.service.itemScore.repository.StudentItemScoreRepository;
import com.fhx.gdms.service.itemScore.service.StudentItemScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentItemScoreServiceImpl implements StudentItemScoreService {

    @Autowired
    private StudentItemScoreRepository studentScoreRecordRepository;

    @Autowired
    private ScoreItemService scoreItemService;

    @Transactional
    @Override
    public List<StudentItemScoreModel> ListByStudentId(Integer studentId, Integer type) {
        List<StudentItemScoreModel> results =  new ArrayList<>();

        List<ScoreItemModel> scoreItemModels = scoreItemService.findAlive(type);

        scoreItemModels.stream().forEach(data ->{
            StudentItemScoreModel model = studentScoreRecordRepository.findByStudentIdAndItemId(studentId, data.getId());
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
    public StudentItemScoreModel save(StudentItemScoreModel model) {
        studentScoreRecordRepository.save(model);

        return this.findById(model.getId());
    }

    @Override
    public StudentItemScoreModel findById(Integer id) {

        return studentScoreRecordRepository.findById(id);
    }

    @Override
    public StudentItemScoreModel update(StudentItemScoreModel model) {
        studentScoreRecordRepository.update(model);

        return this.findById(model.getId());
    }

    @Override
    public StudentItemScoreModel createRecord(StudentItemScoreModel model) {
        model.setScoreNum(0);

        return this.save(model);
    }
}
