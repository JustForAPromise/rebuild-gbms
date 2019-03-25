package com.fhx.gdms.service.web;

import com.fhx.gdms.service.projections.model.ProjectionModel;
import com.fhx.gdms.service.projections.service.ProjectionService;
import com.fhx.gdms.service.studentNumOfTeacher.model.StudentNumOfTeacherModel;
import com.fhx.gdms.service.studentNumOfTeacher.service.StudentNumOfTeacherService;
import com.fhx.gdms.supportUtil.ApiResult;
import com.fhx.gdms.service.user.model.UserModel;
import com.fhx.gdms.service.user.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/help:student")
public class StudentInfoController {

    @Autowired
    private HttpSession session;

    @Autowired
    private StudentNumOfTeacherService studentNumOfTeacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ProjectionService projectionService;

    @RequestMapping(value = "/findList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult findList(UserModel model) {
        ApiResult apiResult = new ApiResult();

        UserModel loginUser = (UserModel) session.getAttribute("userInfo");

        model.setDepartmentId(loginUser.getDepartmentId());

        if (model.getNo() != null) {
            model.setNo("%" + model.getNo() + "%");
        }
        if (model.getName() != null) {
            model.setName("%" + model.getName() + "%");
        }
        List<UserModel> students = studentService.findStudent(model);

        List<ProjectionModel> results = new ArrayList<>();
        students.stream().forEach(data -> {
            ProjectionModel projectionModel = projectionService.findByUserIdAndTeacherId(data.getId(), data.getTeacherId());
            if (projectionModel != null) {
                projectionModel.setStudentModel(data);
                results.add(projectionModel);
            }
        });

        apiResult.setCode(0);
        apiResult.setData(results);
        return apiResult;
    }

    @RequestMapping(value = "/updateNum", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult updateNum(StudentNumOfTeacherModel model) {
        ApiResult apiResult = new ApiResult();

        UserModel loginUser = (UserModel) session.getAttribute("userInfo");
        if (loginUser.getIdentify() < 3) {
            apiResult.setCode(-1);
            apiResult.setMsg("权限不足！");
            return apiResult;
        }

        StudentNumOfTeacherModel results = studentNumOfTeacherService.update(model);

        apiResult.setCode(0);
        apiResult.setData(results);
        return apiResult;
    }
}
