package my.wenda.controller;

import my.wenda.async.EventModel;
import my.wenda.async.EventProducer;
import my.wenda.async.EventType;
import my.wenda.model.Comment;
import my.wenda.model.HostHolder;
import my.wenda.service.CommentService;
import my.wenda.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class CommentController {
    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    QuestionService questionService;

    @Autowired
    EventProducer eventProducer;

    private static Logger logger = LoggerFactory.getLogger(CommentController.class);

    @RequestMapping(path = {"/addComment"},method = {RequestMethod.POST})
    public String  addComment(@RequestParam("questionId") int questionId,
                              @RequestParam("content") String content){
        try {
            Comment comment = new Comment();
            comment.setContent(content);
            if (hostHolder.getUser() != null) {
                comment.setUserId(hostHolder.getUser().getId());
            } else {
//                comment.setUserId(WendaUtil.ANONYMOUS_USERID);
                return "redirect:/reglogin";      //重定向登录页面
            }
            comment.setCreatedDate(new Date());
            comment.setEntityType(0);       //0为问题评论 1为评论回复
            comment.setEntityId(questionId);
            commentService.addComment(comment);

            int count = commentService.getCommentCount(comment.getEntityId(), comment.getEntityType());
            questionService.updateCommentCount(comment.getEntityId(), count);

            logger.info("commentContro getUserId:"+comment.getUserId());
            eventProducer.fireEvent(new EventModel(EventType.COMMENT).setActorId(comment.getUserId())
                    .setEntityId(questionId));
        }catch (Exception e){
            logger.error("增加评论失败 "+e.getMessage());
        }
        return "redirect:/question/"+questionId;
    }
}
