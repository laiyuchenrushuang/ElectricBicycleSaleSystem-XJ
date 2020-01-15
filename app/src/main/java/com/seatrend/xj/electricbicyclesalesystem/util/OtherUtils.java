package com.seatrend.xj.electricbicyclesalesystem.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteUtils;
import com.seatrend.xj.electricbicyclesalesystem.entity.CodeEntity;
import com.seatrend.xj.electricbicyclesalesystem.entity.PhotoTypeEntity;
import com.seatrend.xj.electricbicyclesalesystem.entity.PhotoTypeEntity;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by seatrend on 2018/12/3.
 */

public class OtherUtils {

    /**
     * 设置NumberPicker分割线颜色
     *
     * @param numberPicker：NumberPicker
     * @param color：int
     */
    public static void setNumberPickerDividerColor(NumberPicker numberPicker, int color) {
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field SelectionDividerField : pickerFields) {
            if (SelectionDividerField.getName().equals("mSelectionDivider")) {
                SelectionDividerField.setAccessible(true);
                try {
                    SelectionDividerField.set(numberPicker, new ColorDrawable(color));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public static String getSystemProperty() {

        try {
            Class build = Class.forName("android.os.Build");
            String customName = (String) build.getDeclaredField("PWV_CUSTOM_CUSTOM").get(null);
            return customName;
        } catch (Exception e) {
            e.printStackTrace();
            return "M";
        }

    }

    /**
     * spinner 设置为指定数据
     *
     * @param dmz
     * @param spinner
     */
    public static void setSpinnerToDmz(String dmz, Spinner spinner) {
        if (TextUtils.isEmpty(dmz)) {
            return;
        }
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            String item = adapter.getItem(i);
            String adapterDmz = item.split(":")[0];
            if (dmz.equals(adapterDmz)) {
                spinner.setSelection(i);
                break;
            }
        }

    }

    /**
     * spinner 设置为指定数据
     *
     * @param dmz
     * @param spinner
     */
    public static void setSpinner2Dmz(String dmz, Spinner spinner) {
        if (TextUtils.isEmpty(dmz)) {
            return;
        }
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            String item = adapter.getItem(i);
            if (dmz.equals(item)) {
                spinner.setSelection(i);
                break;
            }
        }

    }

    /**
     * spinner 设置为指定数据
     *
     * @param dmsm
     * @param spinner
     */
    public static void setSpinner2Dmsm(String dmsm, Spinner spinner) {
        if (TextUtils.isEmpty(dmsm)) {
            return;
        }
//        List<CodeEntity.DataBean> dataList = CodeTableSQLiteUtils.queryByDMLB(dataZl);
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            String item = adapter.getItem(i);
            if (dmsm.equals(item)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    /**
     * spinner 设置为指定数据
     *
     * @param dmsm
     * @param spinner
     */
    public static void setSpinnerToDmsm(String dmsm, Spinner spinner) {
        if (TextUtils.isEmpty(dmsm)) {
            return;
        }
//        List<CodeEntity.DataBean> dataList = CodeTableSQLiteUtils.queryByDMLB(dataZl);
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
        int count = adapter.getCount();
        Log.d("lylog", " aa = " + dmsm + "   count = " + count);
        for (int i = 0; i < count; i++) {  //因为第一个 为“”
            if (adapter.getItem(i) != null && adapter.getItem(i).split(":").length > 1) {
                String item = adapter.getItem(i).split(":")[1];
                if (dmsm.equals(item)) {
                    spinner.setSelection(i);
                    break;
                }
            }
        }
    }

    public static void goFaceComparePlugin(Activity activity, byte[] photo, int code) {
        Toast.makeText(activity, "正在开起人脸识别，请稍后...", Toast.LENGTH_SHORT).show();
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setComponent(new ComponentName("com.seatrend.xj.hongruanfacecompare", "com.seatrend.cd.hongruanfacecompare.MainActivity"));
            intent.putExtra("photo", photo);
            activity.startActivityForResult(intent, code);
        } catch (Exception e) {
            Toast.makeText(activity, "未找到人脸识别插件，请先安装插件", Toast.LENGTH_SHORT).show();

        }

    }

    /**
     * 将对象 的属性 转为map
     *
     * @param obj
     * @return
     */
    public static Map<String, String> objcetToMap(Object obj) {
        Map<String, String> map = new HashMap<>();
        // 得到类对象
        Class userCla = obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true); // 设置些属性是可以访问的
            try {
                Object val = f.get(obj);
                // 得到此属性的值
                if (!TextUtils.isEmpty((String) val)) {
                    map.put(f.getName(), val + "");// 设置键值
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        return map;
    }

    /**
     * 截取指定view 的视图
     *
     * @param v
     * @return
     */
    public static Bitmap getViewBp(View v) {
        if (null == v) {
            return null;
        }
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        if (Build.VERSION.SDK_INT >= 11) {
            v.measure(View.MeasureSpec.makeMeasureSpec(v.getWidth(),
                    View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(
                    v.getHeight(), View.MeasureSpec.EXACTLY));
            v.layout((int) v.getX(), (int) v.getY(),
                    (int) v.getX() + v.getMeasuredWidth(),
                    (int) v.getY() + v.getMeasuredHeight());
        } else {
            v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        }
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache(), 0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

        v.setDrawingCacheEnabled(false);
        v.destroyDrawingCache();
        return b;
    }


    /**
     * 截取activity 的视图
     *
     * @param ctx
     * @return
     */
    public static Bitmap shotActivity(Activity ctx) {

        View view = ctx.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        Bitmap bp = Bitmap.createBitmap(view.getDrawingCache(), 0, 0, view.getMeasuredWidth(),
                view.getMeasuredHeight());

        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();

        return bp;
    }

    /**
     * 删除某个文件夹下 的照片
     *
     * @param path
     */
    public static void deleteFileChild(String path) {
        try {
            File catalog = new File(path);
            if (catalog.exists()) {
                File[] files = catalog.listFiles();
                for (File f : files) {
                    if (f.getName().contains(".jpg")) {
                        f.delete();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void toVin(Activity activity, int requestCode_vin, String lsh) {
        try {
            if (lsh == null || lsh.length() == 0) {
                lsh = System.currentTimeMillis() + "";
            }
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setComponent(new ComponentName("com.seatrend.vin.app", "com.seatrend.vin.app.RequestAction"));
            intent.putExtra("cylsh", lsh);
            activity.startActivityForResult(intent, requestCode_vin);
        } catch (Exception io) {
            Toast.makeText(activity, "请先安装VIN插件", Toast.LENGTH_SHORT).show();
        }
    }

    public static ArrayList<PhotoTypeEntity.DataBean.ConfigBean> combinationPhoto(PhotoTypeEntity photoTypeEntity) {
        ArrayList<PhotoTypeEntity.DataBean.ConfigBean> allPhoto = new ArrayList<>();

        PhotoTypeEntity.DataBean photoTypeEntityData = photoTypeEntity.getData();
        if (photoTypeEntityData == null) {
            return allPhoto;
        }
        List<PhotoTypeEntity.DataBean.ConfigBean> ownerConfig = photoTypeEntityData.getOwnerConfig();
        List<PhotoTypeEntity.DataBean.ConfigBean> insuranceConfig = photoTypeEntityData.getInsuranceConfig();
        List<PhotoTypeEntity.DataBean.ConfigBean> originConfig = photoTypeEntityData.getOriginConfig();
        List<PhotoTypeEntity.DataBean.ConfigBean> agentConfig = photoTypeEntityData.getAgentConfig();
        List<PhotoTypeEntity.DataBean.ConfigBean> config = photoTypeEntityData.getConfig();

        if (ownerConfig != null) {
            allPhoto.addAll(ownerConfig);
        }
        if (insuranceConfig != null) {
            allPhoto.addAll(insuranceConfig);
        }
        if (originConfig != null) {
            allPhoto.addAll(originConfig);
        }
        if (agentConfig != null) {
            allPhoto.addAll(agentConfig);
        }
        if (config != null) {
            allPhoto.addAll(config);
        }
        return allPhoto;
    }
}
