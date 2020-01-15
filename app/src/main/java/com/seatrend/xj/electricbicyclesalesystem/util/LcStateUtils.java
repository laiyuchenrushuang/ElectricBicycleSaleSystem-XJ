package com.seatrend.xj.electricbicyclesalesystem.util;

import android.text.TextUtils;

/**
 * Created by ly on 2019/11/2 14:42
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class LcStateUtils {
    /**
     * 流程状态 0-查验未通过，1-已查验，2-已登记，3-已制证，E-已归档，Q-已退办
     *
     * @param dmz
     * @return
     */
    public static String getDMSM(String dmz) {
        if (TextUtils.isEmpty(dmz)) {
            return "";
        }
        if ("0".equals(dmz)) {
            return "查验未通过";
        }
        if ("1".equals(dmz)) {
            return "已查验";
        }
        if ("2".equals(dmz)) {
            return "已登记";
        }
        if ("3".equals(dmz)) {
            return "已制证";
        }
        if ("E".equals(dmz)) {
            return "已归档";
        }
        if ("Q".equals(dmz)) {
            return "已退办";
        }
        return "";
    }
}
