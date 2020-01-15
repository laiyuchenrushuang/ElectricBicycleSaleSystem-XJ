package com.seatrend.xj.electricbicyclesalesystem.util;

import android.text.TextUtils;

/**
 * Created by ly on 2019/10/31 18:43
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class ObjectNullUtil {
    /**
     * true 都不为空
     *
     * false 为空
     * boolean也加入判断,是排除选项，就是不要的情况
     *
     * @param objects
     * @return
     */

    public static boolean checknull(Object... objects) {
        for (Object db : objects) {
            if (null == db || TextUtils.isEmpty(db.toString())) return false;

            if(db instanceof Boolean){
                if((Boolean) db){
                    return false;
                }
            }
        }
        return true;
    }
}
