package my.wenda.controller;

import my.wenda.model.User;
import my.wenda.service.WendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Controller
public class IndexController {

    @Autowired
    WendaService wendaService;

    @RequestMapping(path={"/wenda1","/"})
    @ResponseBody       //返回字符串
    public String index(HttpSession httpSession){
        return "Hello Shabi, " + wendaService.getMessage(1) + "<br>" + httpSession.getAttribute("msg");
    }

    @RequestMapping(value = "/profile/{groupId}/{userId}")
    @ResponseBody
    public String profile(@PathVariable("groupId") String groupId,
                          @PathVariable("userId") int userId,
                          @RequestParam(value = "type", defaultValue = "1") int type,
                          @RequestParam(value = "key", defaultValue = "nowcoder", required=false) String key) {
        return String.format("%s, %d, %d, %s", groupId, userId, type, key);
    }


    @RequestMapping(path={"/home"}, method = {RequestMethod.GET})
    public String template() {
        return "home";
    }

    @RequestMapping(path={"/vm"}, method ={RequestMethod.GET})
    public String templates(Model model){
        model.addAttribute("value1","vvvvv1");
        List<String> colors = Arrays.asList(new String[]{"RED","WHITE","GREEN"});
        model.addAttribute("colors",colors);

        Map<String, String> map = new HashMap<>();
        for(int i=0;i<4;++i){
            map.put(String.valueOf(i),String.valueOf(i*i));
        }
        model.addAttribute("map",map);

        User user = new User("shabi");
        model.addAttribute("user",user);
        return "vm";
    }

    @RequestMapping(path={"/request"},method = {RequestMethod.GET})
    @ResponseBody
    public String request(Model model, HttpServletRequest request,
                           HttpServletResponse response,
                           HttpSession session){
        StringBuffer sb = new StringBuffer();
        sb.append(request.getMethod()+ "<br> ");
        sb.append(request.getQueryString()+"<br>");
        sb.append(request.getPathInfo()+"<br>");
        sb.append(request.getRequestURI()+"<br>");
        sb.append("shabi");
        return sb.toString();
    }

    @RequestMapping(path={"/redirect/{code}"},method = {RequestMethod.GET})
    public RedirectView redirect(@PathVariable("code") int code,
                                 HttpSession httpSession){
        httpSession.setAttribute("msg","Jump from redirect");
        RedirectView red = new RedirectView("/", true);
        if(code == 301){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;
    }

    @RequestMapping(path={"/admin"}, method ={RequestMethod.GET})
    @ResponseBody
    public String admin(@RequestParam("key") String key){
        if("admin".equals(key)){
            return "hello admin";
        }
        throw new IllegalArgumentException("参数不对");
    }

    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e){
        return "error:"+e.getMessage();
    }
}