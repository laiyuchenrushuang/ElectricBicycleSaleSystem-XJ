package com.seatrend.xj.electricbicyclesalesystem.util;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Spinner;

import com.seatrend.xj.electricbicyclesalesystem.common.Constants;
import com.seatrend.xj.electricbicyclesalesystem.common.Constants;

import java.util.ArrayList;

/**
 * Created by ly on 2019/10/29 15:21
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class CsysUtils {
    /**
     * 车身颜色 a,b,c  逗号隔开的形式提交,返回a,b,c || a,c ||b,c ||a,b || a || b || c
     */
    public static String getSpCommitString(Spinner a, Spinner b, Spinner c) {
        String x = TextUtils.isEmpty(a.getSelectedItem().toString()) ? "" : a.getSelectedItem().toString().split(":")[0];
        String y = TextUtils.isEmpty(b.getSelectedItem().toString()) ? "" : b.getSelectedItem().toString().split(":")[0];
        String z = TextUtils.isEmpty(c.getSelectedItem().toString()) ? "" : c.getSelectedItem().toString().split(":")[0];

        String result = x + "," + y + "," + z;
        String[] splist = result.split(",");
        ArrayList<String> rePL = new ArrayList<>();
        for (int i = 0; i < splist.length; i++) {
            //过滤
            if (!TextUtils.isEmpty(splist[i])) {
                rePL.add((splist[i]));
            }
        }
        if (rePL.size() > 1) {
            if (rePL.size() == 2) {
                return rePL.get(0) + "," + rePL.get(1);
            }
            if (rePL.size() == 3) {
                return rePL.get(0) + "," + rePL.get(1) + "," + rePL.get(2);
            }
        } else {
            return rePL.get(0);
        }
        return result;
    }

    /**
     * 根据"csys":"A,B,C"  获取成红白蓝
     *
     * @param csys
     * @return
     */
    public static String getCsysMc(String csys) {
        if (csys != null && csys.contains(",")) {
            String[] array = csys.split(",");
            StringBuffer sApend = new StringBuffer();
            for (String db : array) {
                String enity = DMZUtils.getDMSM(Constants.Companion.getCSYS(), db);
                sApend.append(enity);
            }
            return sApend.toString();
        } else if (!TextUtils.isEmpty(csys)) {
            return DMZUtils.getDMSM(Constants.Companion.getCSYS(), csys);
        }

        return "";
    }
}
