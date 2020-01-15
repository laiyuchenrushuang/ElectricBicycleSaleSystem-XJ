package com.seatrend.xj.electricbicyclesalesystem.util;

import android.text.TextUtils;


public class YgStateUtil {

    /**
     * 员工账户的状态
     * 0正常,1禁用,2待审核 3审核不通过
     *
     * @param dmz
     * @return
     */
    public static String getDMSM(String dmz) {
        if ("0".equals(dmz)) {
            return "正常";
        }
        if ("1".equals(dmz)) {
            return "禁用";
        }
        if ("2".equals(dmz)) {
            return "待审核";
        }
        if ("3".equals(dmz)) {
            return "审核不通过";
        }
        return "";
    }
}
