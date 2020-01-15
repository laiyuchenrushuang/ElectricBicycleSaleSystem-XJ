package com.seatrend.xj.electricbicyclesalesystem.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by ly on 2019/11/7 20:46
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class UTF8Utils {
    /**
     * @param str
     * @return
     */
    public String getUtf8Str(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
