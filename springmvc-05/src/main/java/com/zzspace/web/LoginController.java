package com.zzspace.web;

import com.zzspace.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController {

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/login")
    public String login(@Valid User user, BindingResult result, Model model, HttpSession session) {
        if(result.hasErrors()){
            for (ObjectError error : result.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
        }
        if (user.getName().equals("zz") && user.getPswd().equals("123")) {
            model.addAttribute(user);
            session.setAttribute("user", user);
            return "main";
        } else {
            model.addAttribute("msg", "用户名密码错误");
            return "login";
        }
    }

    @RequestMapping("/logOut")
    public String logOut(HttpSession session) {
        session.removeAttribute("user");
        return "login";
    }

    @RequestMapping("toMain")
    public String toMain() {
        return "main";
    }
}
