package com.seatrend.xj.electricbicyclesalesystem.util;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

/**
 *这个是为了 书写内容是否为空进行判断
 */
public class CheckEditTxetUtils {
    /**
     * true 控件数据有效，反之
     * @param content
     * @return
     */

    public static boolean checkEditextValuable(TextView...content){
        for(TextView db : content){
            if(null == db.getText() || TextUtils.isEmpty(db.getText().toString())){
                return false;
            }
        }
        return true;
    }
}
