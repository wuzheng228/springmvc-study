package com.zzspace.formatter;

import com.zzspace.pojo.MyDate;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter implements Formatter<MyDate> {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public MyDate parse(String s, Locale locale) throws ParseException {
        return new MyDate(dateFormat.parse(s));
    }

    @Override
    public String print(MyDate date, Locale locale) {
        return dateFormat.format(date.getDate());
    }
}
