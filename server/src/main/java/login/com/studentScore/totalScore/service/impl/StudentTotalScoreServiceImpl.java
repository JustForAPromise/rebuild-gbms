package login.com.studentScore.totalScore.service.impl;

import login.com.studentScore.totalScore.model.StudentTotalScoreModel;
import login.com.studentScore.totalScore.repository.StudentTotalScoreRepository;
import login.com.studentScore.totalScore.service.StudentTotalScoreService;
import login.com.user.model.UserModel;
import login.com.user.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class StudentTotalScoreServiceImpl implements StudentTotalScoreService {
    @Autowired
    private StudentTotalScoreRepository studentTotalScoreRepository;

    @Autowired
    private StudentService studentService;

    @Override
    public StudentTotalScoreModel save(StudentTotalScoreModel model) {

        studentTotalScoreRepository.save(model);

        return this.findById(model.getId());
    }

    @Override
    public StudentTotalScoreModel update(StudentTotalScoreModel model) {
        studentTotalScoreRepository.updateModel(model);

        return this.findById(model.getId());
    }

    @Override
    public StudentTotalScoreModel findById(Integer id) {
        return studentTotalScoreRepository.findById(id);
    }

    @Override
    public StudentTotalScoreModel findByStudentMolel(UserModel studentModel) {
        StudentTotalScoreModel model = new StudentTotalScoreModel();
        model.setStudentId(studentModel.getId());
        model.setDepartmentId(studentModel.getDepartmentId());
        model.setMajorId(studentModel.getMajorId());

        StudentTotalScoreModel existModel  = studentTotalScoreRepository.findOne(model);
        if (existModel == null){
            model.setScoreNum(new BigDecimal(0));
            model.setStatus("---");

            existModel = this.save(model);
        }

        return existModel;
    }

    @Override
    public StudentTotalScoreModel findByStudentId(Integer studentId) {
        StudentTotalScoreModel existModel = studentTotalScoreRepository.findByStudentId(studentId);
        if (existModel == null){
            StudentTotalScoreModel model = new StudentTotalScoreModel();

            UserModel student = studentService.findById(studentId);

            model.setDepartmentId(student.getDepartmentId());
            model.setMajorId(student.getMajorId());
            model.setStudentId(student.getId());
            model.setScoreNum(new BigDecimal(0));
            model.setStatus("---");

            existModel = this.save(model);
        }

        return existModel;
    }

    @Override
    public void updateStudentScore(StudentTotalScoreModel totalScoreModel) {
        totalScoreModel.setLevel(getLevel(totalScoreModel.getScoreNum()));

        this.update(totalScoreModel);
    }

    @Override
    public List<StudentTotalScoreModel> listByModel(StudentTotalScoreModel studentTotalScoreModel) {
        return studentTotalScoreRepository.listByModel(studentTotalScoreModel);
    }

    /********** 辅助程序 **************/
    private String getLevel(BigDecimal studentNum){

        String level = null;

        if (studentNum.doubleValue() >= 90) {
            level= "优秀";
        } else if (studentNum.doubleValue() >= 80) {
            level= "良好";
        } else if (studentNum.doubleValue() >= 70) {
            level="中等";
        } else if (studentNum.doubleValue() >= 60) {
            level="及格";
        } else {
            level="不及格";
        }

        return level;
    }
}
