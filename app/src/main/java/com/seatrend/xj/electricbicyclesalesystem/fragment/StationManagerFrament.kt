package com.seatrend.xj.electricbicyclesalesystem.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.activity.BusinessDetailsActivity
import com.seatrend.xj.electricbicyclesalesystem.activity.EmployeeActivity
import com.seatrend.xj.electricbicyclesalesystem.activity.MainOtherActivity
import com.seatrend.xj.electricbicyclesalesystem.activity.WarningMessageActivity
import com.seatrend.xj.electricbicyclesalesystem.common.BaseFragment
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.util.CheckViewUtil
import com.seatrend.xj.electricbicyclesalesystem.util.ViewShowUtils
import kotlinx.android.synthetic.main.common_no_data.*
import kotlinx.android.synthetic.main.fragment_station_manager.*

/**
 * Created by ly on 2019/10/25 13:43
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class StationManagerFrament: BaseFragment() {
    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_station_manager, container, false)
    }

    override fun initView() {
        getShowData()
        bindEvent()
    }

    //需要展示的内容
    private fun getShowData() {
        ViewShowUtils.showGoneView(rl_ywyj,rl_ygba,rl_ywtj,ll_no_data)
        var enity = MainOtherActivity.mLoginEnity
        if (enity != null && enity.data != null && enity.data.seaPrograms != null && enity.data.seaPrograms.size > 0) {
            for (db in enity.data.seaPrograms) {
                if (Constants.PMS_YJ.equals(db.qxdm)) {
                    ViewShowUtils.showVisibleView(rl_ywyj)
                }
                if (Constants.PMS_TJ.equals(db.qxdm)) {
                    ViewShowUtils.showVisibleView(rl_ywtj)
                }
                if (Constants.PMS_BA.equals(db.qxdm)) {
                    ViewShowUtils.showVisibleView(rl_ygba)
                }
            }
        }
        if(!CheckViewUtil.isShow(rl_ywyj,rl_ygba,rl_ywtj)){ //false都不可见
            ll_no_data.visibility =View.VISIBLE
            tv_msg.text = "当前账号无权限使用该功能！"
        }
    }

    private fun bindEvent() {
        rl_ywyj.setOnClickListener {
            startActivity(Intent(context, WarningMessageActivity::class.java))
        }
        rl_ywtj.setOnClickListener {
            startActivity(Intent(context, BusinessDetailsActivity::class.java))
        }
        rl_ygba.setOnClickListener {
            startActivity(Intent(context, EmployeeActivity::class.java))
        }
    }
}