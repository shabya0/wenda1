package my.wenda.interecptor;

import my.wenda.dao.LoginTicketDAO;
import my.wenda.dao.UserDAO;
import my.wenda.model.HostHolder;
import my.wenda.model.LoginTicket;
import my.wenda.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class PassportInterceptor implements HandlerInterceptor {
    //请求开始前调用，return false则请求结束
    @Autowired
    LoginTicketDAO loginTicketDAO;
    @Autowired
    private UserDAO userDAO;

    @Autowired
    HostHolder hostHolder;      //当前登录用户的数据
    private static final Logger logger = LoggerFactory.getLogger(PassportInterceptor.class);

    //根据cookie的token值(ticket)查找用户，不需要重新登录，cookie有效期为一个月
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ticket = null;
        if(request.getCookies()!=null){
            for(Cookie cookie: request.getCookies()){
                //获取token值
                if(cookie.getName().equals("ticket")){
                    ticket = cookie.getValue();
                    break;
                }
            }
            if(ticket!=null){
                LoginTicket loginTicket = loginTicketDAO.selectByTicket(ticket);
                //判断为有效token
                if(loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus()!=0){
                    return true;
                }
                User user = userDAO.getUserById(loginTicket.getUserId());
                 hostHolder.setUsers(user);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(modelAndView!=null){
            modelAndView.addObject("user",hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();     //删除hostHolder中的数据
    }
}
