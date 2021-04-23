package webanoo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/servlet")
public class ServletController {
    @RequestMapping("/login")
    public String login(HttpSession session, HttpServletRequest rq) {
        session.setAttribute("skey", "session范围的值");
        rq.setAttribute("rkey", "request范围的值");
        return "login";
    }

    @RequestMapping("/user")
    public ModelAndView login() {
        User user = new User();
        user.setName("zzspace");
        user.setPassword("12345");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(user);
        modelAndView.setViewName("user");
        return modelAndView;
    }
}
