package com.seatrend.xj.electricbicyclesalesystem.util;



import org.apache.commons.codec.digest.DigestUtils;

import java.net.URLDecoder;

/**
 * Created by ly on 2019/11/7 10:29
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class MD5Utils {

    /**
     *
     * String strUTF8 = URLDecoder.decode(str, "UTF-8");
     * @param str
     * @return
     */

    public static String getMd5Str(String str){
        return  DigestUtils.md5Hex(str);
    }
}
