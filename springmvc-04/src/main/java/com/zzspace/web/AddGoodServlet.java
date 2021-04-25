package com.zzspace.web;

import com.zzspace.pojo.Goods;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddGoodServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        // 设置编码，防止乱码
        req.setCharacterEncoding("utf-8");
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String number = req.getParameter("number");
        // 下面进行类型转换
        double newgoodsprice = Double.parseDouble(price);
        int newgoodsnumber = Integer.parseInt(number);
        // 将转换后的数据封装成goods值对象
        Goods goods = new Goods(name, newgoodsprice, newgoodsnumber);
        // 将goods值对象传递给数据访问层，进行添加操作，代码省略
        req.setAttribute("goods", goods);
        req.getRequestDispatcher("/WEB-INF/jsp/showgoods.jsp").forward(req,resp);
    }
}
