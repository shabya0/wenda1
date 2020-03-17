package com.nowcoder.wenda.controller;

import com.nowcoder.wenda.aspect.LogAspect;
import com.nowcoder.wenda.model.Question;
import com.nowcoder.wenda.model.ViewObject;
import com.nowcoder.wenda.service.QuestionService;
import com.nowcoder.wenda.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @RequestMapping(path = {"/user/{userId}"})
    public String userIndex(Model model, @PathVariable("userId") int userId){
        model.addAttribute("vos",getQuestions(userId,0,10));
        return "index";
    }

    @RequestMapping(path={"/","index"},method = {RequestMethod.GET})
    public String index(Model model){

        model.addAttribute("vos",getQuestions(0,0,10));
        return "index";
    }

    private List<ViewObject> getQuestions(int userId, int offset, int limit){
        List<Question> questionList = questionService.getLastQuestions(userId,offset,limit);
        List<ViewObject> vos = new ArrayList<ViewObject>();
        //将问题列表加入viewobject
        for(Question question: questionList){
            ViewObject vj = new ViewObject();
            vj.set("question",question);
            vj.set("user",userService.getUser(question.getUserId()));
            vos.add(vj);
        }
        return vos;
    }
}
