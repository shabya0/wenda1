package my.wenda.controller;


import my.wenda.async.EventModel;
import my.wenda.async.EventProducer;
import my.wenda.async.EventType;
import my.wenda.model.Comment;
import my.wenda.model.EntityType;
import my.wenda.model.HostHolder;
import my.wenda.service.CommentService;
import my.wenda.service.LikeService;
import my.wenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LikeController {
    @Autowired
    LikeService likeService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(path = {"/like"}, method = {RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId){
        if(hostHolder.getUser() == null){
            return WendaUtil.getJSONString(999);
        }

        Comment comment = commentService.getCommentById(commentId);
        if(comment.getUserId() != hostHolder.getUser().getId())     //当点赞的评论为自己的时，不发送通知
            eventProducer.fireEvent(new EventModel(EventType.LIKE)
                    .setActorId(hostHolder.getUser().getId()).setEntityId(commentId)
                    .setEntityType(EntityType.ENTITY_COMMENT).setEntityOwnerId(comment.getUserId())
                    .setExt("questionId",String.valueOf(comment.getEntityId())));    //调用handler

        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return WendaUtil.getJSONString(0, String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.POST})
    @ResponseBody
    public String dislike(@RequestParam("commentId") int commentId){
        if(hostHolder.getUser() == null){
            return WendaUtil.getJSONString(999);
        }
        long dislikeCount = likeService.dislike(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return WendaUtil.getJSONString(0, String.valueOf(dislikeCount));
    }
}
