package com.seatrend.xj.electricbicyclesalesystem.fragment

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.activity.CarInfoByCyActivity
import com.seatrend.xj.electricbicyclesalesystem.activity.CarInfoByCyActivity.Companion.entranceFlag
import com.seatrend.xj.electricbicyclesalesystem.common.BaseFragment
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.AllBikeMsgEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.YWRegisterEntranceEnity
import com.seatrend.xj.electricbicyclesalesystem.util.*
import kotlinx.android.synthetic.main.fragment_register_info.*

/**
 * Created by ly on 2019/9/29 13:57
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class RegisterInfoFragment : BaseFragment() {
    var enity: AllBikeMsgEnity? = null

    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View {
         return inflater!!.inflate(R.layout.fragment_register_info, container, false)
    }

    override fun initView() {
        getdata()
        setLongTextview(tv_xxdz)
        setLongTextview(tv_yj_xxdz)
    }

    private fun getdata() {
        try {
            enity = activity.intent.getSerializableExtra("all_data") as AllBikeMsgEnity
            if (entranceFlag == Constants.YWTB || entranceFlag == Constants.CAR_ZC) { //注册登记或者是退办过来查询的
                tv_cllx.text = CllxUtils.getCllxDMSM(enity!!.data.checkData.cllx)
                tv_csys.text = CsysUtils.getCsysMc(enity!!.data.checkData.csys)
                var cphm = enity!!.data.checkData.cph
                var cphm1 = enity!!.data.fjdcBusiness.cph

                if (!TextUtils.isEmpty(cphm1)) {
                    cphm = cphm1
                }
                tv_cphm.text = if(TextUtils.isEmpty(cphm)) "/" else cphm
            } else {
                tv_cllx.text = CllxUtils.getCllxDMSM(enity!!.data.fjdcBusiness.cllx)
                tv_cphm.text = enity!!.data.fjdcBusiness.cph
                tv_csys.text = CsysUtils.getCsysMc(enity!!.data.fjdcBusiness.csys)
            }


            tv_hdfs.text = if (enity!!.data.fjdcBusiness == null || TextUtils.isEmpty(enity!!.data.fjdcBusiness.hdfs)) "/" else DMZUtils.getDMSM(Constants.HDFS, enity!!.data.fjdcBusiness.hdfs)
            tv_llzm.text = if (enity!!.data.fjdcBusiness == null || TextUtils.isEmpty(enity!!.data.fjdcBusiness.llzm)) "/" else DMZUtils.getDMSM(Constants.LLZM, enity!!.data.fjdcBusiness.llzm)
            tv_syxz.text = if (enity!!.data.fjdcBusiness == null || TextUtils.isEmpty(enity!!.data.fjdcBusiness.syxz)) "/" else DMZUtils.getDMSM(Constants.SYXZ, enity!!.data.fjdcBusiness.syxz)
            tv_clyt.text = if (enity!!.data.fjdcBusiness == null || TextUtils.isEmpty(enity!!.data.fjdcBusiness.clyt)) "/" else DMZUtils.getDMSM(Constants.CLYT, enity!!.data.fjdcBusiness.clyt)
            tv_hqrq.text = if (enity!!.data.fjdcBusiness == null || enity!!.data.fjdcBusiness.hqrq.toInt() == 0) "/" else StringUtils.longToStringDataNoHour(enity!!.data.fjdcBusiness.hqrq)


            tv_sfzmc.text = if (enity!!.data.fjdcBusiness == null || TextUtils.isEmpty(enity!!.data.fjdcBusiness.sfzmmc)) "/" else DMZUtils.getDMSM(Constants.SFZMMC, enity!!.data.fjdcBusiness.sfzmmc)
            tv_sfz.text = if (enity!!.data.fjdcBusiness == null || TextUtils.isEmpty(enity!!.data.fjdcBusiness.sfzmhm)) "/" else enity!!.data.fjdcBusiness.sfzmhm
            tv_xm.text = if (enity!!.data.fjdcBusiness == null || TextUtils.isEmpty(enity!!.data.fjdcBusiness.syrmc)) "/" else enity!!.data.fjdcBusiness.syrmc
            tv_lxdh.text = if (enity!!.data.fjdcBusiness == null || TextUtils.isEmpty(enity!!.data.fjdcBusiness.lxdh)) "/" else enity!!.data.fjdcBusiness.lxdh
            tv_xzqh.text = if (enity!!.data.fjdcBusiness == null || TextUtils.isEmpty(enity!!.data.fjdcBusiness.djxzqh)) "/" else DMZUtils.getDMSM(Constants.XSQY, enity!!.data.fjdcBusiness.djxzqh)
            tv_xxdz.text = if (enity!!.data.fjdcBusiness == null || TextUtils.isEmpty(enity!!.data.fjdcBusiness.djxxdz)) "/" else enity!!.data.fjdcBusiness.djxxdz

            tv_yj_xzqh.text = if (enity!!.data.fjdcBusiness == null || TextUtils.isEmpty(enity!!.data.fjdcBusiness.lxdzxzqh)) "/" else DMZUtils.getDMSM(Constants.XSQY, enity!!.data.fjdcBusiness.lxdzxzqh)
            tv_yj_xxdz.text = if (enity!!.data.fjdcBusiness == null || TextUtils.isEmpty(enity!!.data.fjdcBusiness.lxxxdz)) "/" else enity!!.data.fjdcBusiness.lxxxdz
        } catch (e: Exception) {
            showToast(e.message.toString())
        }
    }
}