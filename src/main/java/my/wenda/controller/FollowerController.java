package my.wenda.controller;

import my.wenda.async.EventModel;
import my.wenda.async.EventProducer;
import my.wenda.async.EventType;
import my.wenda.model.*;
import my.wenda.service.CommentService;
import my.wenda.service.FollowService;
import my.wenda.service.QuestionService;
import my.wenda.service.UserService;
import my.wenda.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FollowerController {
    @Autowired
    HostHolder hostHolder;
    @Autowired
    EventProducer eventProducer;

    @Autowired
    FollowService followService;

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    private static Logger logger = LoggerFactory.getLogger(CommentController.class);

    @RequestMapping(path = {"/followUser"},method = {RequestMethod.POST})
    @ResponseBody
    public String  follow(@RequestParam("userId") int userId){
        if(hostHolder.getUser() == null )
            return WendaUtil.getJSONString(999,"未登录");

        //关注某个用户
        boolean ret = followService.follow(hostHolder.getUser().getId(), EntityType.ENTITY_USER, userId);
        eventProducer.fireEvent(new EventModel(EventType.FOLLOW).setActorId(hostHolder.getUser().getId())
                .setEntityType(EntityType.ENTITY_USER).setEntityId(userId).setEntityOwnerId(userId));


        //返回用户关注的总数
        return WendaUtil.getJSONString(ret ? 0 : 1,
                String.valueOf(followService.getFolloweeCount(EntityType.ENTITY_USER, hostHolder.getUser().getId())));
    }

    @RequestMapping(path = {"/unfollowUser"},method = {RequestMethod.POST})
    @ResponseBody
    public String  unfollow(@RequestParam("userId") int userId){
        if(hostHolder.getUser() == null )
            return WendaUtil.getJSONString(999,"未登录");

        //取消关注某个用户
        boolean ret = followService.unfollow(hostHolder.getUser().getId(), EntityType.ENTITY_USER, userId);
        eventProducer.fireEvent(new EventModel(EventType.FOLLOW).setActorId(hostHolder.getUser().getId())
                .setEntityType(EntityType.ENTITY_USER).setEntityId(userId).setEntityOwnerId(userId));
        //返回用户关注的总数
        return WendaUtil.getJSONString(ret ? 0 : 1,
                String.valueOf(followService.getFolloweeCount(EntityType.ENTITY_USER, hostHolder.getUser().getId())));
    }


    @RequestMapping(path = {"/followQuestion"},method = {RequestMethod.POST})
    @ResponseBody
    public String  followQuestion(@RequestParam("questionId") int questionId){
        if(hostHolder.getUser() == null )
            return WendaUtil.getJSONString(999,"未登录");

        //判断问题是否存在， 避免地址栏直接输入访问报错
        Question q = questionService.selectQuestionById(questionId);
        if(q == null)
            return WendaUtil.getJSONString(1,"问题不存在");

        //关注某个问题
        boolean ret = followService.follow(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, questionId);
        eventProducer.fireEvent(new EventModel(EventType.FOLLOW).setActorId(hostHolder.getUser().getId())
                .setEntityType(EntityType.ENTITY_QUESTION).setEntityId(questionId).setEntityOwnerId(q.getUserId()));

        Map<String, Object> info = new HashMap<String, Object>();
        info.put("headUrl", hostHolder.getUser().getHeadUrl());
        info.put("name", hostHolder.getUser().getName());
        info.put("id", hostHolder.getUser().getId());
        info.put("count",followService.getFollowerCount(EntityType.ENTITY_QUESTION, questionId));

        //返回用户关注的总数
        return WendaUtil.getJSONString(ret ? 0 : 1, info);
    }

    @RequestMapping(path = {"/unfollowQuestion"},method = {RequestMethod.POST})
    @ResponseBody
    public String  unfollowQuestion(@RequestParam("questionId") int questionId){
        if(hostHolder.getUser() == null )
            return WendaUtil.getJSONString(999,"未登录");

        //判断问题是否存在， 避免地址栏直接输入访问报错
        Question q = questionService.selectQuestionById(questionId);
        if(q == null)
            return WendaUtil.getJSONString(1,"问题不存在");

        //取消关注某个问题
        boolean ret = followService.unfollow(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, questionId);
        eventProducer.fireEvent(new EventModel(EventType.FOLLOW).setActorId(hostHolder.getUser().getId())
                .setEntityType(EntityType.ENTITY_QUESTION).setEntityId(questionId).setEntityOwnerId(q.getUserId()));

        Map<String, Object> info = new HashMap<String, Object>();
        info.put("headUrl", hostHolder.getUser().getHeadUrl());
        info.put("name", hostHolder.getUser().getName());
        info.put("id", hostHolder.getUser().getId());
        info.put("count",followService.getFollowerCount(EntityType.ENTITY_QUESTION, questionId));

        //返回用户关注的总数
        return WendaUtil.getJSONString(ret ? 0 : 1, info);
    }

    //我关注的用户
    @RequestMapping(path = {"/user/{uid}/followees"},method = {RequestMethod.GET})
    public String  followees(Model model, @PathVariable("uid") int uid){
        List<Integer> followeeIds = followService.getFollowees(EntityType.ENTITY_USER, uid, 0, 10);
        if(hostHolder.getUser() != null){
            model.addAttribute("followees", getUsersInfo(hostHolder.getUser().getId(), followeeIds));
        }else {
            model.addAttribute("followees", getUsersInfo(0, followeeIds));
        }
        model.addAttribute("followeeCount", followService.getFolloweeCount(EntityType.ENTITY_USER, uid));
        logger.info("followeeCount:  "+followService.getFolloweeCount(EntityType.ENTITY_USER, uid));
        model.addAttribute("curUser", userService.getUser(uid));
        if(hostHolder.getUser().getId() == uid)
            model.addAttribute("title","我关注的用户");
        else
            model.addAttribute("title","ta关注的用户");
        return "followees";
    }

    //粉丝
    @RequestMapping(path = {"/user/{uid}/followers"},method = {RequestMethod.GET})
    public String  followers(Model model, @PathVariable("uid") int userId){
        List<Integer> followerIds = followService.getFollowers(EntityType.ENTITY_USER, userId, 0, 10);
        if(hostHolder.getUser() != null){
            model.addAttribute("followers", getUsersInfo(hostHolder.getUser().getId(), followerIds));
        }else {
            model.addAttribute("followers", getUsersInfo(0, followerIds));
        }
        model.addAttribute("followerCount", followService.getFollowerCount(EntityType.ENTITY_USER, userId));
        model.addAttribute("curUser", userService.getUser(userId));
        if(hostHolder.getUser().getId() == userId)
            model.addAttribute("title","我的粉丝");
        else
            model.addAttribute("title","ta的粉丝");
        return "followers";

    }

    //获取关注用户或粉丝信息
    private List<ViewObject> getUsersInfo(int localUserId, List<Integer> userIds){
        List<ViewObject> userInfos = new ArrayList<>();
        for(Integer uid: userIds){
            User user = userService.getUser(uid);
            if(user == null)    continue;

            ViewObject vo = new ViewObject();
            vo.set("user", user);
//            vo.set("commentCount", commentService.)
            vo.set("user", user);
            vo.set("commentCount", commentService.getUserCommentCount(uid));
            vo.set("followerCount", followService.getFollowerCount(EntityType.ENTITY_USER, uid));
            vo.set("followerCount", followService.getFollowerCount(EntityType.ENTITY_USER, uid));
            vo.set("followeeCount", followService.getFolloweeCount(EntityType.ENTITY_USER, uid));

            //判断是否为该用户的粉丝
            if(localUserId != 0){
                vo.set("followed", followService.isFollower(localUserId, EntityType.ENTITY_USER, uid));
            }else{
                vo.set("followed",false);
            }
            userInfos.add(vo);
        }
        return userInfos;
    }
}
