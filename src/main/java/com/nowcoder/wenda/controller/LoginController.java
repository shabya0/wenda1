package com.nowcoder.wenda.controller;

import com.nowcoder.wenda.dao.UserDAO;
import com.nowcoder.wenda.model.HostHolder;
import com.nowcoder.wenda.service.UserService;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    UserDAO userDAO;
    @Autowired
    UserService userService;
    @Autowired
    HostHolder hostHolder;
    private static final Logger logger = LoggerFactory.getLogger(SettingController.class);
    @RequestMapping(path = {"/reg/"}, method = {RequestMethod.POST})
    public String reg(Model model,
                      @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value="next",defaultValue = "false")String next,
                      HttpServletResponse response){
        try {
            Map<String, String> map = userService.register(username, password);
            logger.info("注册：进入ticket判断");
            return isContainsTicket(map, response,model,next);
        }catch (Exception e){
            logger.error("注册异常  "    +e.getMessage());
            return "login";
        }
    }

    //判断是否包含ticket，是跳转首页，否，登录页面
    private String isContainsTicket(Map<String, String> map, HttpServletResponse response, Model model,String next) {
        //next为redirect URL
        if (map.containsKey("ticket")) {
            logger.info("包含ticket");
            Cookie cookie = new Cookie("ticket", map.get("ticket"));

            cookie.setMaxAge(3600*24*30*1000);         //设置cookiecu存活时间 1 month
            cookie.setPath("/");
            response.addCookie(cookie);     //添加cookie返回页面
            if(StringUtils.isNotBlank(next)){     //是否未登录跳转
                return "redirect:"+next;
            }
            return "redirect:/";
        }else{
            logger.info("有错误信息msg: "+map.get("msg"));
            model.addAttribute("msg", map.get("msg"));
            model.addAttribute("next",next);
            return "login";
        }
    }

    @RequestMapping(path = {"/reglogin"}, method = {RequestMethod.GET})
    public String reg(Model model,@RequestParam(value = "next",required = false)String next){
        if(StringUtils.isBlank(next)||next==null)
            next="/";
            model.addAttribute("next",next);

        return "login";
    }

    @RequestMapping(path = {"/login/"}, method = {RequestMethod.POST})
    public String reg(Model model,
                      @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value = "next",required = false) String next,
                      @RequestParam(value="rememberme",defaultValue = "false") boolean rememberme,
                      HttpServletResponse response){
        try {
            Map<String, String> map = userService.login(username, password);        //验证用户名，密码
            logger.info("login  ",next);
            return isContainsTicket(map, response,model,next);
        }catch (Exception e){
            logger.error("登录异常  "    +e.getMessage());
            return "login";
        }
    }

    @RequestMapping(path = {"/logout"}, method = {RequestMethod.GET})
    public String logout(@CookieValue("ticket") String ticket){
        userService.logout(ticket);         //删除或修改ticket状态，此处为删除

        return "redirect:/reglogin";
    }
}
