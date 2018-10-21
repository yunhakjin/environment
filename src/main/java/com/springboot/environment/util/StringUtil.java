package com.springboot.environment.util;

import java.util.regex.Pattern;

/**
 * 自定义的字符串工具类。
 * 如果第三方包中带有相关的工具类，建议使用已有的工具类。
 */
public class StringUtil {


    public static boolean isInteger(String str){

        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
