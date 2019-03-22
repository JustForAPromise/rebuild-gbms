package com.fhx.gdms.projectAuditor.web;

import com.fhx.gdms.power.model.PowerModel;
import com.fhx.gdms.power.service.PowerService;
import com.fhx.gdms.projections.model.ProjectionModel;
import com.fhx.gdms.projections.service.ProjectionService;
import com.fhx.gdms.supportUtil.ApiResult;
import com.fhx.gdms.user.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/projectAuditor")
public class ProjectAuditorController {

    @Autowired
    private ProjectionService projectionService;

    @Autowired
    private PowerService powerService;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/listAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ModelAndView listAll() {
        ModelAndView modelAndView = new ModelAndView("/projectAuditor/projection.html");

        UserModel projectAuditor = (UserModel) session.getAttribute("userInfo");

        PowerModel power = powerService.findById(projectAuditor.getPowerId());

        List<ProjectionModel> projectionModelList = projectionService.listProjectionToAudit(projectAuditor.getId(), projectAuditor.getDepartmentId());

        modelAndView.addObject("projections", projectionModelList);
        return modelAndView;
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult findById(Integer id) {
        ApiResult apiResult = new ApiResult();

        ProjectionModel result  = projectionService.findById(id);

        apiResult.setCode(0);
        apiResult.setData(result);
        return apiResult;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult update(ProjectionModel model) {
        ApiResult apiResult = new ApiResult();

        model = projectionService.updateProjection(model);

        if (model != null) {
            apiResult.setCode(0);
            apiResult.setMsg("更新成功");
            apiResult.setData(model);
        } else {
            apiResult.setCode(-1);
            apiResult.setMsg("ERROR！");
        }
        return apiResult;
    }

    @RequestMapping(value = "/listToAudit", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult listToAudit() {
        ApiResult apiResult = new ApiResult();

        UserModel projectAuditor = (UserModel) session.getAttribute("userInfo");
        List<ProjectionModel> projectionModelList = projectionService.listProjectionToAudit(projectAuditor.getId(), projectAuditor.getDepartmentId());

        apiResult.setCode(0);
        apiResult.setData(projectionModelList);
        return apiResult;
    }

    @RequestMapping(value = "/findByAuditStatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ApiResult findByAuditStatus(Integer auditStatus) {
        ApiResult apiResult = new ApiResult();

        UserModel userInfo = (UserModel) session.getAttribute("userInfo");
        ProjectionModel model = new ProjectionModel();
        model.setAuditStatus(auditStatus);
        model.setTeacherId(userInfo.getId());
        model.setDepartmentId(userInfo.getDepartmentId());

        List<ProjectionModel> projectionModelList = projectionService.findListToAudit(model);

        apiResult.setData(projectionModelList);

        return apiResult;
    }
}
