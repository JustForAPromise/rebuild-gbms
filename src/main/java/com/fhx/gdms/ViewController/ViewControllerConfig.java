package com.fhx.gdms.ViewController;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;

@Configuration
public class ViewControllerConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("").setViewName("/login.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        registry.addViewController("admin/personInfo").setViewName("/admin/personInfo/personInfo.html");
        registry.addViewController("admin/department").setViewName("/admin/infoManage/department.html");
        registry.addViewController("admin/student").setViewName("/admin/infoManage/student.html");
        registry.addViewController("admin/teacher").setViewName("/admin/infoManage/teacher.html");
        registry.addViewController("admin/systemLog").setViewName("/admin/infoManage/systemLog.html");
        registry.addViewController("admin/power").setViewName("/admin/powerManage/power.html");


        registry.addViewController("student/personInfo").setViewName("/student/personInfo/personInfo.html");
        registry.addViewController("student/projection").setViewName("/student/info/projection.html");
        registry.addViewController("student/projectionInfo").setViewName("/student/info/projectionInfo.html");
        registry.addViewController("student/contactTeacher").setViewName("/student/info/contactTeacher.html");
        registry.addViewController("student/score").setViewName("/student/info/score.html");


        registry.addViewController("teacher/personInfo").setViewName("/teacher/personInfo/personInfo.html");
        registry.addViewController("teacher/projection").setViewName("/teacher/info/projection.html");
        registry.addViewController("teacher/student").setViewName("/teacher/info/student.html");
        registry.addViewController("teacher/projectionInfo").setViewName("/teacher/info/projectionInfo.html");
        registry.addViewController("teacher/contactTeacher").setViewName("/teacher/info/contactTeacher.html");
        registry.addViewController("teacher/score").setViewName("/teacher/info/score.html");

        super.addViewControllers(registry);
    }
}
