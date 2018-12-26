package com.springboot.environment.util;

import java.util.List;
import java.util.Map;
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

    /**
     * 判断list是否为空
     * @param list
     * @return
     */
    public static boolean isNullOrEmpty(List list){
        if (list.size() == 0 || list == null){
            return true;
        }
        return false;
    }

    /**
     * 判断map是否为空
     * @param map
     * @return
     */
    public static boolean isNullOrEmpty(Map map){
        if (map.size() == 0 || map == null){
            return true;
        }
        return false;
    }

    /**
     * 将字符串转换为整型数字百分比的形式
     * @param string
     * @return
     */
    public static String convertStringToInt(String string) {
        float num = Float.parseFloat(string);
        int number = (int) num;
        return number + "%";
    }
}
