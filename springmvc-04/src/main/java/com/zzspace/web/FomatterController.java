package com.zzspace.web;

import com.zzspace.pojo.MyDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/fm")
public class FomatterController {
    @RequestMapping(value = "/date", method = RequestMethod.POST)
    public String convertDate(@RequestParam("date") MyDate date, Model model) {
        model.addAttribute(date);
        return "showdate";
    }
}
