package my.wenda.service;

import my.wenda.dao.QuestionDAO;
import my.wenda.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDAO questionDAO;

    @Autowired
    SensitiveService sensitiveService;
    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    //添加问题
    public int addQuestion(Question question){
        //转义特殊字符/<>\deng，避免用户输入代码被浏览器其读取执行
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        // 敏感词过滤
        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));

        int res_count = questionDAO.addQuestion(question);      //返回受影响行数
        return res_count >0 ?question.getId() : 0;      //返回插入问题得到的id
    }

    //返回用户为userid的最新limit条问题
    public List<Question> getLastQuestions(int userId, int offset, int limit){
        return questionDAO.selectLatestQuestions(userId, offset, limit);
    }

    //返回问题
    public  Question selectQuestionById(int id){
        return questionDAO.selectQuestionById(id);
    }

    public int updateCommentCount(int id, int commentCount){
        return questionDAO.updateCommentCount(id, commentCount);
    }

}
