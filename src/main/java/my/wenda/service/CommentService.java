package my.wenda.service;

import my.wenda.dao.CommentDAO;
import my.wenda.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentDAO commentDAO;

    @Autowired
    SensitiveService sensitiveService;      //敏感词
    public List<Comment> getCommentsByEntity(int entityId, int entityType){
        return commentDAO.selectCommentByzentity(entityId, entityType);
    }

    public int addComment(Comment comment){
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));     //去掉html标签
        comment.setContent(sensitiveService.filter(comment.getContent()));      //敏感词过滤
        return commentDAO.addComment(comment);
    }

    public boolean deleteComment(int commentId){
        return commentDAO.updateStatus(commentId, 1)>0;
    }

    public int getCommentCount(int entityId, int entityType){
        return commentDAO.getCommentCount(entityId, entityType);
    }

    public int getUserCommentCount(int userId) {
        return commentDAO.getUserCommentCount(userId);
    }


    public Comment getCommentById(int id){
        return commentDAO.getCommentById(id);
    }
}
