package com.demo.cook.utils.format;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {

    public static String format(Date date, String format){
        return new SimpleDateFormat(format).format(date);
    }
}
