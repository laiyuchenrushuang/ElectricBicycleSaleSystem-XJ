package com.seatrend.xj.electricbicyclesalesystem.util;

import android.view.View;


public class ViewShowUtils {
    /**
     * 显示
     * @param vList
     */
    public static void showVisibleView(View...vList){
        for(View db : vList){
            db.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 不显示但占位
     * @param vList
     */
    public static void showInisibleView(View...vList){
        for(View db : vList){
            db.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 完全不显示
     * @param vList
     */
    public static void showGoneView(View...vList){
        for(View db : vList){
            db.setVisibility(View.GONE);
        }
    }
}
