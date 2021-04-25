package com.zzspace.web;

import com.zzspace.pojo.Goods;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class GoodController {
    @RequestMapping("/addgood")
    public String add(HttpServletRequest req, Goods goods) {
        req.setAttribute("goods", goods);
        return "showgoods";
    }
}
