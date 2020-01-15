package com.seatrend.xj.electricbicyclesalesystem.util;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Spinner;

/**
 * Created by ly on 2019/9/26 17:16
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class CsysCompareSameUtils {
    public static boolean compare2(Spinner sp1, Spinner sp2) {

        if (!TextUtils.isEmpty(sp1.getSelectedItem().toString()) && sp1.getSelectedItem().toString().equals(sp2.getSelectedItem().toString())) {
            return false;
        }
        if (!TextUtils.isEmpty(sp2.getSelectedItem().toString()) && sp2.getSelectedItem().toString().equals(sp1.getSelectedItem().toString())) {
            return false;
        }
        return true;
    }

    public static boolean compare3(Spinner sp1, Spinner sp2, Spinner sp3) {
        if (TextUtils.isEmpty(sp1.getSelectedItem().toString()) && TextUtils.isEmpty(sp2.getSelectedItem().toString())&& TextUtils.isEmpty(sp3.getSelectedItem().toString())) {
            return false;
        }
        if (!compare2(sp1, sp3) || !compare2(sp1, sp2) || !compare2(sp2, sp3)) {
            return false;
        }
        return true;
    }
}
