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



        super.addViewControllers(registry);
    }
}
