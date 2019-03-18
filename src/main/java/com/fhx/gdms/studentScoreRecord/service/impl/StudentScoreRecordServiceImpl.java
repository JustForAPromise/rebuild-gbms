package com.fhx.gdms.studentScoreRecord.service.impl;

import com.fhx.gdms.materialStatus.model.MaterialStatusModel;
import com.fhx.gdms.materialStatus.service.MaterialStatusService;
import com.fhx.gdms.projections.service.ProjectionService;
import com.fhx.gdms.scoreItem.model.ScoreItemModel;
import com.fhx.gdms.scoreItem.service.ScoreItemService;
import com.fhx.gdms.selectRecord.repository.SelectRecordRepository;
import com.fhx.gdms.studentScoreRecord.model.StudentScoreRecordModel;
import com.fhx.gdms.studentScoreRecord.repository.StudentScoreRecordRepository;
import com.fhx.gdms.studentScoreRecord.service.StudentScoreRecordService;
import com.fhx.gdms.user.service.StudentService;
import com.fhx.gdms.user.service.TeacherService;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
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
    public List<StudentScoreRecordModel> ListByStudentId(Integer studentId) {
        List<StudentScoreRecordModel> results =  new ArrayList<>();

        List<ScoreItemModel> scoreItemModels = scoreItemService.findAlive();
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
