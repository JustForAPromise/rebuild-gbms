package com.fhx.gdms.config.ViewController;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ViewControllerConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("").setViewName("/login.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        registry.addViewController("admin/personInfo").setViewName("/admin/personInfo/personInfo.html");
        registry.addViewController("admin/department").setViewName("/admin/infoManage/department.html");
        registry.addViewController("admin/student").setViewName("/admin/infoManage/student.html");
        registry.addViewController("admin/teacher").setViewName("/admin/infoManage/teacher.html");
        registry.addViewController("admin/systemLog").setViewName("/admin/infoManage/systemLog.html");
        registry.addViewController("admin/power").setViewName("/admin/powerManage/power.html");
        registry.addViewController("admin/scoreCondition").setViewName("/admin/scoreCondition/scoreCondition.html");

        registry.addViewController("student/projection").setViewName("/student/info/projection.html");
        registry.addViewController("student/score").setViewName("/student/info/score.html");


        registry.addViewController("teacher/personInfo").setViewName("/teacher/personInfo/personInfo.html");
        registry.addViewController("teacher/projection").setViewName("/teacher/info/projection.html");
        registry.addViewController("teacher/student").setViewName("/teacher/info/student.html");
        registry.addViewController("teacher/materialReview").setViewName("/teacher/info/materialReview.html");
        registry.addViewController("teacher/score").setViewName("/teacher/info/score.html");
        registry.addViewController("teacher/studentScore").setViewName("/teacher/info/studentScore.html");
        registry.addViewController("teacher/power").setViewName("/departmentLeader/power.html");


        registry.addViewController("helper/student-info").setViewName("/helper/info/student-info.html");
        registry.addViewController("helper/teacher-info").setViewName("/helper/info/teacher-info.html");
    }
}
