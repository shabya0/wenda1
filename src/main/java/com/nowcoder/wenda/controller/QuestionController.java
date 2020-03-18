package com.nowcoder.wenda.controller;

import com.nowcoder.wenda.model.*;
import com.nowcoder.wenda.service.CommentService;
import com.nowcoder.wenda.service.LikeService;
import com.nowcoder.wenda.service.QuestionService;
import com.nowcoder.wenda.service.UserService;
import com.nowcoder.wenda.util.WendaUtil;
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

            if(questionService.addQuestion(question) > 0 ){
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
        return "detail";
    }

}
