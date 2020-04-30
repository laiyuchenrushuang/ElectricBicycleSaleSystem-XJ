package com.seatrend.xj.electricbicyclesalesystem.fragment

import android.content.Context
import android.text.TextUtils
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.activity.CarInfoActivity
import com.seatrend.xj.electricbicyclesalesystem.common.BaseFragment
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.CYEntranceEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.ThreeCEnity
import com.seatrend.xj.electricbicyclesalesystem.util.StringUtils
import com.seatrend.xj.electricbicyclesalesystem.util.TextViewUtils
import kotlinx.android.synthetic.main.fragment_ggxx.*
import kotlinx.android.synthetic.main.fragment_ggxx.carinfo_jscs_c
import kotlinx.android.synthetic.main.fragment_ggxx.carinfo_jscs_ccczsbh
import kotlinx.android.synthetic.main.fragment_ggxx.carinfo_jscs_ccczsfzrq
import kotlinx.android.synthetic.main.fragment_ggxx.carinfo_jscs_ccczszt
import kotlinx.android.synthetic.main.fragment_ggxx.carinfo_jscs_cjszcbmdwz
import kotlinx.android.synthetic.main.fragment_ggxx.carinfo_jscs_clzwsb
import kotlinx.android.synthetic.main.fragment_ggxx.carinfo_jscs_clzzs
import kotlinx.android.synthetic.main.fragment_ggxx.carinfo_jscs_cphgzbh
import kotlinx.android.synthetic.main.fragment_ggxx.carinfo_jscs_cpxh
import kotlinx.android.synthetic.main.fragment_ggxx.carinfo_jscs_csys
import kotlinx.android.synthetic.main.fragment_ggxx.carinfo_jscs_g
import kotlinx.android.synthetic.main.fragment_ggxx.carinfo_jscs_k
import kotlinx.android.synthetic.main.fragment_ggxx.carinfo_jscs_mpgdwz
import kotlinx.android.synthetic.main.fragment_ggxx.carinfo_jscs_xhlc
import kotlinx.android.synthetic.main.fragment_ggxx.carinfo_jscs_zbzl
import kotlinx.android.synthetic.main.fragment_ggxx.carinfo_jscs_zcbm
import kotlinx.android.synthetic.main.fragment_ggxx.carinfo_jscs_zgsjcs
import kotlinx.android.synthetic.main.fragment_ggxx.carinfo_jscs_zzrq

/**
 * Created by ly on 2019/7/2 10:03
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class CarMsgGgxxFragment : BaseFragment() {
    private var mActivity: CarInfoActivity? = null
    private val ywlx = "2"  //业务类型通道
    private val yclx = "1"  //远程类型通道

    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_ggxx, container, false)
    }

    override fun initView() {
        initData()
        bindEvent()
    }


    private fun bindEvent() {
        //字段过长的处理
        setScollTextView(carinfo_jscs_kzqscqy, carinfo_jscs_ddjscqy, carinfo_jscs_clzzs, carinfo_jscs_xdcscqy, carinfo_jscs_scqymc, carinfo_jscs_scqydz, carinfo_jscs_mpgdwz,carinfo_jscs_cjszcbmdwz)
    }

    private fun initData() {
        val data1 = activity.intent.getSerializableExtra("all_data") as CYEntranceEnity

        if (data1 == null || data1.data == null) {
//            showToast("获取参数为空")
            return
        }


//        carinfo_jscs_zcbm.text = if (TextUtils.isEmpty(data3c.data.threeCertificates.data.vehicleCode)) "/" else data1.data.threeCertificates.vehicleCode//zcbm

        if ("A".equals(activity.intent.getStringExtra("ywlx"))) {
            val data3c = activity.intent.getSerializableExtra("3c_data") as ThreeCEnity
            if (data3c == null || data3c.data == null || data3c.data == null || data3c.data.threeCertificates == null || data3c.data.threeCertificates.data == null) {
//                showToast("获取3C技术参数为空")
                return
            }
            ll_zcbm.visibility = View.VISIBLE
            zcbm_v.visibility = View.VISIBLE
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

        if (data1.data.fjdcJscu == null) {
//            showToast("获取基础参数列表为空")
            return
        }
        carinfo_jscs_scqymc.text = if (TextUtils.isEmpty(data1.data.fjdcJscu.scqymc)) "/" else data1.data.fjdcJscu.scqymc //生产企业名称
        carinfo_jscs_scqydz.text = if (TextUtils.isEmpty(data1.data.fjdcJscu.scqydz)) "/" else data1.data.fjdcJscu.scqydz //生产企业地址
        carinfo_jscs_clywsb.text = if (TextUtils.isEmpty(data1.data.fjdcJscu.clywsb)) "/" else data1.data.fjdcJscu.clywsb //车辆英文商标
        carinfo_jscs_qdfs.text = if (TextUtils.isEmpty(data1.data.fjdcJscu.qdfs)) "/" else data1.data.fjdcJscu.qdfs //驱动方式
        carinfo_jscs_qhlzxj.text = if (TextUtils.isEmpty(data1.data.fjdcJscu.qhlzxj)) "/" else data1.data.fjdcJscu.qhlzxj //前后轮中心距
        carinfo_jscs_ccczsbbh.text = if (TextUtils.isEmpty(data1.data.fjdcJscu.cccbbh)) "/" else data1.data.fjdcJscu.cccbbh //CCC证书版本号
        carinfo_jscs_ddjxh.text = if (TextUtils.isEmpty(data1.data.fjdcJscu.ddjxh)) "/" else data1.data.fjdcJscu.ddjxh //电动机型号
        carinfo_jscs_ddjbm.text = if (TextUtils.isEmpty(data1.data.fjdcJscu.ddjbm)) "/" else data1.data.fjdcJscu.ddjbm //电动机编号
        carinfo_jscs_ddjxs.text = if (TextUtils.isEmpty(data1.data.fjdcJscu.ddjxs)) "/" else data1.data.fjdcJscu.ddjxs //电动机形式
        carinfo_jscs_edlxscgl.text = if (TextUtils.isEmpty(data1.data.fjdcJscu.edlxscgl)) "/" else data1.data.fjdcJscu.edlxscgl //额定输出功率
        carinfo_jscs_eddy.text = if (TextUtils.isEmpty(data1.data.fjdcJscu.eddy)) "/" else data1.data.fjdcJscu.eddy //额定电压
        carinfo_jscs_edzs.text = if (TextUtils.isEmpty(data1.data.fjdcJscu.edzs)) "/" else data1.data.fjdcJscu.edzs //额定转速
        carinfo_jscs_ddjscqy.text = if (TextUtils.isEmpty(data1.data.fjdcJscu.ddjscqy)) "/" else data1.data.fjdcJscu.ddjscqy //电动机生产企业
        carinfo_jscs_kzqxh.text = if (TextUtils.isEmpty(data1.data.fjdcJscu.kzqxh)) "/" else data1.data.fjdcJscu.kzqxh //控制器型号
        carinfo_jscs_kzqscqy.text = if (TextUtils.isEmpty(data1.data.fjdcJscu.kzqscqy)) "/" else data1.data.fjdcJscu.kzqscqy //控制器生产企业
        carinfo_jscs_xdcrl.text = if (TextUtils.isEmpty(data1.data.fjdcJscu.xdcrl)) "/" else data1.data.fjdcJscu.xdcrl //蓄电池容量
        carinfo_jscs_xdcxh.text = if (TextUtils.isEmpty(data1.data.fjdcJscu.xdcxh)) "/" else data1.data.fjdcJscu.xdcxh //蓄电池型号
        carinfo_jscs_xdclx.text = if (TextUtils.isEmpty(data1.data.fjdcJscu.xdclx)) "/" else data1.data.fjdcJscu.xdclx //蓄电池类型
        carinfo_jscs_xdcscqy.text = if (TextUtils.isEmpty(data1.data.fjdcJscu.xdcscqy)) "/" else data1.data.fjdcJscu.xdcscqy //蓄电池企业
        carinfo_jscs_qlltgg.text = if (TextUtils.isEmpty(data1.data.fjdcJscu.qlltgg)) "/" else data1.data.fjdcJscu.qlltgg //前轮规格
        carinfo_jscs_hlltgg.text = if (TextUtils.isEmpty(data1.data.fjdcJscu.hlltgg)) "/" else data1.data.fjdcJscu.hlltgg //后轮规格
        carinfo_jscs_qybhz.text = if (TextUtils.isEmpty(data1.data.fjdcJscu.qybhz)) "/" else data1.data.fjdcJscu.qybhz //欠压保护
        carinfo_jscs_glbhz.text = if (TextUtils.isEmpty(data1.data.fjdcJscu.glbhz)) "/" else data1.data.fjdcJscu.glbhz //过流保护
        carinfo_jscs_sqr.text = if (TextUtils.isEmpty(data1.data.fjdcJscu.sqr)) "/" else data1.data.fjdcJscu.sqr //申请人
        carinfo_jscs_bgldh.text = if (TextUtils.isEmpty(data1.data.fjdcJscu.bgldh)) "/" else data1.data.fjdcJscu.bgldh //百公里电耗

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is CarInfoActivity) {
            mActivity = context
        } else if (context is CarInfoActivity) {
            mActivity = context
        }
    }
}