package com.fhx.gdms.user.controllers;

import com.fhx.gdms.department.service.DepartmentService;
import com.fhx.gdms.major.service.MajorService;
import com.fhx.gdms.power.service.PowerService;
import com.fhx.gdms.projections.model.ProjectionModel;
import com.fhx.gdms.projections.service.ProjectionService;
import com.fhx.gdms.selectRecord.model.SelectRecordModel;
import com.fhx.gdms.selectRecord.service.SelectRecordService;
import com.fhx.gdms.supportUtil.ApiResult;
import com.fhx.gdms.user.model.UserModel;
import com.fhx.gdms.user.service.StudentService;
import com.fhx.gdms.user.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private HttpSession session;

    @Autowired
    private TeacherService teacherService;

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

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult save(UserModel model) {
        ApiResult apiResult = new ApiResult();

        String no = model.getNo();
        if (no == null || "".equals(no)) {
            apiResult.setCode(-1);
            apiResult.setMsg("学号不能为空");
        }

        model = studentService.saveStudent(model);

        if (model != null) {
            apiResult.setCode(0);
            apiResult.setMsg("添加成功");
            apiResult.setData(model);
        } else {
            apiResult.setCode(-1);
            apiResult.setMsg(no + " 已存在");
        }
        return apiResult;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult update(UserModel model) {
        ApiResult apiResult = new ApiResult();

        model = studentService.updateStudent(model);

        if (model != null) {
            apiResult.setCode(0);
            apiResult.setMsg("更新成功");
            apiResult.setData(model);
        } else {
            apiResult.setCode(-1);
            apiResult.setMsg("学号已存在！");
        }
        return apiResult;
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    void deleteById(Integer id) {

        studentService.deleteById(id);
    }


    @RequestMapping(value = "/findById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult findById(Integer id) {
        ApiResult apiResult = new ApiResult();

        UserModel model = studentService.findById(id);

        apiResult.setData(model);
        return apiResult;
    }

    @RequestMapping(value = "/findStudent", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult findStudent(UserModel model) {
        ApiResult apiResult = new ApiResult();

        model.setName("%" + model.getName() + "%");
        model.setNo("%" + model.getNo() + "%");

        List<UserModel> studentModelList = studentService.findStudent(model);

        apiResult.setData(studentModelList);

        return apiResult;
    }


    @RequestMapping(value = "/findByMajorIdAndDepartmentId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult findByMajorIdAndDepartmentId(Integer departmentId, Integer majorId) {
        ApiResult apiResult = new ApiResult();

        List<UserModel> studentModelList = studentService.findByMajorIdAndDepartmentId(departmentId, majorId);

        apiResult.setData(studentModelList);

        return apiResult;
    }

    @RequestMapping(value = "/getPersonInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView findPersonInfo() {
        UserModel student = (UserModel) session.getAttribute("userInfo");
        student = studentService.findById(student.getId());

        if (student.getTeacherId() != null) {
            student.setTeacherModel(teacherService.findById(student.getTeacherId()));
        }
        if (student.getPowerId() != null) {
            student.setPowerModel(powerService.findById(student.getPowerId()));
        }
        if (student.getDepartmentId() != null) {
            student.setDepartmentModel(departmentService.findById(student.getDepartmentId()));
        }
        if (student.getMajorId() != null) {
            student.setMajorModel(majorService.findById(student.getMajorId()));
        }
        SelectRecordModel selectRecordModel = selectRecordService.findHavedSelectedRecordByStudentId(student.getId());
        if (selectRecordModel != null) {
            ProjectionModel projectionModel = projectionService.findById(selectRecordModel.getProjectionId());
            student.setProjectionModel(projectionModel);
        }

        ModelAndView modelAndView = new ModelAndView("/student/personInfo/personInfo.html");
        modelAndView.addObject("userInfo", student);

        return modelAndView;
    }


    @RequestMapping(value = "/projectionInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView getProjectionInfoPage() {
        UserModel student = (UserModel) session.getAttribute("userInfo");
        student = studentService.findById(student.getId());

        if (student.getTeacherId() == null) {
            ModelAndView modelAndView = new ModelAndView("/student/info/projectionInfo.html");
            modelAndView.addObject("flag", true);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("/student/info/projectionInfo.html");
        return modelAndView;
    }

    @RequestMapping(value = "/listToHelper", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult listTohelper(UserModel model) {
        ApiResult apiResult = new ApiResult();

        UserModel helper = (UserModel) session.getAttribute("userInfo");
        if (helper == null) {
            apiResult.setCode(-1);
            apiResult.setMsg("请先登录！");
            return apiResult;
        }

        model.setDepartmentId(helper.getDepartmentId());
        if (model.getName() != null) {
            model.setName("%" + model.getName() + "%");
        }
        if (model.getNo() != null) {
            model.setNo("%" + model.getNo() + "%");
        }

        List<UserModel> studentModelList = studentService.findStudent(model);

        List<UserModel> results = new ArrayList<>();

        studentModelList.stream().forEach(data -> {
            if (data.getProjectionSelectModel() == null) {
                results.add(data);
            }
        });
        studentModelList.stream().forEach(data -> {
            if (data.getProjectionSelectModel() != null) {
                results.add(data);
            }
        });

        apiResult.setData(results);

        return apiResult;
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult reset(Integer id) {
        ApiResult apiResult = new ApiResult();

        UserModel helper = (UserModel) session.getAttribute("userInfo");
        if (helper == null) {
            apiResult.setCode(-1);
            apiResult.setMsg("请先登录！");
            return apiResult;
        }else if (helper.getIdentify() != 3){
            apiResult.setCode(-1);
            apiResult.setMsg("权限不足！");
            return apiResult;
        }

        UserModel student = studentService.findById(id);
        student.setPassword(student.getNo());

        student = studentService.update(student);
        if (student != null) {
            apiResult.setCode(0);
            apiResult.setMsg("更新成功! 初始密码与学号相同");
        } else {
            apiResult.setCode(-1);
            apiResult.setMsg("ERROR！");
        }
        return apiResult;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView findDetail(@PathVariable Integer id) {

        UserModel helper = (UserModel) session.getAttribute("userInfo");
        if (helper == null) {
           return null;
        }

        UserModel student =studentService.findById(id);

        ModelAndView modelAndView = new ModelAndView("/helper/info/student-detail-info.html");
        modelAndView.addObject("info", student);

        return modelAndView;
    }
}
