package com.module.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日期格式转换
 *
 * @author BD
 * @date 2021/12/14
 */
public class DateUtils {

    private DateUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 日期转换，将yyyy-MM-dd转为yyyy年MM月dd日
     *
     * @param str 传递的日期字符串
     */
    public static String stringToDate(String str) {
        Date parse;
        String dateString = str;
        try {
            parse = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(str);
            if(null != parse) {
                dateString = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault()).format(parse);
            }
        } catch (ParseException e) {
            dateString = str;
        }
        return dateString;
    }

    /**
     * 日期转换，将yyyy-MM-dd HH:mm:ss转为yyyy-MM-dd
     *
     * @param str 传递的日期字符串
     */
    public static String stringToDate1(String str) {
        Date parse;
        String dateString = str;
        try {
            parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(str);
            if(null != parse) {
                dateString = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(parse);
            }
        } catch (ParseException e) {
            dateString = str;
        }
        return dateString;
    }
}
