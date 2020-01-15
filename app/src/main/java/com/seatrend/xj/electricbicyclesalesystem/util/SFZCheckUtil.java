package com.seatrend.xj.electricbicyclesalesystem.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ly on 2019/11/25 14:34
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class SFZCheckUtil {

    //true 就是正常的身份证号码
    public static boolean isCorrect(String sfz){
        String regx = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(sfz);
        return matcher.matches();
    }
}
