package login.com.studentScoreAllInfo.service;

import login.com.studentScore.itemScore.model.StudentItemScoreModel;
import login.com.studentScoreAllInfo.api.SearchDetailApiGet;
import login.com.studentScoreAllInfo.data.StudentScoreData;
import login.com.user.model.UserModel;

import java.util.List;

public interface StudentScoreService {

    /**
     * 查找学生成绩记录
     *
     * @param receiveData
     * @return
     */
    StudentScoreData findRecord(SearchDetailApiGet receiveData);

    /**
     * 成绩登记
     *
     * @param model
     */
    void updateNum(StudentItemScoreModel model);

    /**
     * 学生查询成绩
     *
     * @param student
     * @return
     */
    StudentScoreData findScoreToStudent(UserModel student);

    /**
     * 系主任 查看学生成绩信息
     *
     * @param student
     * @return
     */
    List<StudentScoreData> findBaseInfoList(UserModel student);

    /**
     * @param studentId
     * @param ids       分数项Id
     * @param scoreNums
     * @param type      1 指导老师 2 评阅教师 3 答辩教师
     */
    void updateStudentScore(Integer studentId, Integer[] ids, Integer[] scoreNums, Integer type);
}
