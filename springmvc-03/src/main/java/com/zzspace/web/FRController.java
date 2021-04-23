package com.zzspace.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fr")
public class FRController {
    @RequestMapping("/login")
    public String login() {
        // 转发到一个视图
        return "login";
    }

    @RequestMapping("/register")
    public String register() {
        // 转发到一个请求方法, 同一个控制器可以省略”fr“
        return "forward:/fr/isRegister";
    }

    @RequestMapping("/isRegister")
    public String isRegister() {
        // 重定向一个请求方法
        return "redirect:/fr/login";
    }
}
