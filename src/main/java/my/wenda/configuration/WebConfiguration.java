package my.wenda.configuration;

import my.wenda.interecptor.LoginRequredInterceptor;
import my.wenda.interecptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebConfiguration implements WebMvcConfigurer {
    @Autowired
    PassportInterceptor passportInterceptor;        //自定义拦截器 登录用户信息
    @Autowired
    LoginRequredInterceptor loginRequredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);       //系统初始化时自动添加自动登录用拦截器
        registry.addInterceptor(loginRequredInterceptor).addPathPatterns("/user/*");   //登录跳转拦截器
    }
}
