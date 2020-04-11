package my.wenda.controller;

import my.wenda.async.EventModel;
import my.wenda.async.EventProducer;
import my.wenda.async.EventType;
import my.wenda.model.*;
import my.wenda.service.*;
import my.wenda.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(SettingController.class);

    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    LikeService likeService;
    @Autowired
    CommentService commentService;
    @Autowired
    FollowService followService;
    @Autowired
    EventProducer eventProducer;

    @RequestMapping(value = "/question/add",method = {RequestMethod.POST})
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title, @RequestParam("content") String content){
        try{
            Question question = new Question();
            if(hostHolder.getUser()!=null){     //可能需要添加未登录跳转
                question.setUserId(hostHolder.getUser().getId());
            }else{      //若要未登录跳转， 可删除
                question.setUserId(WendaUtil.ANONYMOUS_USERID);
            }
            question.setContent(content);
            question.setTitle(title);
            question.setCommentCount(0);
            question.setCreatedDate(new Date());
            int ret = questionService.addQuestion(question);
            if(ret > 0 ){
                logger.info("添加问题返回的id："+ret);
                eventProducer.fireEvent(new EventModel(EventType.ADD_QUESTION).setActorId(question.getUserId())
                        .setEntityId(ret));
                return WendaUtil.getJSONString(0);      //成功返回0
            }

        }catch (Exception e){
            logger.error("增加问题失败： "+e.getMessage());
        }
        return WendaUtil.getJSONString(1,"失败");      //失败返回1
    }


    @RequestMapping(value = "/question/{qid}")
    public String questionDetail(Model model, @PathVariable("qid") int qid){
        Question question = questionService.selectQuestionById(qid);
        model.addAttribute("question", question);
        model.addAttribute("user",userService.getUser(question.getUserId()));
        model.addAttribute("title",question.getTitle());
        List<Comment> commentlist =commentService.getCommentsByEntity(qid, 0);  //0表示问题的评论， 1表示评论的回复
        List<ViewObject> comments = new ArrayList<ViewObject>();
        for(Comment comment: commentlist){
            ViewObject vo = new ViewObject();
            vo.set("comment",comment);
            if(hostHolder.getUser() == null){
                vo.set("liked", 0);     //查询用户是否喜欢，此情况为未登录
            }else{
                vo.set("liked",likeService.getLikeStatus(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT,comment.getId()));
            }
            vo.set("likeCount",likeService.getLikeCount(EntityType.ENTITY_COMMENT,comment.getId()));
            vo.set("user",userService.getUser(comment.getUserId()));
            comments.add(vo);
        }
        model.addAttribute("comments",comments);

        List<ViewObject> followUsers = new ArrayList<ViewObject>();
        // 获取关注的用户信息
        List<Integer> users = followService.getFollowers(EntityType.ENTITY_QUESTION, qid, 20);
        for (Integer userId : users) {
            ViewObject vo = new ViewObject();
            User u = userService.getUser(userId);
            if (u == null)  continue;
            vo.set("name", u.getName());
            vo.set("headUrl", u.getHeadUrl());
            vo.set("id", u.getId());
            followUsers.add(vo);
        }
        model.addAttribute("followUsers", followUsers);
        model.addAttribute("size",followUsers.size());
        if (hostHolder.getUser() != null) {
            model.addAttribute("followed", followService.isFollower(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, qid));
        } else {
            model.addAttribute("followed", false);
        }
        return "detail";
    }
}
