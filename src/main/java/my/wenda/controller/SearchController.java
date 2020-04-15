package my.wenda.controller;

import my.wenda.model.EntityType;
import my.wenda.model.Question;
import my.wenda.model.ViewObject;
import my.wenda.service.FollowService;
import my.wenda.service.QuestionService;
import my.wenda.service.SearchService;
import my.wenda.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {
    @Autowired
    SearchService searchService;
    @Autowired
    QuestionService questionService;
    @Autowired
    FollowService followService;

    @Autowired
    UserService userService;

    private static Logger logger = LoggerFactory.getLogger(SearchController.class);

    @RequestMapping(path = {"/search"},method = {RequestMethod.GET})
    public String  search(Model model, @RequestParam("q") String keyword,
                              @RequestParam(value = "offset", defaultValue = "0") int offset,
                              @RequestParam(value = "count", defaultValue = "10") int count){
        try {
            if(keyword == "") return "redirect:/";
            List<Question> questionList = searchService.searQuestion(keyword, offset, count, "<em><font color=\"red\">", "</font></em>");
            List<ViewObject> vos = new ArrayList<ViewObject>();
            int len=setVos(questionList, vos);  //将查询到的数据放入vos容器中，并返回容器大小

            model.addAttribute("vos_len",len);
            model.addAttribute("vos", vos);
            model.addAttribute("keyword", keyword);
            model.addAttribute("title","搜索结果");
        }catch (Exception e){
            logger.error("搜索失败 "+e.getMessage());
        }
        return "result";
    }

    @RequestMapping(path = {"/searchMore"},method = {RequestMethod.GET,RequestMethod.POST})
    public String  searchMore(Model model, @RequestParam("q") String keyword,
                              @RequestParam(value = "offset") int offset,
                              @RequestParam(value = "count", defaultValue = "10") int count){
        try {
            List<Question> questionList = searchService.searQuestion(keyword, offset, count, "<em><font color=\"red\">", "</font></em>");
            List<ViewObject> vos = new ArrayList<ViewObject>();
            int len=setVos(questionList, vos);  //将查询到的数据放入vos容器中，并返回容器大小

            model.addAttribute("vos_len",len);
            model.addAttribute("vos", vos);
            model.addAttribute("keyword", keyword);
        }catch (Exception e){
            logger.error("搜索失败 "+e.getMessage());
        }
        return "resultMore";
    }

    //将查询到的数据放入vos容器中，并返回容器大小
    public int setVos(List<Question> questionList,List<ViewObject> vos){
        int len=0;
        for(Question question: questionList){
            Question q = questionService.selectQuestionById(question.getId());
            ViewObject vo = new ViewObject();
            if(question.getContent() != null){
                q.setContent(question.getContent());

            }
            if(question.getTitle() != null){
                q.setTitle(question.getTitle());
            }
            vo.set("question",q);
            vo.set("user",userService.getUser(q.getUserId()));
            vo.set("followCount", followService.getFollowerCount(EntityType.ENTITY_QUESTION, question.getId()));
            vos.add(vo);
            ++len;
        }
        return len;
    }
}
