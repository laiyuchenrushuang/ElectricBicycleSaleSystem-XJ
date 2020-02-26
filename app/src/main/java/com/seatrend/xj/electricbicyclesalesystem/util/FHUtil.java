package com.seatrend.xj.electricbicyclesalesystem.util;

import android.text.TextUtils;

import com.seatrend.xj.electricbicyclesalesystem.entity.FHEnity;
import com.seatrend.xj.electricbicyclesalesystem.entity.FHEnity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by ly on 2019/11/1 15:50
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class FHUtil {


    /**
     * 复核标记 9-无需复核，0-待复核，1-复核通过，2-复核未通过   null 也是待复核
     *
     * @param dmz
     * @return
     */
    public static String getDMSM(String dmz) {
        if (TextUtils.isEmpty(dmz)) {
            return "待复核";
        }
        if ("0".equals(dmz)) {
            return "待复核";
        }
        if ("1".equals(dmz)) {
            return "复核通过";
        }
        if ("2".equals(dmz)) {
            return "复核未通过";
        }
        return "无需复核";
    }

    public static ArrayList<FHEnity.Data.FHList> getSortList(ArrayList<FHEnity.Data.FHList> data) {
        Collections.sort(data, mMyFhztCompare);
        return data;
    }

    //    private static Comparator<? super FHEnity.Data.FHList> mMyTimeCompare = new Comparator<FHEnity.Data.FHList>() {
//        @Override
//        public int compare(FHEnity.Data.FHList t1, FHEnity.Data.FHList t2) {
//
//            return (int) (t2.getFhsj() - t1.getFhsj());
//        }
//    };
    private static Comparator<? super FHEnity.Data.FHList> mMyFhztCompare = new Comparator<FHEnity.Data.FHList>() {
        @Override
        public int compare(FHEnity.Data.FHList t1, FHEnity.Data.FHList t2) {

            if ((Integer.valueOf(t1.getFhbj()) - Integer.valueOf(t2.getFhbj())) < 0) {
                return -1;
            } else if ((Integer.valueOf(t1.getFhbj()) - Integer.valueOf(t2.getFhbj())) > 0) {
                return 0;
            } else {
                return (int) (t2.getFhsj() - t1.getFhsj());
            }
        }
    };
}
