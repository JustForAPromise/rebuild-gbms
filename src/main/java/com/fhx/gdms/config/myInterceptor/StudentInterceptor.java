package com.fhx.gdms.config.myInterceptor;

import com.fhx.gdms.service.user.model.UserModel;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther fanhanxi
 * @Date 2019/3/25
 * @Description:
 */
public class StudentInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag =true;
        UserModel user=(UserModel) request.getSession().getAttribute("userInfo");
        if(null==user){
            response.sendRedirect("/student");
            flag = false;
        }else if (user.getIdentify() != 2){
            response.sendRedirect("/student");
            flag = false;
        }else{
            flag = true;
        }
        return flag;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}