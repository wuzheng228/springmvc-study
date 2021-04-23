package com.zzspace.web;

import com.zzspace.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/test")
public class TestController {

    @ModelAttribute
    public User before(String username) {
        System.out.println("before执行了");
        return new User(username,"123");
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String test() {
        return "status";
    }
}
