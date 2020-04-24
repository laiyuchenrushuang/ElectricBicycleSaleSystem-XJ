package com.seatrend.xj.electricbicyclesalesystem.util;

import android.util.Log;

import com.seatrend.xj.electricbicyclesalesystem.common.Constants;
import com.seatrend.xj.electricbicyclesalesystem.common.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ly on 2019/11/5 16:00
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class CphmUtils {

    public static boolean checkValueCphm(String cphm) {
        String regx = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(cphm);
        return matcher.matches();
    }

    //新A A12345  true正确
    public static boolean checkXjValueCphm(String cphm) {
//        if ("1".equals(Constants.Companion.getQH_CONFIG())) { //新疆的
        String regEx = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领]{1}[A-Z]{1}[A-Z0-9]{6}$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(cphm);
        return matcher.matches();
//        }
//        return true;
    }
}
