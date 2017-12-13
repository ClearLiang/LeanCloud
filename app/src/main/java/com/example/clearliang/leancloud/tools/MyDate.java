package com.example.clearliang.leancloud.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dhht on 2017/12/13.
 */

public class MyDate {

    public static String getDate(){
        SimpleDateFormat formatter    =   new    SimpleDateFormat    ("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
        String    str    =    formatter.format(curDate);
        return str;
    }
}
