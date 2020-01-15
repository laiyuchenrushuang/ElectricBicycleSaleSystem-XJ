package com.seatrend.xj.electricbicyclesalesystem.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ly on 2019/11/5 10:25
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class ParseQcodeUtil {
    /**
     * http://mv.cqccms.com.cn/incoc/GSViewEbike!viewCocEbike.action?vinCode=117321900000243
     * 截取 zcbm字段
     *
     * @param url
     * @return
     */
    public static String getZcbmString(String url) {
        String regEx = "vinCode=([0-9]{15})";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(url);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }

    /**
     * true 是15位数字，正确整车编码
     * @param url
     * @return
     */
    public static boolean isZcbmString(String url) {
        String regEx = "\\d{15}";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(url);
        if (m.find()) {
            return m.matches();
        }
        return false;
    }
}
