package my.wenda.controller;

import my.wenda.dao.UserDAO;
import my.wenda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class sqlController {
    @Autowired
    UserDAO userDAO;

    @RequestMapping(path={"/sql"})
    @ResponseBody       //返回字符串
    public String index(){
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<11;++i) {
            sb.append(userDAO.getUserById(i+1).getName().toString()+"<br>");
        }
//        Assert.is("xx", userDAO.selectById(2).getPassword());
        User user = userDAO.getUserById(2);
        return sb.toString()+"<br> #{user.getId} #{user.getName()} #{user.getPassword} #{user.getSalt}" ;
    }
}
