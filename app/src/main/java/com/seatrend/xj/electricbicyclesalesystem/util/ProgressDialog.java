package com.seatrend.xj.electricbicyclesalesystem.util;

import android.app.Dialog;
import android.content.Context;

import com.seatrend.xj.electricbicyclesalesystem.R;

/**
 * Created by ly on 2019/9/30 13:42
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class ProgressDialog {
    public static Dialog getProgressDialog(Context context){
        try{
            final Dialog mDialog = new Dialog(context);
            mDialog.setContentView(R.layout.dialog_progress);
            mDialog.setCanceledOnTouchOutside(false);
            return mDialog;
        }catch (Exception e){
            e.printStackTrace();
            return null;

        }
    }
}
