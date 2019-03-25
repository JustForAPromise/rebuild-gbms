package com.fhx.gdms.service.studentScoreRecord.service.impl;

import com.fhx.gdms.service.scoreItem.model.ScoreItemModel;
import com.fhx.gdms.service.scoreItem.service.ScoreItemService;
import com.fhx.gdms.service.studentScoreRecord.model.StudentScoreRecordModel;
import com.fhx.gdms.service.studentScoreRecord.repository.StudentScoreRecordRepository;
import com.fhx.gdms.service.studentScoreRecord.service.StudentScoreRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentScoreRecordServiceImpl implements StudentScoreRecordService  {

    @Autowired
    private StudentScoreRecordRepository studentScoreRecordRepository;

    @Autowired
    private ScoreItemService scoreItemService;

    @Transactional
    @Override
    public List<StudentScoreRecordModel> ListByStudentId(Integer studentId, Integer type) {
        List<StudentScoreRecordModel> results =  new ArrayList<>();

        List<ScoreItemModel> scoreItemModels = scoreItemService.findAlive(type);

        scoreItemModels.stream().forEach(data ->{
            StudentScoreRecordModel model = studentScoreRecordRepository.findByStudentIdAndItemId(studentId, data.getId());
            if ( null == model){
                model = new StudentScoreRecordModel();
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
    public StudentScoreRecordModel save(StudentScoreRecordModel model) {
        studentScoreRecordRepository.save(model);

        return this.findById(model.getId());
    }

    @Override
    public StudentScoreRecordModel findById(Integer id) {

        return studentScoreRecordRepository.findById(id);
    }

    @Override
    public StudentScoreRecordModel update(StudentScoreRecordModel model) {
        studentScoreRecordRepository.update(model);

        return this.findById(model.getId());
    }

    @Override
    public StudentScoreRecordModel createRecord(StudentScoreRecordModel model) {
        model.setScoreNum(0);

        return this.save(model);
    }
}