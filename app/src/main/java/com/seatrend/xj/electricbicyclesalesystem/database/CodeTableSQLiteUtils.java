package com.seatrend.xj.electricbicyclesalesystem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;


import com.seatrend.xj.electricbicyclesalesystem.common.Constants;
import com.seatrend.xj.electricbicyclesalesystem.entity.AllLizmBean;
import com.seatrend.xj.electricbicyclesalesystem.entity.CodeEntity;
import com.seatrend.xj.electricbicyclesalesystem.entity.PhotoEntity;
import com.seatrend.xj.electricbicyclesalesystem.util.PermissionEnity;
import com.seatrend.xj.electricbicyclesalesystem.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CodeTableSQLiteUtils {


    //添加数据

    /**
     * "xtlb": "00",
     * "dmlb": "1007",
     * "dmz": "01",
     * "mldh": "01",
     * "dmsm1": "大型汽车",
     * "mlmc": "大型汽车",
     * "dmsm2": "黄底黑字",
     * "dmsm3": null,
     * "zt": "1",
     * "dmsm4": null
     *
     * @param
     */
    public static void insert(List<CodeEntity.DataBean> data) {
        CodeTableSQLiteOpenHelper dbHelper = CodeTableSQLiteOpenHelper.getInstance();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        for (CodeEntity.DataBean dataBean : data) {
            cv.clear();
            cv.put("xtlb", StringUtils.isNull(dataBean.getXtlb()));
            cv.put("dmlb", StringUtils.isNull(dataBean.getDmlb()));
            cv.put("dmz", StringUtils.isNull(dataBean.getDmz()));
            cv.put("dmsm1", StringUtils.isNull(dataBean.getDmsm1()));
            cv.put("mlmc", StringUtils.isNull(dataBean.getXtlb()));
            cv.put("dmsm2", StringUtils.isNull(dataBean.getDmsm2()));
            cv.put("dmsm3", StringUtils.isNull(dataBean.getDmsm3()));
            cv.put("zt", StringUtils.isNull(dataBean.getZt()));
            cv.put("dmsm4", StringUtils.isNull(dataBean.getDmsm4()));
            db.insert(CodeTableSQLiteOpenHelper.TABLE_NAME, null, cv);
        }

        db.close();
    }

    /**
     * "xtlb": "00",
     * "dmlb": "1007",
     * "dmz": "01",
     * "mldh": "01",
     * "dmsm1": "大型汽车",
     * "mlmc": "大型汽车",
     * "dmsm2": "黄底黑字",
     * "dmsm3": null,
     * "zt": "1",
     * "dmsm4": null
     *
     * @param
     */
    //查询数据
    public static List<CodeEntity.DataBean> queryByDMLB(String DMLB) {
        CodeTableSQLiteOpenHelper dbHelper = CodeTableSQLiteOpenHelper.getInstance();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<CodeEntity.DataBean> list = new ArrayList<>();

        //db.insert(CodeTableSQLiteOpenHelper.TABLE_NAME,null,cv);//"dmlb="+DMLB
        Cursor query = db.query(CodeTableSQLiteOpenHelper.TABLE_NAME, null, "dmlb=?", new String[]{DMLB}, null, null, null, null);
        while (query.moveToNext()) {

            String dmlb = query.getString(query.getColumnIndex("dmlb"));
            String dmz = query.getString(query.getColumnIndex("dmz"));
            String dmsm1 = query.getString(query.getColumnIndex("dmsm1"));
            CodeEntity.DataBean dataBean = new CodeEntity.DataBean();
            dataBean.setDmlb(dmlb);
            dataBean.setDmz(dmz);
            dataBean.setDmsm1(dmsm1);
            list.add(dataBean);
        }
        db.close();
        query.close();
        return list;
    }

    //查询数据 DMLB 和 dmz 种类查询
    public static List<CodeEntity.DataBean> queryByDmlbAndDmz(String DMLB, String Dmz) {
        CodeTableSQLiteOpenHelper dbHelper = CodeTableSQLiteOpenHelper.getInstance();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<CodeEntity.DataBean> list = new ArrayList<>();

        Cursor query = db.query(CodeTableSQLiteOpenHelper.TABLE_NAME, new String[]{}, "dmlb=? and dmz=?", new String[]{DMLB, Dmz}, null, null, null, null);
        while (query.moveToNext()) {
            String dmlb = query.getString(query.getColumnIndex("dmlb"));
            String dmz = query.getString(query.getColumnIndex("dmz"));
            String dmsm1 = query.getString(query.getColumnIndex("dmsm1"));
            CodeEntity.DataBean dataBean = new CodeEntity.DataBean();
            dataBean.setDmlb(dmlb);
            dataBean.setDmz(dmz);
            dataBean.setDmsm1(dmsm1);
            list.add(dataBean);
        }
        db.close();
        query.close();

        return list;
    }

    //查询数据 DMLB 和 dmsm1 种类查询
    public static String queryByDmlbAndDmsm(String DMLB, String dmsm1) {
        CodeTableSQLiteOpenHelper dbHelper = CodeTableSQLiteOpenHelper.getInstance();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor query = db.query(CodeTableSQLiteOpenHelper.TABLE_NAME, new String[]{}, "dmlb=? and dmsm1=?", new String[]{DMLB, dmsm1}, null, null, null, null);
        while (query.moveToNext()) {
            String dmz = query.getString(query.getColumnIndex("dmz"));
            if (!TextUtils.isEmpty(dmz)) {
                return dmz;
            }
        }
        return "";
    }

    //查询数据 DMLB 和 dmz 种类查询具体值
    public static String queryByDmlbAndDmzGetDmsm(String DMLB, String dmz) {
        CodeTableSQLiteOpenHelper dbHelper = CodeTableSQLiteOpenHelper.getInstance();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor query = db.query(CodeTableSQLiteOpenHelper.TABLE_NAME, new String[]{}, "dmlb=? and dmz=?", new String[]{DMLB, dmz}, null, null, null, null);
        while (query.moveToNext()) {
            String dmsm1 = query.getString(query.getColumnIndex("dmsm1"));
            if (!TextUtils.isEmpty(dmsm1)) {
                return dmsm1;
            }
        }
        return "";
    }

    public static List<CodeEntity.DataBean> queryAllDMZ() {
        CodeTableSQLiteOpenHelper dbHelper = CodeTableSQLiteOpenHelper.getInstance();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<CodeEntity.DataBean> list = new ArrayList<>();

        Cursor query = db.query(CodeTableSQLiteOpenHelper.TABLE_NAME, new String[]{}, null, null, null, null, null, null);
        while (query.moveToNext()) {
            String dmlb = query.getString(query.getColumnIndex("dmlb"));
            String dmz = query.getString(query.getColumnIndex("dmz"));
            String dmsm1 = query.getString(query.getColumnIndex("dmsm1"));
            CodeEntity.DataBean dataBean = new CodeEntity.DataBean();
            dataBean.setDmlb(dmlb);
            dataBean.setDmz(dmz);
            dataBean.setDmsm1(dmsm1);
            list.add(dataBean);
        }
        db.close();
        query.close();

        return list;

    }


    //查询字符 是否存在
    public static boolean isExist(Context context, String text) {
        CodeTableSQLiteOpenHelper dbHelper = CodeTableSQLiteOpenHelper.getInstance();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //db.insert(CodeTableSQLiteOpenHelper.TABLE_NAME,null,cv);
        Cursor query = db.query(CodeTableSQLiteOpenHelper.TABLE_NAME, null, null, null, null, null, null, null);
        if (query.moveToFirst()) {
            String exist = query.getString(query.getColumnIndex("text"));
            if (exist.equals(text)) {
                db.close();
                query.close();
                return true;
            }
        }
        db.close();
        query.close();
        return false;
    }

    public static void deleteAll(String tableName) {
        CodeTableSQLiteOpenHelper dbHelper = CodeTableSQLiteOpenHelper.getInstance();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String sql = String.format("delete from %s", tableName);
        db.execSQL(sql);

        db.close();

    }

    //增加来历证明的数据存储
    public static void insertLlzmToDB(List<? extends AllLizmBean.Data.OriginConfig> data) {
        CodeTableSQLiteOpenHelper dbHelper = CodeTableSQLiteOpenHelper.getInstance();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        for (AllLizmBean.Data.OriginConfig dataBean : data) {
            cv.clear();
            cv.put("dmlb", StringUtils.isNull(Constants.Companion.getLLZM()));
            cv.put("dmz", StringUtils.isNull(dataBean.getZplx()));
            cv.put("dmsm1", StringUtils.isNull(dataBean.getZmmc()));
            db.insert(CodeTableSQLiteOpenHelper.TABLE_NAME, null, cv);
        }
    }

    //增加行政区划省级的存储，目的是增加dmlb进行本地查询
    public static void insertQhToDB(List<CodeEntity.DataBean> data) {
        CodeTableSQLiteOpenHelper dbHelper = CodeTableSQLiteOpenHelper.getInstance();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        for (CodeEntity.DataBean dataBean : data) {
            cv.clear();
            cv.put("dmlb", StringUtils.isNull(Constants.Companion.getMY_QH_SHENG_DMLB()));  //0219
            cv.put("dmz", StringUtils.isNull(dataBean.getDmz()));
            cv.put("dmsm1", StringUtils.isNull(dataBean.getDmsm1()));
            db.insert(CodeTableSQLiteOpenHelper.TABLE_NAME, null, cv);
        }
    }

    //增加权限代码code
    public static void insertPermmisionToDB(List<PermissionEnity.Data> data) {
        CodeTableSQLiteOpenHelper dbHelper = CodeTableSQLiteOpenHelper.getInstance();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        for (PermissionEnity.Data dataBean : data) {
            if (Constants.Companion.getUSER_PMS().equals(dataBean.getXtlb())) {
                cv.clear();
                cv.put("dmlb", StringUtils.isNull(Constants.Companion.getUSER_PMS()));  //98
                cv.put("dmz", StringUtils.isNull(dataBean.getQxdm()));
                cv.put("dmsm1", StringUtils.isNull(dataBean.getQxmc()));
                db.insert(CodeTableSQLiteOpenHelper.TABLE_NAME, null, cv);
            }
        }
    }


    // ===============================service  图片的相关SQL ===========================================

    public static void addPhoto(PhotoEntity entity) {
        String lsh = entity.getLsh();
        String zpzl = entity.getZpzl();

        // 数据库已经存在 该车辆类型的照片 执行更新，不存在执行 插入
        if (!TextUtils.isEmpty(lsh)) {
            List<PhotoEntity> list = queryByLshAndDmzRList(lsh, zpzl);
            if (list.size() > 0) {
                updateByLshAndDmz(entity);
            } else {
                insert(entity);
            }
        } else if (!TextUtils.isEmpty(lsh)) {
            List<PhotoEntity> list = queryByLshAndDmzRList(lsh, zpzl);
            if (list.size() > 0) {
                updateByLshAndDmz(entity);
            } else {
                insert(entity);
            }
        }

    }


    public static void insert(PhotoEntity entity) {
        CodeTableSQLiteOpenHelper dbHelper = CodeTableSQLiteOpenHelper.getInstance();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.Companion.getS_LSH(), entity.getLsh());
        cv.put(Constants.Companion.getS_XH(), entity.getXh());
        cv.put(Constants.Companion.getS_ZPZL(), entity.getZpzl());
        cv.put(Constants.Companion.getS_ZPDZ(), entity.getZpdz());
        cv.put(Constants.Companion.getS_ZPPATH(), entity.getZpPath());
        cv.put(Constants.Companion.getS_LRR(), entity.getLrr());
        cv.put(Constants.Companion.getS_LRBM(), entity.getLrbm());
        cv.put(Constants.Companion.getS_ZPSM(), entity.getZpsm());
        cv.put(Constants.Companion.getS_CFFS(), entity.getCffs());
        db.insert(CodeTableSQLiteOpenHelper.PHOTO_TABLE_NAME, null, cv);
        db.close();
    }


    public static List<PhotoEntity> queryAll() {
        CodeTableSQLiteOpenHelper dbHelper = CodeTableSQLiteOpenHelper.getInstance();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor query = db.query(CodeTableSQLiteOpenHelper.PHOTO_TABLE_NAME, new String[]{}, null,
                null, null, null, null, null);
        List<PhotoEntity> list = new ArrayList<>();
        while (query.moveToNext()) {
            String lsh = query.getString(query.getColumnIndex(Constants.Companion.getS_LSH()));
            String xh = query.getString(query.getColumnIndex(Constants.Companion.getS_XH()));
            String zpzl = query.getString(query.getColumnIndex(Constants.Companion.getS_ZPZL()));
            String zpdz = query.getString(query.getColumnIndex(Constants.Companion.getS_ZPDZ()));
            String zpsm = query.getString(query.getColumnIndex(Constants.Companion.getS_ZPSM()));
            String cffs = query.getString(query.getColumnIndex(Constants.Companion.getS_CFFS()));
            String lrr = query.getString(query.getColumnIndex(Constants.Companion.getS_LRR()));
            String lrbm = query.getString(query.getColumnIndex(Constants.Companion.getS_LRBM()));
            String zpPath = query.getString(query.getColumnIndex(Constants.Companion.getS_ZPPATH()));

            PhotoEntity photoEntity = new PhotoEntity();
            photoEntity.setLsh(lsh);
            photoEntity.setXh(xh);
            photoEntity.setZpzl(zpzl);
            photoEntity.setZpdz(zpdz);
            photoEntity.setZpsm(zpsm);
            photoEntity.setCffs(cffs);
            photoEntity.setLrr(lrr);
            photoEntity.setLrbm(lrbm);
            photoEntity.setZpPath(zpPath);
            list.add(photoEntity);
        }
        return list;
    }

    /**
     * 用lsh，代码值 删除数据
     *
     * @param lsh
     * @param zpzl
     */
    public static void deleteByLshAndDmz(String lsh, String zpzl) {
        CodeTableSQLiteOpenHelper dbHelper = CodeTableSQLiteOpenHelper.getInstance();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = String.format("delete from %s where lsh='" + lsh + "' and " + "zpzl='" + zpzl + "'", CodeTableSQLiteOpenHelper.PHOTO_TABLE_NAME);
        db.execSQL(sql);
        db.close();
    }

    /**
     * 增加元素
     *
     * @param id   照片地址
     * @param lsh  流水号
     * @param zplx 照片类型
     */
    public static void updateByLshAndDmz(String lsh, String zplx, String id) {
        CodeTableSQLiteOpenHelper dbHelper = CodeTableSQLiteOpenHelper.getInstance();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.Companion.getS_ZPDZ(), id);
        db.update(CodeTableSQLiteOpenHelper.PHOTO_TABLE_NAME, cv, "lsh=? and zpzl=?", new String[]{lsh, zplx});
        db.close();
    }

    /**
     * 增加元素
     *
     * @param entity 实体
     */
    public static void updateByLshAndDmz(PhotoEntity entity) {
        CodeTableSQLiteOpenHelper dbHelper = CodeTableSQLiteOpenHelper.getInstance();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.Companion.getS_LSH(), entity.getLsh());
        cv.put(Constants.Companion.getS_XH(), entity.getXh());
        cv.put(Constants.Companion.getS_ZPZL(), entity.getZpzl());
        cv.put(Constants.Companion.getS_ZPDZ(), entity.getZpdz());
        cv.put(Constants.Companion.getS_ZPPATH(), entity.getZpPath());
        cv.put(Constants.Companion.getS_LRR(), entity.getLrr());
        cv.put(Constants.Companion.getS_LRBM(), entity.getLrbm());
        cv.put(Constants.Companion.getS_ZPSM(), entity.getZpsm());
        cv.put(Constants.Companion.getS_CFFS(), entity.getCffs());
        db.update(CodeTableSQLiteOpenHelper.PHOTO_TABLE_NAME, cv, "lsh=? and zpzl=?", new String[]{entity.getLsh(), entity.getZpzl()});
        db.close();
    }

    /**
     * 查询元素
     */
    public static PhotoEntity queryByLshAndDmz(String lsh, String zplx) {
        CodeTableSQLiteOpenHelper dbHelper = CodeTableSQLiteOpenHelper.getInstance();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor query = db.query(CodeTableSQLiteOpenHelper.PHOTO_TABLE_NAME, new String[]{}, null,
                null, null, null, null, null);

        while (query.moveToNext()) {
            String lshx = query.getString(query.getColumnIndex(Constants.Companion.getS_LSH()));
            String zpzl = query.getString(query.getColumnIndex(Constants.Companion.getS_ZPZL()));
            if (lsh.equals(lshx) && zplx.equals(zpzl)) {
                String zpdz = query.getString(query.getColumnIndex(Constants.Companion.getS_ZPDZ()));
                String zpsm = query.getString(query.getColumnIndex(Constants.Companion.getS_ZPSM()));
                String cffs = query.getString(query.getColumnIndex(Constants.Companion.getS_CFFS()));
                String lrr = query.getString(query.getColumnIndex(Constants.Companion.getS_LRR()));
                String lrbm = query.getString(query.getColumnIndex(Constants.Companion.getS_LRBM()));
                String zpPath = query.getString(query.getColumnIndex(Constants.Companion.getS_ZPPATH()));
                String xh = query.getString(query.getColumnIndex(Constants.Companion.getS_XH()));

                PhotoEntity photoEntity = new PhotoEntity();
                photoEntity.setLsh(lsh);
                photoEntity.setXh(xh);
                photoEntity.setZpzl(zpzl);
                photoEntity.setZpdz(zpdz);
                photoEntity.setZpsm(zpsm);
                photoEntity.setCffs(cffs);
                photoEntity.setLrr(lrr);
                photoEntity.setLrbm(lrbm);
                photoEntity.setZpPath(zpPath);
                return photoEntity;
            }
        }
        return null;
    }


    /**
     * 查询元素
     */
    public static List<PhotoEntity> queryByLshAndDmzRList(String lsh, String zplx) {
        CodeTableSQLiteOpenHelper dbHelper = CodeTableSQLiteOpenHelper.getInstance();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor query = db.query(CodeTableSQLiteOpenHelper.PHOTO_TABLE_NAME, new String[]{}, null,
                null, null, null, null, null);
        List<PhotoEntity> list = new ArrayList<>();
        while (query.moveToNext()) {
            String lshx = query.getString(query.getColumnIndex(Constants.Companion.getS_LSH()));
            String zpzl = query.getString(query.getColumnIndex(Constants.Companion.getS_ZPZL()));

            if (lsh.equals(lshx) && zplx.equals(zpzl)) {
                String zpdz = query.getString(query.getColumnIndex(Constants.Companion.getS_ZPDZ()));
                String zpsm = query.getString(query.getColumnIndex(Constants.Companion.getS_ZPSM()));
                String cffs = query.getString(query.getColumnIndex(Constants.Companion.getS_CFFS()));
                String lrr = query.getString(query.getColumnIndex(Constants.Companion.getS_LRR()));
                String lrbm = query.getString(query.getColumnIndex(Constants.Companion.getS_LRBM()));
                String zpPath = query.getString(query.getColumnIndex(Constants.Companion.getS_ZPPATH()));
                String xh = query.getString(query.getColumnIndex(Constants.Companion.getS_XH()));

                PhotoEntity photoEntity = new PhotoEntity();
                photoEntity.setLsh(lsh);
                photoEntity.setXh(xh);
                photoEntity.setZpzl(zpzl);
                photoEntity.setZpdz(zpdz);
                photoEntity.setZpsm(zpsm);
                photoEntity.setCffs(cffs);
                photoEntity.setLrr(lrr);
                photoEntity.setLrbm(lrbm);
                photoEntity.setZpPath(zpPath);
                list.add(photoEntity);
            }
        }
        return list;
        // ===============================service  图片的相关SQL ===========================================
    }
}
