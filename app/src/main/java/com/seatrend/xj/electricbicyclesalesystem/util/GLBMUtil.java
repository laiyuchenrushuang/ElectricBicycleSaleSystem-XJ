package com.seatrend.xj.electricbicyclesalesystem.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ly on 2019/11/14 17:21
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class GLBMUtil {
    /**
     * true 包含字母
     * @param result
     * @return
     */
    public static Boolean isSearchLargerGlbm(String result) {
        String regEx = ".*[a-zA-Z]+.*";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(result);
        return matcher.matches();
    }
}
