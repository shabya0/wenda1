package com.nowcoder.wenda.controller;


import com.nowcoder.wenda.dao.QuestionDAO;
import com.nowcoder.wenda.dao.UserDAO;
import com.nowcoder.wenda.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class SettingController {
    @Autowired
    QuestionDAO questionDAO;
    @Autowired
    UserDAO userDAO;
    private static final Logger logger = LoggerFactory.getLogger(SettingController.class);
    @RequestMapping(path = {"/question"}, method = {RequestMethod.GET})
    @ResponseBody
    public String setting(HttpSession httpSession){
        StringBuffer sb = new StringBuffer();
//            Question question = new Question("title"+199999,"content"+999999,
//                    new Date(), 99999,0);
//            questionDAO.addQuestion(question);
//        List<Question> list = questionDAO.selectLatestQuestions(1,0,6);
//        for(int i=0;i<list.size();++i){
//            sb.append(list.get(i).toString()+"<br>");
//        }
//            User user = new User();
//            user.setPassword("asdfghjkl");
//            user.setSalt("feafafffffffffff");
//            user.setHeadUrl("fefeaeffffffffff");
//            user.setName("aaaaaaaaaaaa");
////            userDAO.updateUserPassword(user);
//        userDAO.addUser(user);
//        sb.append(user.toString());
        User user = userDAO.getUserById(1);
        sb.append(user.toString());

        return sb.toString();
    }
}
