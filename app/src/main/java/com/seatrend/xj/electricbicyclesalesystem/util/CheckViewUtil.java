package com.seatrend.xj.electricbicyclesalesystem.util;

import android.view.View;

/**
 * Created by ly on 2019/11/22 17:00
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class CheckViewUtil {

    // true ，是有可见的部分，false 都不可见  只针对GONE VISIBLE
    public static boolean isShow(View...vList){
        for(View view : vList){
            if(view.getVisibility() != View.GONE){
                return true;
            }
        }
        return  false;
    }
}
