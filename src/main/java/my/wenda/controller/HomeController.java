package my.wenda.controller;

import com.alibaba.fastjson.JSON;
import my.wenda.aspect.LogAspect;
import my.wenda.model.*;
import my.wenda.service.CommentService;
import my.wenda.service.FollowService;
import my.wenda.service.QuestionService;
import my.wenda.service.UserService;
import my.wenda.util.WendaUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @RequestMapping(path={"/404/"})
    public  String page404(Model model){
        model.addAttribute("title","404");
        return "nullPage";
    }

    @RequestMapping(path = {"/user/{userId}"})
    public String userIndex(Model model, @PathVariable("userId") int userId){
        model.addAttribute("vos", getQuestions(userId, 0, 10));

        User user = userService.getUser(userId);
        if(user == null){
            return "redirect:/404/";
        }
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
        model.addAttribute("title",user.getName()+"的主页");
        return "profile";
    }



    @RequestMapping(path={"/","index"},method = {RequestMethod.GET})
    public String index(Model model){

        model.addAttribute("vos",getQuestions(0,0,10));
        model.addAttribute("title","享受美好校园时光");
        return "index";
    }

    @CrossOrigin
    @RequestMapping(path={"/more"},method = {RequestMethod.GET, RequestMethod.POST})
    public Object more(Model model, HttpServletResponse response, HttpServletRequest request, @RequestParam("pages") int currpage){
        logger.info("进到了more");
        int offset = (currpage-1)*10;
//        return JSON.toJSON(getQuestions(0,offset, 10));
        model.addAttribute("vos",getQuestions(0,offset, 10));

        return "indexMore";
    }

    @CrossOrigin
    @RequestMapping(path={"/moreProfile"},method = {RequestMethod.GET, RequestMethod.POST})
    public Object moreProfile(Model model, HttpServletResponse response, HttpServletRequest request, @RequestParam("pages") int currpage){
        logger.info("进到了moreProfile");
        int offset = (currpage-1)*10;
        model.addAttribute("vos",getQuestions(hostHolder.getUser().getId(),offset, 10));
        return "profileMore";
    }

    @RequestMapping(path={"/chgpw"},method = {RequestMethod.GET})
    public String changepw(Model model){
        model.addAttribute("title","修改密码");
        return "changepw";
    }

    @RequestMapping(path={"/changepwd/"},method = {RequestMethod.POST})
    public String changepwd(Model model,
                           @RequestParam("username") String oldpwd,
                           @RequestParam("password") String newpwd){
        logger.info("来到了判断密码阶段");
        User user = hostHolder.getUser();
        if(!(WendaUtil.MD5(oldpwd + user.getSalt()) ).equals(user.getPassword())){
            logger.info("密码错误:"+oldpwd+"   "+newpwd);
            model.addAttribute("msg","旧密码填写错误");
            model.addAttribute("title","修改密码");
            return  "changepw";
        }
        if(userService.updatePwd(user.getId(),newpwd) > 0){
            model.addAttribute("重新登录");
            model.addAttribute("msg","修改密码成功，请重新登录");
            model.addAttribute("title","重新登录");
            return "login";

        }else {
            return "redirect:/404/";
        }
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
