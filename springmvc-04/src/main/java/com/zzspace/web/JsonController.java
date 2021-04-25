package com.zzspace.web;

import com.zzspace.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/json")
public class JsonController {
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String test(@RequestBody User user, Model model) {
        model.addAttribute(user);
        return "user";
    }

    @RequestMapping(value = "/user2", method = RequestMethod.POST)
    @ResponseBody
    public User test(@RequestBody User user) {
        return user;
    }
}
