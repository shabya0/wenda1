package com.nowcoder.wenda.service;
import com.nowcoder.wenda.dao.MessageDAO;
import com.nowcoder.wenda.model.HostHolder;
import com.nowcoder.wenda.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageDAO messageDAO;

    @Autowired
    SensitiveService sensitiveService;

    @Autowired
    HostHolder hostHolder;

    public int addMessage(Message message){
        if(message.getContent() !=null)
            message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setContent(sensitiveService.filter(message.getContent()));
        return messageDAO.addMessage(message)>0? message.getId(): 0;
    }

    public Message getLastedByConversationId(String conversationId){
        return messageDAO.getLastedByConversationId(conversationId);
    }

    public List<Message> getConversationDetail(String conversationId, int offset, int limit){
        messageDAO.setStatus(conversationId, hostHolder.getUser().getId());       //设置has_read为1，标志为已读
        return messageDAO.getConversationDetail(conversationId, offset, limit);
    }

    public List<Message> getConversationList(int userId, int offset, int limit){
        return messageDAO.getConversationList(userId, offset, limit);
    }

    public int getConversationUnreadCount(int userId, String conversationId){
        return messageDAO.getConversationUnreadCount(userId, conversationId);
    }

}
