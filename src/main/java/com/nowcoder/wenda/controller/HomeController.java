package com.nowcoder.wenda.controller;

import com.nowcoder.wenda.aspect.LogAspect;
import com.nowcoder.wenda.model.*;
import com.nowcoder.wenda.service.CommentService;
import com.nowcoder.wenda.service.FollowService;
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
    HostHolder hostHolder;
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;
    @Autowired
    FollowService followService;

    @RequestMapping(path = {"/user/{userId}"})
    public String userIndex(Model model, @PathVariable("userId") int userId){
        model.addAttribute("vos", getQuestions(userId, 0, 10));

        User user = userService.getUser(userId);
        ViewObject vo = new ViewObject();
        vo.set("user", user);
        vo.set("commentCount", commentService.getUserCommentCount(userId));
        vo.set("followerCount", followService.getFollowerCount(EntityType.ENTITY_USER, userId));
        vo.set("followeeCount", followService.getFolloweeCount(EntityType.ENTITY_USER, userId));
        logger.info("homeController_user_userId:  "+ hostHolder.getUser());
        if (hostHolder.getUser() != null) {
            vo.set("followed", followService.isFollower(hostHolder.getUser().getId(), EntityType.ENTITY_USER, userId));
        } else {
            vo.set("followed", false);
        }
        model.addAttribute("profileUser", vo);
        return "profile";
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
            ViewObject vo = new ViewObject();
            vo.set("question",question);
            vo.set("user",userService.getUser(question.getUserId()));
            vo.set("followCount", followService.getFollowerCount(EntityType.ENTITY_QUESTION, question.getId()));
            vos.add(vo);
        }
        return vos;
    }
}
