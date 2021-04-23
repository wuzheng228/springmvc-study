package com.zzspace.web;

import com.zzspace.pojo.User;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public String login(User user, HttpSession session, Model model) {
//        if ("zz".equals(user.getUname())
//                && "123".equals(user.getUpass())) {
//            session.setAttribute("u", user.getUname());
//            return "status"; // 登录成功，跳转到 status.jsp
//        } else {
//            model.addAttribute("message", "用户名或密码错误");
//            return "login";
//        }
//    }
//
//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    public String register(User user, HttpSession session, Model model) {
//        if ("zz".equals(user.getUname())
//                && "123".equals(user.getUpass())) {
//            session.setAttribute("u", user.getUname());
//            return "login";
//        } else {
//            model.addAttribute("message", user.getUname());
//            return "register";
//        }
//    }

//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public String login(String uname, String upass, HttpSession session, Model model) {
//        System.out.println("---->" + uname);
//        if ("zz".equals(uname)
//                && "123".equals(upass)) {
//            session.setAttribute("u", uname);
//            return "status"; // 登录成功，跳转到 status.jsp
//        } else {
//            model.addAttribute("message", "用户名或密码错误");
//            return "login";
//        }
//    }
//
//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    public String register(@RequestParam("uname") String uname, String upass, HttpSession session, Model model) {
//        if ("zz".equals(uname)
//                && "123".equals(upass)) {
//            session.setAttribute("u", uname);
//            return "login";
//        } else {
//            model.addAttribute("message", uname);
//            return "register";
//        }
//    }

    @RequestMapping("/path/{id}")
    public String path(@PathVariable("id") int id, HttpSession session) {
        session.setAttribute("u", id);
        return "status";
    }

//    @RequestMapping("/login")
//    public String request(HttpServletRequest rq, HttpSession session) {
//        session.setAttribute("u", rq.getParameter("uname") + rq.getParameter("upass"));
//        return "status";
//    }

    @RequestMapping("/register")
    public String model(@ModelAttribute("user") User user) {
        return "status";
    }
}
