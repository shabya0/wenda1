package com.nowcoder.wenda.controller;

import com.nowcoder.wenda.model.HostHolder;
import com.nowcoder.wenda.model.Message;
import com.nowcoder.wenda.model.User;
import com.nowcoder.wenda.model.ViewObject;
import com.nowcoder.wenda.service.MessageService;
import com.nowcoder.wenda.service.UserService;
import com.nowcoder.wenda.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.ViewResolversBeanDefinitionParser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {
    @Autowired
    HostHolder hostHolder;

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    private static Logger logger = LoggerFactory.getLogger(CommentController.class);

    @RequestMapping(path = {"/msg/addMessage"},method = {RequestMethod.POST})
    @ResponseBody
    public String  addMessage(@RequestParam("toName") String toName,
                              @RequestParam("content") String content){
        try{
            if(hostHolder.getUser() == null){
                return WendaUtil.getJSONString(999, "未登录");
            }
            User user = userService.selectUserByName(toName);
            if(user == null)
                return WendaUtil.getJSONString(1,"用户不存在");
            if(user.getName().equals(hostHolder.getUser().getName()))
                return WendaUtil.getJSONString(1, "不能发送给自己");

            Message message = new Message();
            message.setCreatedDate(new Date());
            message.setFromId(hostHolder.getUser().getId());
            message.setToId(user.getId());
            message.setContent(content);

            messageService.addMessage(message);

            return WendaUtil.getJSONString(0);
//            return null;
        }catch (Exception e){
            logger.error("发送信息失败 ",e.getMessage());
            return WendaUtil.getJSONString(1,"发送失败");
        }
    }

    @RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET})
    public String getConversationList(Model model){
        if(hostHolder.getUser() == null){
            return "redirect:/reglogin";
        }
        int localUserId = hostHolder.getUser().getId();
        List<Message> conversationList = messageService.getConversationList(localUserId,0,10);
        List<ViewObject> conversations = new ArrayList<ViewObject>();
        for(Message message : conversationList){
            ViewObject vo = new ViewObject();
            Message message_lasted = messageService.getLastedByConversationId(message.getConversationId());        //获取最新的一条数据
            message.setContent(message_lasted.getContent());        //将最新的记录更新进去
            message.setCreatedDate(message_lasted.getCreatedDate());
            vo.set("message", message);
            int targetId =  message.getFromId() == localUserId? message.getToId() : message.getFromId();
            vo.set("user", userService.getUser(targetId));
            vo.set("unread",messageService.getConversationUnreadCount(localUserId,message.getConversationId()));
            conversations.add(vo);
        }
        model.addAttribute("conversations", conversations);
        return "letter";
    }

    @RequestMapping(path = {"/msg/detail"}, method = {RequestMethod.GET})
    public String getConversationDetail(Model model, @RequestParam("conversationId") String conversationId){
        try{
            List<Message> messageList = messageService.getConversationDetail(conversationId, 0, 10);
            List<ViewObject> messages = new ArrayList<ViewObject>();
            for(Message message: messageList){
                ViewObject vo = new ViewObject();
                vo.set("message",message);
                vo.set("user", userService.getUser(message.getFromId()));
                messages.add(vo);
            }
            model.addAttribute("messages", messages);
        }catch (Exception e){
            logger.error("跳转detail失败：",e.getMessage());
        }
        return "letterDetail";
    }

}
