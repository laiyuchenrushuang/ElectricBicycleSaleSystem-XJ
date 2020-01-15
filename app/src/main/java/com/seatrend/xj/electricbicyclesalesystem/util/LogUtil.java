package com.seatrend.xj.electricbicyclesalesystem.util;

import android.util.Log;

/**
 * Created by ly on 2019/11/11 11:56
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class LogUtil {

    private static LogUtil mLogUtil = null;

    public static LogUtil getInstance() {
        if (mLogUtil == null) {
            synchronized (LogUtil.class) {
                if (mLogUtil == null) {
                    mLogUtil = new LogUtil();
                }
            }
        }
        return mLogUtil;
    }

    public void d(String msg) {
        Log.d("[lylog]", msg);
    }
}
