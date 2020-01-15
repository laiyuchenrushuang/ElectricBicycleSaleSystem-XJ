package com.seatrend.xj.electricbicyclesalesystem.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ly on 2019/10/30 16:17
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class CheckPawUtil {

    /**
     *  长度是 6-16
     * @param s
     * @return
     */
    //豹哥要求
    public static boolean isSixPaw(String s) {
        String regEx = "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_!@#$%^&*`~()-+=]+$)(?![a-z0-9]+$)(?![a-z\\W_!@#$%^&*`~()-+=]+$)(?![0-9\\W_!@#$%^&*`~()-+=]+$)[a-zA-Z0-9\\W_!@#$%^&*`~()-+=]{8,16}$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }
}
