package com.fhx.gdms.login.web;

import com.fhx.gdms.department.service.DepartmentService;
import com.fhx.gdms.major.service.MajorService;
import com.fhx.gdms.power.service.PowerService;
import com.fhx.gdms.projections.model.ProjectionModel;
import com.fhx.gdms.projections.service.ProjectionService;
import com.fhx.gdms.selectRecord.model.SelectRecordModel;
import com.fhx.gdms.selectRecord.service.SelectRecordService;
import com.fhx.gdms.user.model.UserModel;
import com.fhx.gdms.user.service.AdminService;
import com.fhx.gdms.user.service.HelperService;
import com.fhx.gdms.user.service.StudentService;
import com.fhx.gdms.user.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private HttpSession session;

    @Autowired
    private AdminService adminService;

    @Autowired
    private HelperService helperService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private PowerService powerService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private SelectRecordService selectRecordService;

    @Autowired
    private ProjectionService projectionService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView login(Integer identify, String no, String password) {
        UserModel userModel = new UserModel();
        ModelAndView modelAndView =  null;

        if (identify == 1) {
            userModel = adminService.findByNoAndPasswd(no, password);

            if (userModel != null) {
                 modelAndView = new ModelAndView("/admin/index.html");
            }

        } else if (identify == 2) {
            userModel = teacherService.findByNoAndPasswd(no, password);
            if (userModel != null) {
                 modelAndView = new ModelAndView("/teacher/index.html");
            }

        } else if (identify == 3) {
            userModel = helperService.findByNoAndPasswd(no, password);
            if (userModel != null) {
                 modelAndView = new ModelAndView("/helper/index.html");
            }

        } else if (identify == 4) {
            userModel = studentService.findByNoAndPasswd(no, password);
            if (userModel != null) {
                 modelAndView = new ModelAndView("/student/index.html");
            }
        }


        if (userModel == null){
            modelAndView = new ModelAndView("/login.html");
            modelAndView.addObject("tip", "密码或用户名错误！");
            return modelAndView;
        }else{
            if (userModel.getPowerId() != null){
                userModel.setPowerModel(powerService.findById(userModel.getPowerId()));
            }
            if (userModel.getTeacherId() != null){
                userModel.setTeacherModel(teacherService.findById(userModel.getTeacherId()));
            }
            if (userModel.getDepartmentId() != null){
                userModel.setDepartmentModel(departmentService.findById(userModel.getDepartmentId()));
            }
            if (userModel.getMajorId() != null){
                userModel.setMajorModel(majorService.findById(userModel.getMajorId()));
            }
            if (userModel.getIdentify() == 2){
                SelectRecordModel selectRecordModel =  selectRecordService.findHavedSelectedRecordByStudentId(userModel.getId());
                if (selectRecordModel != null){
                    ProjectionModel projectionModel = projectionService.findById(selectRecordModel.getProjectionId());
                    userModel.setProjectionModel(projectionModel);
                }
            }
            session.setAttribute("userInfo", userModel);
            return modelAndView;
        }
    }

    @RequestMapping(value = "/loginOut", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView loginOut() {
        session.removeAttribute("userInfo");
        ModelAndView modelAndView = new ModelAndView("/login.html");
        return modelAndView;
    }
}
