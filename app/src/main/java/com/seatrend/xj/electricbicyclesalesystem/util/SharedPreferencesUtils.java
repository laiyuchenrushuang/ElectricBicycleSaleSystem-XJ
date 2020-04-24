package com.seatrend.xj.electricbicyclesalesystem.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import com.seatrend.xj.electricbicyclesalesystem.common.Constants;
import com.seatrend.xj.electricbicyclesalesystem.common.MyApplication;
import com.seatrend.xj.electricbicyclesalesystem.common.Constants;
import com.seatrend.xj.electricbicyclesalesystem.common.MyApplication;

import java.util.Objects;

import static com.seatrend.xj.electricbicyclesalesystem.common.Constants.*;


/**
 * Created by seatrend on 2018/8/22.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class SharedPreferencesUtils{
    public static void setIsFirst(boolean isfirst){
        Objects.requireNonNull(MyApplication.Companion.getMyApplicationContext()).getSharedPreferences(Constants.Companion.getSETTING(), Context.MODE_PRIVATE)
                .edit().putBoolean(Constants.Companion.getIS_FIRST(),isfirst).apply();
    }
    public static boolean getIsFirst(){
        return Objects.requireNonNull(MyApplication.Companion.getMyApplicationContext()).getSharedPreferences(Constants.Companion.getSETTING(), Context.MODE_PRIVATE)
                .getBoolean(Constants.Companion.getIS_FIRST(), true);
    }

    public static void setIpAddress(String ip){
        Objects.requireNonNull(MyApplication.Companion.getMyApplicationContext()).getSharedPreferences(Constants.Companion.getSETTING(), Context.MODE_PRIVATE)
                .edit().putString(Constants.Companion.getIP_K(),ip).apply();
    }
    public static String getIpAddress(){
      return   Objects.requireNonNull(MyApplication.Companion.getMyApplicationContext()).getSharedPreferences(Constants.Companion.getSETTING(), Context.MODE_PRIVATE)
               .getString(Constants.Companion.getIP_K(),"192.168.0.46");
    }
    public static String getNetworkAddress(){
      return   Objects.requireNonNull(MyApplication.Companion.getMyApplicationContext()).getSharedPreferences(Constants.Companion.getSETTING(), Context.MODE_PRIVATE)
               .getString(Constants.Companion.getNET_K(),"http://220.171.43.76:82/ddc");
    }
    public static void setNetworkAddress(String network){
        Objects.requireNonNull(MyApplication.Companion.getMyApplicationContext()).getSharedPreferences(Constants.Companion.getSETTING(), Context.MODE_PRIVATE)
                .edit().putString(Constants.Companion.getNET_K(),network).apply();
    }

    public static void setPort(String port){
        Objects.requireNonNull(MyApplication.Companion.getMyApplicationContext()).getSharedPreferences(Constants.Companion.getSETTING(), Context.MODE_PRIVATE)
                .edit().putString(Constants.Companion.getPORT_K(),port).apply();
    }
    public static String getPort(){
       return Objects.requireNonNull(MyApplication.Companion.getMyApplicationContext()).getSharedPreferences(Constants.Companion.getSETTING(), Context.MODE_PRIVATE)
               .getString(Constants.Companion.getPORT_K(),"8080");
    }

    public static void setSynCode(boolean b){
        Objects.requireNonNull(MyApplication.Companion.getMyApplicationContext()).getSharedPreferences(Constants.Companion.getSETTING(), Context.MODE_PRIVATE)
                .edit().putBoolean(Constants.Companion.getSYN_CODE(),b).apply();
    }
    public static boolean getSynCode(){
        return Objects.requireNonNull(MyApplication.Companion.getMyApplicationContext()).getSharedPreferences(Constants.Companion.getSETTING(), Context.MODE_PRIVATE)
                .getBoolean(Constants.Companion.getSYN_CODE(),false);
    }
    public static void setAdmain(String admain){
        Objects.requireNonNull(MyApplication.Companion.getMyApplicationContext()).getSharedPreferences(Constants.Companion.getSETTING(), Context.MODE_PRIVATE)
                .edit().putString(Constants.Companion.getADMAIN(),admain).apply();
    }
    public static String getAdmain(){
        return Objects.requireNonNull(MyApplication.Companion.getMyApplicationContext()).getSharedPreferences(Constants.Companion.getSETTING(), Context.MODE_PRIVATE)
                .getString(Constants.Companion.getADMAIN(),"system");
    }

    public static void setTjqssj(String time){
        Objects.requireNonNull(MyApplication.Companion.getMyApplicationContext()).getSharedPreferences(Constants.Companion.getSETTING(), Context.MODE_PRIVATE)
                .edit().putString(Constants.Companion.getTJQSSJ(),time).apply();
    }
    public static String getTjqssj(){
        return Objects.requireNonNull(MyApplication.Companion.getMyApplicationContext()).getSharedPreferences(Constants.Companion.getSETTING(), Context.MODE_PRIVATE)
                .getString(Constants.Companion.getTJQSSJ(),StringUtils.longToStringDataNoHour(System.currentTimeMillis()));
    }

    public static void setTjzzsj(String time){
        Objects.requireNonNull(MyApplication.Companion.getMyApplicationContext()).getSharedPreferences(Constants.Companion.getSETTING(), Context.MODE_PRIVATE)
                .edit().putString(Constants.Companion.getTJZZSJ(),time).apply();
    }
    public static String getTjzzsj(){
        return Objects.requireNonNull(MyApplication.Companion.getMyApplicationContext()).getSharedPreferences(Constants.Companion.getSETTING(), Context.MODE_PRIVATE)
                .getString(Constants.Companion.getTJZZSJ(),StringUtils.longToStringDataNoHour(System.currentTimeMillis()));
    }

    public static void  setVesionTime(String time){
         Objects.requireNonNull(MyApplication.Companion.getMyApplicationContext()).getSharedPreferences(Constants.Companion.getSETTING(), Context.MODE_PRIVATE)
                .edit().putString(Constants.Companion.getVEISION_TIME(),time).apply();
    }
    public static String getVesionTime(){
        return Objects.requireNonNull(MyApplication.Companion.getMyApplicationContext()).getSharedPreferences(Constants.Companion.getSETTING(), Context.MODE_PRIVATE)
                .getString(Constants.Companion.getVEISION_TIME(),Constants.Companion.getUPDATA_TIME());
    }
}
