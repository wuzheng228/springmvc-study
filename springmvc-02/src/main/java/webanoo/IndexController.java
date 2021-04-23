package webanoo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class IndexController {
    @RequestMapping("/index/login")
    public String indexLogin(Model model) {
        /*在视图中可以使用EL表达式${success}取出model中的值*/
        model.addAttribute("success", "注册成功");
        return "login";
    }

}
