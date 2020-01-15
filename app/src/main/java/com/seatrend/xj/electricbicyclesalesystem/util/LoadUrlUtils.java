package com.seatrend.xj.electricbicyclesalesystem.util;

import android.util.Log;

import com.seatrend.xj.electricbicyclesalesystem.common.Constants;

/**
 * Created by ly on 2019/11/6 9:56
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class LoadUrlUtils {
    /**
     *
     * //  http://11.121.35.182:8081/file/getPhoto?lsh=191025000002&zpzl=A1
     * load photo 的 url 拼接
     * @param path
     * @return
     */
    public static String loadurl(String path){
        path=AESUtils.encrypt(path);
        String baseUrl = SharedPreferencesUtils.getNetworkAddress();
        String finalUrl = baseUrl + Constants.Companion.getPHOTO_SHOW();
        Log.d("lylog ","load photo url ==== "+finalUrl+"?filePath="+path);
        return finalUrl+"?filePath="+path;
    }
}
