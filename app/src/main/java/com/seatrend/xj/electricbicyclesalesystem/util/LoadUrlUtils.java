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
        Log.d("lylog ","http load photo url ==== "+finalUrl+"?filePath="+path);
        return finalUrl+"?filePath="+path;
    }

    /**
     * 获取表格的图片  原来是pdf  改成了jpg
     *
     * http://11.121.35.182:8081/file/getbikeCheckPdfBylsh?lx=65FAE00A3B2A0E233A5A665A6A2CDB4D&lsh=FDBFC7E5F877EE3417021C4B2911827A
     */

    public static String loadPdfUrl(String lsh,String url){
        String baseUrl = SharedPreferencesUtils.getNetworkAddress();
        String finalUrl = baseUrl + url;
        Log.d("lylog ","http load photo url ==== "+finalUrl+"?lsh="+AESUtils.encrypt(lsh));
        return finalUrl+"?lsh="+AESUtils.encrypt(lsh);
    }
}
