package com.seatrend.xj.electricbicyclesalesystem.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.seatrend.xj.electricbicyclesalesystem.R;
import com.seatrend.xj.electricbicyclesalesystem.common.Constants;
import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteUtils;
import com.seatrend.xj.electricbicyclesalesystem.entity.CodeEntity;
import com.seatrend.xj.electricbicyclesalesystem.entity.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ly on 2019/10/30 14:09
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class SpinnerUtil {
    public static int INSERT_DATA = 0x1;

    /**
     * @param context 上下文
     * @param dataZl  查询的数据种类
     * @param spinner spiner控件
     */

//    public static void setPinnerData(Context context, String dataZl, Spinner spinner) {
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.my_simple_spinner_item);
//        adapter.setDropDownViewResource(R.layout.item_spinner__down_common);
//        List<CodeEntity.DataBean> dataList = CodeTableSQLiteUtils.queryByDMLB(dataZl);
//        for (CodeEntity.DataBean db : dataList) {
//            String dmz = db.getDmz().trim();
//            String dmsm1 = db.getDmsm1().trim();
//            adapter.add(dmz + ":" + dmsm1);
//        }
//        spinner.setAdapter(adapter);
//        if ("1".equals(Constants.Companion.getQH_CONFIG())) { // 新疆
//            OtherUtils.setSpinnerToDmz(Constants.Companion.getXIN_JIANG(), spinner);
//        } else if ("2".equals(Constants.Companion.getQH_CONFIG())) { //四川
//            OtherUtils.setSpinnerToDmz(Constants.Companion.getSI_CHUAN(), spinner);
//        }
//    }
    public static void setPinnerData(Context context, String dataZl, Spinner spinner) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.my_simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.item_spinner__down_common);
        List<CodeEntity.DataBean> dataList = CodeTableSQLiteUtils.queryByDMLB(dataZl);
        for (CodeEntity.DataBean db : dataList) {
            String dmz = db.getDmz().trim();
            String dmsm1 = db.getDmsm1().trim();
            adapter.add(dmz + ":" + dmsm1);
        }
        spinner.setAdapter(adapter);
    }

    public static void setPinnerDataQh(Context context, String dataZl, Spinner spinner, String qh) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.my_simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.item_spinner__down_common);
        List<CodeEntity.DataBean> dataList = CodeTableSQLiteUtils.queryByDMLB(dataZl);
        for (CodeEntity.DataBean db : dataList) {
            String dmsm1 = db.getDmsm1().trim();
            adapter.add(dmsm1);
        }
        spinner.setAdapter(adapter);
        if (qh == null || TextUtils.isEmpty(qh)) { // 新疆
            OtherUtils.setSpinner2Dmsm("新疆维吾尔自治区", spinner);
        } else {
            OtherUtils.setSpinner2Dmsm(CodeTableSQLiteUtils.queryByDmlbAndDmzGetDmsm(Constants.Companion.getXSQY(), qh), spinner);
        }
    }

    /**
     * true 控件数据有效，反之
     *
     * @param spinner
     * @return
     */

    public static boolean checkSpinnerValuable(Spinner... spinner) {
        for (Spinner db : spinner) {
            if (null == db.getSelectedItem() || TextUtils.isEmpty(db.getSelectedItem().toString())) {
                return false;
            }
        }
        return true;
    }

//    /**
//     * @param context
//     * @param flag    1 是新疆的  2 成都  其他 所有
//     * @param spinner
//     */
//    public static void setPinnerQHData(Context context, String flag, Spinner spinner) {
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.my_simple_spinner_item);
//        adapter.setDropDownViewResource(R.layout.item_spinner__down_common);
//        List<CodeEntity.DataBean> dataList;
//        if ("1".equals(flag)) {
//            dataList = getXjQhData();
//        } else if ("2".equals(flag)) {
//            dataList = getScQhData();
//        } else {
//            dataList = CodeTableSQLiteUtils.queryByDMLB(Constants.Companion.getXSQY());
//        }
//
//        for (CodeEntity.DataBean db : dataList) {
//            String dmz = db.getDmz().trim();
//            String dmsm1 = db.getDmsm1().trim();
//            adapter.add(dmz + ":" + dmsm1);
//        }
//        spinner.setAdapter(adapter);
//        if("1".equals(flag)){
//            OtherUtils.setSpinnerToDmz("650100",spinner);
//        }else if ("2".equals(flag)) {
//            OtherUtils.setSpinnerToDmz("510100",spinner);
//        }
//    }


    private static List<CodeEntity.DataBean> getXjQhData() {
        if (QHUtils.getXjCitys().size() > 0) {
            return QHUtils.getXjCitys();
        } else {
            return QHUtils.getXJOneLevelCity();
        }
    }

    private static List<CodeEntity.DataBean> getScQhData() {
        if (QHUtils.getScCitys().size() > 0) {
            return QHUtils.getScCitys();
        } else {
            return QHUtils.getSCOneLevelCity();
        }
    }


    public static void setPinnerQHData(Context context, String dmz1, Spinner spinner) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.my_simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.item_spinner__down_common);
        List<CodeEntity.DataBean> dataList = QHUtils.getAllOneLevelCitys(dmz1);
        for (CodeEntity.DataBean db : dataList) {
            String dmz = db.getDmz().trim();
            String dmsm1 = db.getDmsm1().trim();
            adapter.add(dmz + ":" + dmsm1);
        }
        spinner.setAdapter(adapter);
        if (Constants.Companion.getXIN_JIANG().equals(dmz1)) {
            OtherUtils.setSpinnerToDmz("650100", spinner);
        }
    }

    //
//    public static void setPinnerQHData(Context context, String dmz1,ArrayList<CodeEntity.DataBean> dataList, Spinner spinner) {
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.my_simple_spinner_item);
//        adapter.setDropDownViewResource(R.layout.item_spinner__down_common);
//        for (CodeEntity.DataBean db : dataList) {
//            String dmz = db.getDmz().trim();
//            String dmsm1 = db.getDmsm1().trim();
//            adapter.add(dmz + ":" + dmsm1);
//        }
//        spinner.setAdapter(adapter);
//        if(Constants.Companion.getXIN_JIANG().equals(dmz1)){
//            OtherUtils.setSpinnerToDmz("650100", spinner);
//        }
//    }
    public static void setPinnerQHData(Context context, String dmz1, ArrayList<CodeEntity.DataBean> dataList, Spinner spinner, Handler handler) {
        if (TextUtils.isEmpty(dmz1)) {
            Toast.makeText(context, "代码值为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String defaultQh = "";
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.my_simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.item_spinner__down_common);
        for (CodeEntity.DataBean db : dataList) {
            String dmsm1 = db.getDmsm1().trim();
            if (UserInfo.GLBM != null && UserInfo.GLBM.length() > 4 && !UserInfo.GLBM.substring(0, 4).equals("")) {
                if ((UserInfo.GLBM.substring(0, 4)+"00").equals(db.getDmz())) {
                    defaultQh = db.getDmsm1().trim();
                }
            }
            adapter.add(dmsm1);
        }
        spinner.setAdapter(adapter);
        Message msg = Message.obtain();
        msg.what = INSERT_DATA;
        msg.obj = spinner;
        handler.sendMessage(msg);

        //新疆的定制，ll说修改(2020-04-10)
        if (Constants.Companion.getXIN_JIANG().equals(dmz1)) {
            OtherUtils.setSpinner2Dmsm("乌鲁木齐市", spinner);
            //新疆定制去找 GLBM 下的区划 因为 现实是这个管理部门的生成规则 只知道新疆的所以抛个异常
            if(!"".equals(defaultQh)){
                try {
                    //去找发证机关城市，发证机关和GLBM 是有关系的，例如管理部门650000000000000000，前面4位加“00”，就是发证机关的城市区划代码值，找不到就锁定省第一个城市
                    OtherUtils.setSpinner2Dmsm(defaultQh, spinner);
                } catch (Exception e) {
                    //异常 就默认省级省会城市
                    OtherUtils.setSpinner2Dmsm("乌鲁木齐市", spinner);
                }
            }

        }
    }

    /**
     * 获取省初版
     */
    public static void setPinnerProvinceData(Context context, Spinner spinner) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.my_simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.item_spinner__down_common);

        List<CodeEntity.DataBean> dataList = QHUtils.getAllOneLevelProvince();

        for (CodeEntity.DataBean db : dataList) {
            String dmz = db.getDmz().trim();
            String dmsm1 = db.getDmsm1().trim();
            adapter.add(dmz + ":" + dmsm1);
        }
        spinner.setAdapter(adapter);
    }
}
