package com.seatrend.xj.electricbicyclesalesystem.util;

/**
 * Created by ly on 2019/11/5 16:41
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class CllxUtils {
    /**
     * 获取车辆名称  1 标准
     * @param dmz
     * @return
     */
    public static String getCllxDMSM(String dmz){
        if("1".equals(dmz)){
            return "标准电动自行车";
        }
        return "超标电动自行车";
    }
}
