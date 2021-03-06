package com.seatrend.xj.electricbicyclesalesystem.util;

import android.util.Log;

import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteUtils;
import com.seatrend.xj.electricbicyclesalesystem.entity.CodeEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ly on 2019/11/6 11:13
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class QHUtils {

    private final static String DMLB = "0033"; //行政区划的代码类别

    private static ArrayList<CodeEntity.DataBean> xjCitys = new ArrayList<>();
    private static ArrayList<CodeEntity.DataBean> scCitys = new ArrayList<>();

    private static ArrayList<CodeEntity.DataBean> allProvinces = new ArrayList<>(); //所有省

    private static String cdRegx = "[5][1][0][1]\\d{2}"; // ChengDu OneLevelCity 正则表达式

    /**
     * 获取新疆的 所有城市 例如 （乌鲁木齐 伊犁）
     *
     * @return xx
     */
    public static ArrayList<CodeEntity.DataBean> getXJOneLevelCity() {
        //去找新疆的 代号65 00 00 (前两位是省，中间两位是城市，后两位是城市下的区县)
        List<CodeEntity.DataBean> list = CodeTableSQLiteUtils.queryByDMLB(DMLB); //获取所有的区划
        for (CodeEntity.DataBean db : list) {
            // XJOneLevelCity 正则表达式
            String xjRegx = "[6][5]\\d{1}[1-9][0][0]";
            Pattern p = Pattern.compile(xjRegx);
            Matcher m = p.matcher(db.getDmz());
            if (m.matches()) {
                xjCitys.add(db);
            }
        }
        return xjCitys;
    }

    /**
     * 获取四川的 所有城市 例如 （成都 泸州）
     *
     * @return
     */
    public static ArrayList<CodeEntity.DataBean> getSCOneLevelCity() {
        //去找四川的 代号51 00 00 (前两位是省，中间两位是城市，后两位是城市下的区县)
        List<CodeEntity.DataBean> list = CodeTableSQLiteUtils.queryByDMLB(DMLB); //获取所有的区划
        for (CodeEntity.DataBean db : list) {
            // SCOneLevelCity 正则表达式
            String scRegx = "[5][1]\\d{1}[1-9][0][0]";
            Pattern p = Pattern.compile(scRegx);
            Matcher m = p.matcher(db.getDmz());
            if (m.matches()) {
                scCitys.add(db);
            }
        }
        return scCitys;
    }

    /**
     * 获取所有省级单位 例如（四川 北京）
     *
     * @return
     */
    public static ArrayList<CodeEntity.DataBean> getAllOneLevelProvince() {
        //去找四川的 代号51 00 00 (前两位是省，中间两位是城市，后两位是城市下的区县)
        List<CodeEntity.DataBean> list = CodeTableSQLiteUtils.queryByDMLB(DMLB); //获取所有的区划
        for (CodeEntity.DataBean db : list) {
            String provinceRex = "\\d{2}[0][0][0][0]";
            Pattern p = Pattern.compile(provinceRex);
            Matcher m = p.matcher(db.getDmz());
            if (m.matches()) {
                allProvinces.add(db);
            }
        }
        return allProvinces;
    }

    /**
     * 获取所有省级单位下的城市 例如（成都，北京的东城区）
     *
     * @return
     */
    public static ArrayList<CodeEntity.DataBean> getAllOneLevelCitys(String provinDmz) {
        if (provinDmz == null || provinDmz.length() != 6) {
            return new ArrayList<>();
        }
        List<CodeEntity.DataBean> list = CodeTableSQLiteUtils.queryByDMLB(DMLB); //获取所有的区划
        ArrayList<CodeEntity.DataBean> allCitys = new ArrayList<>(); //所有省下的城市
        String newRex;
        String header1 = provinDmz.substring(0, 1);
        String header2 = provinDmz.substring(1, 2);
        String footer1 = provinDmz.substring(4, 5);
        String footer2 = provinDmz.substring(5, 6);

        // 匹配规则有北京（110000） 天津（120000） 上海（310000） 重庆（500000） 几个特殊的区县不一样

        //台湾 (710000) 香港 (810000) 澳门 (820000)

//        newRex = "[" + header1 + "]" + "[" + header2 + "]" + "\\d{1}[0-9]" + "[" + footer1 + "]" + "[" + footer2 + "]";  //654000 伊犁[0-9]
        newRex = "[" + header1 + "]" + "[" + header2 + "]" + "\\d{2}" + "[" + footer1 + "]" + "[" + footer2 + "]";  //654000 伊犁 第四位[0-9]

        if ("1".equals(header1) && "1".equals(header2)) {
            newRex = "[1][1]" + "[0][1-2]" + "\\d{1}[1-9]";
        }
        if ("1".equals(header1) && "2".equals(header2)) {
            newRex = "[1][2]" + "[0][1-2]" + "\\d{1}[1-9]";
        }
        if ("3".equals(header1) && "1".equals(header2)) {
            newRex = "[3][1]" + "[0][1-2]" + "\\d{1}[1-9]";
        }
        if ("5".equals(header1) && "0".equals(header2)) {
            newRex = "[5][0]" + "[0][1-2]" + "\\d{1}[1-9]";
        }

        //香港 台湾 澳门 特殊处理 返回原来的省区划
        if ("7".equals(header1) && "1".equals(header2)) {
            newRex = "[7][1][0][0][0][0]";
            for (CodeEntity.DataBean db : list) {
                Pattern p = Pattern.compile(newRex);
                Matcher m = p.matcher(db.getDmz());
                if (m.matches()) {
                    allCitys.add(db);
                }
            }
            return allCitys;
        }
        if ("8".equals(header1) && "1".equals(header2)) {
            newRex = "[8][1][0][0][0][0]";
            for (CodeEntity.DataBean db : list) {
                Pattern p = Pattern.compile(newRex);
                Matcher m = p.matcher(db.getDmz());
                if (m.matches()) {
                    allCitys.add(db);
                }
            }
            return allCitys;
        }
        if ("8".equals(header1) && "2".equals(header2)) {
            newRex = "[8][2][0][0][0][0]";
            for (CodeEntity.DataBean db : list) {
                Pattern p = Pattern.compile(newRex);
                Matcher m = p.matcher(db.getDmz());
                if (m.matches()) {
                    allCitys.add(db);
                }
            }
            return allCitys;
        }

        //去找四川的 代号51 00 00 (前两位是省，中间两位是城市，后两位是城市下的区县)
        for (CodeEntity.DataBean db : list) {
            Pattern p = Pattern.compile(newRex);
            Matcher m = p.matcher(db.getDmz());
            if (m.matches()) {
                if (!(header1 + header2 + "00").equals(db.getDmz().substring(0, 4))) { // 查询条件h1+h2+2个数字+f1+f2  可能包含了省 例如新疆650000  去除6500的省区划
                    allCitys.add(db);
                }
            }
            //新疆特定
            if ("65".equals(header1+header2) && "6590".equals(db.getDmz().substring(0, 4))) {  //新疆{"dmlb":"0033","dmsm1":"自治区直辖县级行政区划","dmz":"659000"}  新疆{"dmlb":"0033","dmsm1":"石河子市","dmz":"659001"}  新疆{"dmlb":"0033","dmsm1":"阿拉尔市","dmz":"659002"} 新疆{"dmlb":"0033","dmsm1":"图木舒克市","dmz":"659003"} 新疆{"dmlb":"0033","dmsm1":"五家渠市","dmz":"659004"}
                Pattern p_1 = Pattern.compile("[6][5][9][0]" + "[0]" + "[1-9]");
                Matcher m_2 = p_1.matcher(db.getDmz());
                if (m_2.matches()) {
                    allCitys.add(db);
                }
//                Log.d("lylog", "----  新疆" + GsonUtils.toJson(db));
            }
        }

//        Log.d("lylog", "----  正则表达式" + newRex);
//        Log.d("lylog", "----  城市列表" + GsonUtils.toJson(allCitys));
//        Log.d("lylog", "---- 根据0033查询城市数据 " + GsonUtils.toJson(list));
        return allCitys;
    }


    public static ArrayList<CodeEntity.DataBean> getXjCitys() {
        return xjCitys;
    }

    public static ArrayList<CodeEntity.DataBean> getScCitys() {
        return scCitys;
    }

    public static ArrayList<CodeEntity.DataBean> getAllProvince() {
        return allProvinces;
    }

}