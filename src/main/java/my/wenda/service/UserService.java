package my.wenda.service;

import my.wenda.controller.FeedController;
import my.wenda.dao.LoginTicketDAO;
import my.wenda.dao.UserDAO;
import my.wenda.model.LoginTicket;
import my.wenda.model.User;
import my.wenda.util.WendaUtil;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    @Autowired
    private LoginTicketDAO loginTicketDAO;

    private static final Logger logger = LoggerFactory.getLogger(FeedController.class);

    public User getUser(int id){
        logger.info("getUser:"+id);
        return userDAO.getUserById(id);
    }
    public User selectUserByName(String name){ return userDAO.selectUserByName(name); }

    public int updatePwd(int userId, String newpwd){
        String salt = UUID.randomUUID().toString().substring(1,6);
        newpwd = WendaUtil.MD5(newpwd + salt);
        return userDAO.updatePwd(userId, newpwd, salt);
    }

    public int updateImg(int userId, String url){
        return userDAO.updateImg(userId, url);
    }

    public Map<String, String> register(String username, String password){
        Map<String, String> map = new HashMap<String, String>();
        if(StringUtils.isBlank(username)){
            map.put("msg","用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }
        User user = userDAO.selectUserByName(username);
        if(user!=null){
            map.put("msg","用户名已经被注册");
            return map;
        }

        user = new User();
        user.setName(username);
        user.setPassword(password);
        user.setSalt(UUID.randomUUID().toString().substring(1,6));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        user.setPassword(WendaUtil.MD5(password+user.getSalt()));
        userDAO.addUser(user);

        map.put("ticket",addLoginTicket( userDAO.selectUserByName(user.getName()).getId()));     //注册成功，添加ticket，默认登录跳转首页
        return map;
    }

    public Map<String, String> login(String username, String password){
        Map<String, String> map = new HashMap<String, String>();
        if(StringUtils.isBlank(username)){
            map.put("msg","用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }
        User user = userDAO.selectUserByName(username);
        if(user==null){
            map.put("msg","用户名不存在");
            return map;
        }

        if( !(WendaUtil.MD5(password + user.getSalt()) ).equals(user.getPassword()) ){
            map.put("msg","密码错误");
            return map;
        }
        //添加ticket
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    public String addLoginTicket(int userId){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);

        Calendar c = Calendar.getInstance();        //使用calendar进行时间运算，date直接运算莫名int溢出
        c.add(Calendar.MONTH,1);  //增加3个月
        Date now = c.getTime();
        loginTicket.setExpired(now);
        loginTicket.setStatus(0);
        //替换uuid中的-
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketDAO.addTicket(loginTicket);
        return loginTicket.getTicket();
    }

    public void logout(String ticket) {
        loginTicketDAO.deleteLoginTicketByTicket(ticket);
//        loginTicketDAO.updateStatus(ticket,1);
    }
}
