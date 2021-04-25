package com.zzspace.converter;

import com.zzspace.pojo.Goods;
import org.springframework.core.convert.converter.Converter;

public class GoodsConverter implements Converter<String, Goods> {
    @Override
    public Goods convert(String s) {
        System.out.println("convert------->");
        String[] strs = s.split(",");
        String name = strs[0];
        double price = Double.parseDouble(strs[1]);
        int number = Integer.parseInt(strs[2]);
        return new Goods(name, price, number);
    }
}
