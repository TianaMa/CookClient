package com.demo.cook.utils.format;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {

    public static String format(Date date, String format){
        if (date==null){
            return "";
        }
        return new SimpleDateFormat(format).format(date);
    }
}
