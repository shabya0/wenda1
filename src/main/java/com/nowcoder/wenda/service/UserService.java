package com.nowcoder.wenda.service;

import com.nowcoder.wenda.dao.LoginTicketDAO;
import com.nowcoder.wenda.dao.UserDAO;
import com.nowcoder.wenda.model.LoginTicket;
import com.nowcoder.wenda.model.User;
import com.nowcoder.wenda.util.WendaUtil;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    @Autowired
    private LoginTicketDAO loginTicketDAO;
    public User getUser(int id){
        return userDAO.getUserById(id);
    }
    public User selectUserByName(String name){ return userDAO.selectUserByName(name); }
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
