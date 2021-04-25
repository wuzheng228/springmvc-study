package com.zzspace.web;

import com.zzspace.pojo.Goods;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/my")
public class ConverterController {
    @RequestMapping("/converter")
    public String myConverter(@RequestParam("goods")Goods goods, HttpServletRequest req) {
        System.out.println("-------------------");
        req.setAttribute("goods", goods);
        return "showgoods";
    }
}
