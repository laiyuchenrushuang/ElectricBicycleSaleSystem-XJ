package com.seatrend.xj.electricbicyclesalesystem.util

import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.common.MyApplication
import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteUtils
import com.seatrend.xj.electricbicyclesalesystem.entity.YwSearchByZcbmBean
import com.seatrend.xj.electricbicyclesalesystem.http.HttpService

import java.util.HashMap

/**
 * Created by ly on 2019/8/1 17:59
 *
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class CarUtils {

    companion object {
        val CY_ZCDJ = MyApplication.myApplicationContext!!.getString(R.string.zcdj)
        val CY_ZYDJ = MyApplication.myApplicationContext!!.getString(R.string.zydj)
        val CY_BGDJ = MyApplication.myApplicationContext!!.getString(R.string.bgdj)
        val CY_BHPZ = MyApplication.myApplicationContext!!.getString(R.string.bhpz)
        val CY_XSDJ = MyApplication.myApplicationContext!!.getString(R.string.xsdj)
        val CY_ZXDJ = MyApplication.myApplicationContext!!.getString(R.string.zxdj)
        val CY_CLCY = MyApplication.myApplicationContext!!.getString(R.string.clcy)
        val Car_LX_BZ = MyApplication.myApplicationContext!!.getString(R.string.bz_car)
        val Car_LX_CBZ = MyApplication.myApplicationContext!!.getString(R.string.cbz_car)
        val ZC_QY_OFF = MyApplication.myApplicationContext!!.getString(R.string.zc_qy_off)
        val ZC_QY_ON = MyApplication.myApplicationContext!!.getString(R.string.zc_qy_on)
        val ZX_ZXBF = MyApplication.myApplicationContext!!.getString(R.string.zx_zxbf)
        val ZX_MS = MyApplication.myApplicationContext!!.getString(R.string.zx_ms)
        //业务类型码表对应  业务类型->代码值
        val ywlxMap: HashMap<String, String?>
            get() {
                val ywlxMap = HashMap<String, String?>()
                ywlxMap[CY_CLCY] = "1"
                ywlxMap[CY_ZCDJ] = "2"
                ywlxMap[CY_ZYDJ] = "4"
                ywlxMap[CY_BGDJ] = "3"
                ywlxMap[CY_BHPZ] = "7"
                ywlxMap[CY_XSDJ] = "6"
                ywlxMap[CY_ZXDJ] = "5"
                return ywlxMap
            }
        //业务类型码表对应  代码值->业务类型
        val dmzGetywlxMap: HashMap<String, String?>
            get() {
                val ywlxMap = HashMap<String, String?>()
                ywlxMap["1"] = CY_CLCY
                ywlxMap["2"] = CY_ZCDJ
                ywlxMap["4"] = CY_ZYDJ
                ywlxMap["3"] = CY_BGDJ
                ywlxMap["7"] = CY_BHPZ
                ywlxMap["6"] = CY_XSDJ
                ywlxMap["5"] = CY_ZXDJ
                return ywlxMap
            }

        //车身颜色码表对应(颜色->码)
        val csysMap: HashMap<String, String?>
            get() {
                val csysMap = HashMap<String, String?>()
                val sfzmmcList = CodeTableSQLiteUtils.queryByDMLB(Constants.CSYS)
                for (db in sfzmmcList) {
                    val dmz = db.dmz
                    val dmsm1 = db.dmsm1
                    csysMap.put(dmsm1, dmz)
                }
                return csysMap
            }

        //车身颜色码表对应  (码->颜色)
        val csysGetMap: HashMap<String, String?>
            get() {
                val csysMap = HashMap<String, String?>()
                val sfzmmcList = CodeTableSQLiteUtils.queryByDMLB(Constants.CSYS)
                for (db in sfzmmcList) {
                    val dmz = db.dmz
                    val dmsm1 = db.dmsm1
                    csysMap.put(dmz, dmsm1)
                }
                return csysMap
            }

        val csysGetPositionMap: HashMap<String, Int>
            get() {
                val csysMap = HashMap<String, Int>()
                val sfzmmcList = CodeTableSQLiteUtils.queryByDMLB(Constants.CSYS)
                for (i in 0 until sfzmmcList.size-1) {
                    val dmsm1 = sfzmmcList.get(i).dmsm1
                    csysMap.put(dmsm1, i)
                }
                return csysMap
            }

        // 车辆类型码表对应
        val cllxMap: HashMap<String, String?>
            get() {
                val cllxMap = HashMap<String, String?>()
                cllxMap[Car_LX_BZ] = "1"
                cllxMap[Car_LX_CBZ] = "2"
                return cllxMap
            }

        // 行驶区域码表对应（名称->码）（受限制，不受限制）
        val xsqyMap: HashMap<String, String?>
            get() {
                val xsqyMap = HashMap<String, String?>()
                xsqyMap[ZC_QY_OFF] = "1"
                xsqyMap[ZC_QY_ON] = "2"
                return xsqyMap
            }
        // 行驶区域码表对应（码->名称）（受限制，不受限制）
        val xsqyGetMcMap: HashMap<String, String?>
            get() {
                val xsqyMap = HashMap<String, String?>()
                xsqyMap["1"] = ZC_QY_OFF
                xsqyMap["2"] = ZC_QY_ON
                return xsqyMap
            }

        //统一接口 名称->码（去除来历证明，行驶区域（受限制，不受限制））
        fun getQTDMZ(cxxh: String): HashMap<String, String?> {
            val QTMap = HashMap<String, String?>()
            val sfzmmcList = CodeTableSQLiteUtils.queryByDMLB(cxxh)
            for (db in sfzmmcList) {
                val dmz = db.dmz.trim()
                val dmsm1 = db.dmsm1.trim()
                QTMap.put(dmsm1, dmz)
            }
            return QTMap
        }

        //统一接口 码->名称(去除来历证明，行驶区域（受限制，不受限制）)
        fun getQTDMMC(cxxh: String): HashMap<String, String?> {
            val QTMap = HashMap<String, String?>()
            val sfzmmcList = CodeTableSQLiteUtils.queryByDMLB(cxxh)
            for (db in sfzmmcList) {
                val dmz = db.dmz.trim()
                val dmsm1 = db.dmsm1.trim()
                QTMap.put(dmz, dmsm1)
            }
            return QTMap
        }
        var llzmMap = HashMap<String, String?>() //数据来自YwxxFragment

        //获取车辆类型
        val getCllxMap: HashMap<String, String?>
            get() {
                val getCllxMap = HashMap<String, String?>()
                getCllxMap["1"] = Car_LX_BZ
                getCllxMap["2"] = Car_LX_CBZ
                return getCllxMap
            }

        var sfzmcMap = HashMap<String, String?>()//数据来自YwxxFragment
        var szqyMap = HashMap<String, String?>()//数据来自YwxxFragment

        //根据ywlx 查询相关信息
       fun  getYwBusiness(lx:String,list:List<YwSearchByZcbmBean.Data.BusList>): YwSearchByZcbmBean.Data.BusList? {
           for (db in list){
               if (lx == db.ywlx){
                   return  db
               }
           }
            return null
       }

        //注销原因 （1自行报废，2灭失）
        val getZxyyDmzMap:HashMap<String,String?>
            get(){
                val getCllxMap = HashMap<String, String?>()
                getCllxMap[ZX_ZXBF] = "1"
                getCllxMap[ZX_MS] = "2"
                return getCllxMap
            }
    }
}
