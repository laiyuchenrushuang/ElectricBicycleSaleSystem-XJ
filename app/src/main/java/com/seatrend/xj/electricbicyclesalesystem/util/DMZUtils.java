package com.seatrend.xj.electricbicyclesalesystem.util;

import com.seatrend.xj.electricbicyclesalesystem.common.Constants;
import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteUtils;
import com.seatrend.xj.electricbicyclesalesystem.entity.CodeEntity;
import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteUtils;
import com.seatrend.xj.electricbicyclesalesystem.entity.CodeEntity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ly on 2019/10/29 19:40
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class DMZUtils {

    /**
     *  key是代码值，value是具体内容
     *  dmlb  代码类别
     *  key 是dmz
     */
    public static String getDMSM(String dmlb,String key){
        List<CodeEntity.DataBean> cllxList = CodeTableSQLiteUtils.queryByDMLB(dmlb);
        for (CodeEntity.DataBean db : cllxList) {
            String dmz = db.getDmz();
            if(dmz.equals(key)){
                return db.getDmsm1().trim();
            }
        }
        return null;
    }

    /**
     * 根据内容找代码值
     * dmlb  代码类别
     * value 是 dmsm1
     */
    public static String getDMZ(String dmlb,String value){
        List<CodeEntity.DataBean> cllxList = CodeTableSQLiteUtils.queryByDMLB(dmlb);
        for (CodeEntity.DataBean db : cllxList) {
            String dmsm = db.getDmsm1();
            if(dmsm.equals(value)){
                return db.getDmz();
            }
        }
        return null;
    }

}
