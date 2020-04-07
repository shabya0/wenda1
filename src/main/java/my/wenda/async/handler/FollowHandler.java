package my.wenda.async.handler;

import my.wenda.async.EventHandler;
import my.wenda.async.EventModel;
import my.wenda.async.EventType;
import my.wenda.model.EntityType;
import my.wenda.model.Message;
import my.wenda.model.User;
import my.wenda.service.MessageService;
import my.wenda.service.QuestionService;
import my.wenda.service.UserService;
import my.wenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class FollowHandler implements EventHandler {
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user = userService.getUser(model.getActorId());
        if(model.getEntityType() == EntityType.ENTITY_QUESTION)
            message.setContent("用户 "+user.getName() +
                    "关注了你的问题 “"+ questionService.selectQuestionById(Integer.parseInt(model.getExt("questionId"))).getTitle() +"” ，localhost:8080/question/" + model.getExt("questionId"));
        else if(model.getEntityType() == EntityType.ENTITY_USER){
            message.setContent("用户 "+user.getName() +
                    " 关注了你，localhost:8080/user/"+model.getActorId());
        }
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }

}
