package com.seatrend.xj.electricbicyclesalesystem.util;

/**
 * Created by ly on 2019/11/5 13:05
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class JslxUtils {
    /**
     *   1 车管部门 2 服务站 3 经销商
     *
     * @param dmz
     * @return
     */
    public static String getJslxMc(String dmz){
        if("1".equals(dmz)){
            return "车管部门";
        }

        if("2".equals(dmz)){
            return "服务站";
        }

        if("3".equals(dmz)){
            return "经销商";
        }
        return null;
    }
}
