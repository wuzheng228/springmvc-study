package com.zzspace.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/index")
public class indexController {
    @RequestMapping("/login")
    public String login() {
        return "login"; // 跳转到/WEB-INF/jsp下的login.jsp
    }
    @RequestMapping("/register")
    public String register() {
        return "register";
    }
}
