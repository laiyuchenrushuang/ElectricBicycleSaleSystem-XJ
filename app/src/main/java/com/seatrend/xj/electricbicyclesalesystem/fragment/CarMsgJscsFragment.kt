package com.seatrend.xj.electricbicyclesalesystem.fragment

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.activity.CarInfoActivity
import com.seatrend.xj.electricbicyclesalesystem.activity.CarInfoByCyActivity
import com.seatrend.xj.electricbicyclesalesystem.activity.Yw3CzActivity
import com.seatrend.xj.electricbicyclesalesystem.common.BaseFragment
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.*
import com.seatrend.xj.electricbicyclesalesystem.util.CarUtils
import com.seatrend.xj.electricbicyclesalesystem.util.OtherUtils
import com.seatrend.xj.electricbicyclesalesystem.util.StringUtils
import kotlinx.android.synthetic.main.fragment_jscs.*

/**
 * Created by ly on 2019/7/2 10:03
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class CarMsgJscsFragment : BaseFragment() {
    var enity: AllBikeMsgEnity? = null
    private var cydata : CarMsgEnity?=null
    private var mActivity: CarInfoActivity? = null

    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_jscs, container, false)
    }

    override fun initView() {
        initData()
        bindEvent()
    }

    private fun bindEvent() {
        setScollTextView( carinfo_jscs_clzzs, carinfo_jscs_mpgdwz,carinfo_jscs_cjszcbmdwz)
    }

    private fun initData() {
        try {
            if (activity is CarInfoActivity) {

                if ("A" == activity.intent.getStringExtra("ywlx")) {
                    var data3c = activity.intent.getSerializableExtra("3c_data") as ThreeCEnity //注册登记专用
                    //注册登记3c走互联网获取的3c信息
                    if (data3c == null || data3c.data == null || data3c.data.threeCertificates == null) {
                        showToast("获取3C技术参数失败")
                        return
                    }

                    carinfo_jscs_zcbm.text = activity.intent.getStringExtra("zcbm")

                    carinfo_jscs_cjszcbmdwz.text = if (TextUtils.isEmpty(data3c.data.threeCertificates.data.codeOnFrame)) "/" else data3c.data.threeCertificates.data.codeOnFrame.trim()//车架上整车编码的位置
                    carinfo_jscs_clzwsb.text = if (TextUtils.isEmpty(data3c.data.threeCertificates.data.trademarkCn)) "/" else data3c.data.threeCertificates.data.trademarkCn //车辆中文商标
                    carinfo_jscs_cpxh.text = if (TextUtils.isEmpty(data3c.data.threeCertificates.data.productModel)) "/" else data3c.data.threeCertificates.data.productModel//产品型号
                    carinfo_jscs_mpgdwz.text = if (TextUtils.isEmpty(data3c.data.threeCertificates.data.fixedPositionName)) "/" else data3c.data.threeCertificates.data.fixedPositionName//铭牌固定位置
                    carinfo_jscs_cphgzbh.text = if (TextUtils.isEmpty(data3c.data.threeCertificates.data.qualificationCode)) "/" else data3c.data.threeCertificates.data.qualificationCode//产品合格证编号
                    carinfo_jscs_ccczsbh.text = if (TextUtils.isEmpty(data3c.data.threeCertificates.data.certcode)) "/" else data3c.data.threeCertificates.data.certcode//CCC证书编号
                    carinfo_jscs_ccczsfzrq.text = if (TextUtils.isEmpty(data3c.data.threeCertificates.data.certExpiryDate)) "/" else data3c.data.threeCertificates.data.certExpiryDate//CCC证书有效期止日期
                    carinfo_jscs_csys.text = if (TextUtils.isEmpty(data3c.data.threeCertificates.data.color)) "/" else data3c.data.threeCertificates.data.color//车颜色
                    carinfo_jscs_c.text = if (TextUtils.isEmpty(data3c.data.threeCertificates.data.length)) "/" else data3c.data.threeCertificates.data.length//长
                    carinfo_jscs_k.text = if (TextUtils.isEmpty(data3c.data.threeCertificates.data.width)) "/" else data3c.data.threeCertificates.data.width//宽
                    carinfo_jscs_g.text = if (TextUtils.isEmpty(data3c.data.threeCertificates.data.height)) "/" else data3c.data.threeCertificates.data.height//高
                    carinfo_jscs_zbzl.text = if (TextUtils.isEmpty(data3c.data.threeCertificates.data.weight)) "/" else data3c.data.threeCertificates.data.weight//整备质量
                    carinfo_jscs_zgsjcs.text = if (TextUtils.isEmpty(data3c.data.threeCertificates.data.maxSpeed)) "/" else data3c.data.threeCertificates.data.maxSpeed//最高时速
                    carinfo_jscs_clzzs.text = if (TextUtils.isEmpty(data3c.data.threeCertificates.data.vehicleManufacturer)) "/" else data3c.data.threeCertificates.data.vehicleManufacturer//车辆制造商
                    carinfo_jscs_xhlc.text = if (TextUtils.isEmpty(data3c.data.threeCertificates.data.continuousMileage)) "/" else data3c.data.threeCertificates.data.continuousMileage//续航里程
                    carinfo_jscs_ccczszt.text = if (TextUtils.isEmpty(data3c.data.threeCertificates.data.certState)) "/" else data3c.data.threeCertificates.data.certState    //证书状态
                    carinfo_jscs_zzrq.text = if (TextUtils.isEmpty(data3c.data.threeCertificates.data.manufacturingDate)) "/" else data3c.data.threeCertificates.data.manufacturingDate //制造日期
                } else {
                    //其他的登记 走服务器上存储的3c信息
                    var data1 = activity.intent.getSerializableExtra("all_data") as CYEntranceEnity
                    if (data1 == null || data1.data == null || data1.data.threeCertificates == null) {
                        showToast("获取3C技术参数失败")
                        return
                    }
                    carinfo_jscs_zcbm.text = data1.data.threeCertificates.zcbm
                    carinfo_jscs_cjszcbmdwz.text = if (TextUtils.isEmpty(data1.data.threeCertificates.cjszcbmwz)) "/" else data1.data.threeCertificates.cjszcbmwz.trim()//车架上整车编码的位置
                    carinfo_jscs_clzwsb.text = if (TextUtils.isEmpty(data1.data.threeCertificates.clzwsb)) "/" else data1.data.threeCertificates.clzwsb //车辆中文商标
                    carinfo_jscs_cpxh.text = if (TextUtils.isEmpty(data1.data.threeCertificates.cpxh)) "/" else data1.data.threeCertificates.cpxh//产品型号
                    carinfo_jscs_mpgdwz.text = if (TextUtils.isEmpty(data1.data.threeCertificates.mpgdwz)) "/" else data1.data.threeCertificates.mpgdwz//铭牌固定位置
                    carinfo_jscs_cphgzbh.text = if (TextUtils.isEmpty(data1.data.threeCertificates.cphgzbh)) "/" else data1.data.threeCertificates.cphgzbh//产品合格证编号
                    carinfo_jscs_ccczsbh.text = if (TextUtils.isEmpty(data1.data.threeCertificates.cccbh)) "/" else data1.data.threeCertificates.cccbh//CCC证书编号
                    carinfo_jscs_ccczsfzrq.text = if (TextUtils.isEmpty(data1.data.threeCertificates.cccyxq)) "/" else data1.data.threeCertificates.cccyxq//CCC证书有效期止日期
                    carinfo_jscs_csys.text = if (TextUtils.isEmpty(data1.data.threeCertificates.csys)) "/" else data1.data.threeCertificates.csys//车颜色
                    carinfo_jscs_c.text = if (TextUtils.isEmpty(data1.data.threeCertificates.cwkc)) "/" else data1.data.threeCertificates.cwkc//长
                    carinfo_jscs_k.text = if (TextUtils.isEmpty(data1.data.threeCertificates.cwkk)) "/" else data1.data.threeCertificates.cwkk//宽
                    carinfo_jscs_g.text = if (TextUtils.isEmpty(data1.data.threeCertificates.cwkg)) "/" else data1.data.threeCertificates.cwkg//高
                    carinfo_jscs_zbzl.text = if (TextUtils.isEmpty(data1.data.threeCertificates.zbzl)) "/" else data1.data.threeCertificates.zbzl//整备质量
                    carinfo_jscs_zgsjcs.text = if (TextUtils.isEmpty(data1.data.threeCertificates.zgcs)) "/" else data1.data.threeCertificates.zgcs//最高时速
                    carinfo_jscs_clzzs.text = if (TextUtils.isEmpty(data1.data.threeCertificates.clzzs)) "/" else data1.data.threeCertificates.clzzs//车辆制造商
                    carinfo_jscs_xhlc.text = if (TextUtils.isEmpty(data1.data.threeCertificates.xhlc)) "/" else data1.data.threeCertificates.xhlc//续航里程
                    carinfo_jscs_ccczszt.text = if (TextUtils.isEmpty(data1.data.threeCertificates.ccczt)) "/" else data1.data.threeCertificates.ccczt    //证书状态
                    carinfo_jscs_zzrq.text = if (TextUtils.isEmpty(data1.data.threeCertificates.zzrq)) "/" else data1.data.threeCertificates.zzrq //制造日期
                }
            } else {

                if (activity is Yw3CzActivity && "3" == activity.intent.getStringExtra(Constants.UI_TYPE)) { //证明是车辆查询来的

                    cydata = activity.intent.getSerializableExtra("cy_data") as CarMsgEnity

                    if (cydata == null || cydata!!.data == null || cydata!!.data.jscs == null || cydata!!.data.jscs.cphgzbh == null || cydata!!.data.cccdata == null) {
                        showToast("获取技术参数失败")
                        return
                    }
                    carinfo_jscs_zcbm.text = cydata!!.data.jscs.cphgzbh //zcbm
                    carinfo_jscs_cjszcbmdwz.text = cydata!!.data.jscs.cjszcbhwz.trim() //wz
                    carinfo_jscs_clzwsb.text = cydata!!.data.jscs.clzwsb //
                    carinfo_jscs_cpxh.text = cydata!!.data.jscs.cpxh //
                    carinfo_jscs_mpgdwz.text = cydata!!.data.jscs.mpgdwz //
                    carinfo_jscs_cphgzbh.text = cydata!!.data.jscs.cphgzbh //
                    carinfo_jscs_ccczsbh.text = cydata!!.data.jscs.cccbh //
                    carinfo_jscs_ccczsfzrq.text = cydata!!.data.cccdata.cccyxq
                    carinfo_jscs_csys.text = cydata!!.data.cccdata.csys
                    carinfo_jscs_c.text = cydata!!.data.jscs.cwkc
                    carinfo_jscs_k.text = cydata!!.data.jscs.cwkk
                    carinfo_jscs_g.text = cydata!!.data.jscs.cwkg
//                carinfo_jscs_qhlzxj.text = enity!!.data.checkData.scqhlzxj  //前后中心距
                    carinfo_jscs_zbzl.text = cydata!!.data.cccdata.zbzl
                    carinfo_jscs_zgsjcs.text = cydata!!.data.jscs.zgcs
                    //        carinfo_jscs_ddjxh.text = enity.data.cccData.ddjxs // 电动机序号
                    carinfo_jscs_clzzs.text = cydata!!.data.jscs.clzzs//车辆制造商
                    carinfo_jscs_xhlc.text = cydata!!.data.cccdata.xhlc //续航里程  张月说不展示
                    carinfo_jscs_zzrq.text = cydata!!.data.cccdata.zzrq//制造日期
                    carinfo_jscs_ccczszt.text =cydata!!.data.cccdata.ccczt//ccc状态
                } else { //原来的逻辑不变
                    enity = activity.intent.getSerializableExtra("all_data") as AllBikeMsgEnity
                    if (enity == null || enity!!.data == null || enity!!.data.cccData == null) {
                        showToast("获取技术参数失败")
                        return
                    }
                    carinfo_jscs_zcbm.text = if (null == enity!!.data.checkData || TextUtils.isEmpty(enity!!.data.checkData.zcbm)) enity!!.data.fjdcBusiness.zcbm else enity!!.data.checkData.zcbm //zcbm
                    carinfo_jscs_cjszcbmdwz.text = enity!!.data.cccData.cjszcbmwz.trim() //wz
                    carinfo_jscs_clzwsb.text = enity!!.data.cccData.clzwsb //
                    carinfo_jscs_cpxh.text = enity!!.data.cccData.cpxh //
                    carinfo_jscs_mpgdwz.text = enity!!.data.cccData.mpgdwz //
                    carinfo_jscs_cphgzbh.text = enity!!.data.cccData.cphgzbh //
                    carinfo_jscs_ccczsbh.text = enity!!.data.cccData.cccbh //
                    carinfo_jscs_ccczsfzrq.text = enity!!.data.cccData.cccyxq
                    carinfo_jscs_csys.text = enity!!.data.cccData.csys
                    carinfo_jscs_c.text = enity!!.data.cccData.cwkc
                    carinfo_jscs_k.text = enity!!.data.cccData.cwkk
                    carinfo_jscs_g.text = enity!!.data.cccData.cwkg
//                carinfo_jscs_qhlzxj.text = enity!!.data.checkData.scqhlzxj  //前后中心距
                    carinfo_jscs_zbzl.text = enity!!.data.cccData.zbzl
                    carinfo_jscs_zgsjcs.text = enity!!.data.cccData.zgcs
                    //        carinfo_jscs_ddjxh.text = enity.data.cccData.ddjxs // 电动机序号
                    carinfo_jscs_clzzs.text = enity!!.data.cccData.clzzs//车辆制造商
                    carinfo_jscs_xhlc.text = enity!!.data.cccData.xhlc //续航里程
                    carinfo_jscs_zzrq.text = enity!!.data.cccData.zzrq//制造日期
                    carinfo_jscs_ccczszt.text = enity!!.data.cccData.ccczt//ccc状态
                }
            }
        } catch (e: Exception) {
            showToast(e.message.toString())
        }

    }

//    override fun onAttach(context: Context?) {
//        super.onAttach(context)
//        if (context is CarInfoActivity) {
//            mActivity = context
//        } else if (context is CarInfoActivity) {
//            mActivity = context
//        }
//    }
}