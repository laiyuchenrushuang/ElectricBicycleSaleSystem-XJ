package com.seatrend.xj.electricbicyclesalesystem.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.seatrend.xj.electricbicyclesalesystem.common.MyApplication;
import com.seatrend.xj.electricbicyclesalesystem.util.AppUtils;
import com.seatrend.xj.electricbicyclesalesystem.util.SharedPreferencesUtils;
import com.seatrend.xj.electricbicyclesalesystem.util.StringUtils;


/**
 * Created by ly on 2020/5/20 9:22
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //接收安装广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            String packageName = intent.getDataString();
            System.out.println("安装了1:" + packageName + "包名的程序");
        }
        //接收卸载广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            String packageName = intent.getDataString();
            System.out.println("卸载了:" + packageName + "包名的程序");
//            System.out.println("卸载了:" + (AppUtils.getPackageInfo(MyApplication.Companion.getMyApplicationContext()).packageName + "包名的程序"));
            if(packageName != null && packageName.contains(AppUtils.getPackageInfo(MyApplication.Companion.getMyApplicationContext()).packageName)){
                System.out.println("卸载重置");
                SharedPreferencesUtils.setIsFirst(true);
            }
        }
    }
}
