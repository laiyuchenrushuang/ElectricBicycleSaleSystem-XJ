package com.seatrend.xj.electricbicyclesalesystem.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.activity.*
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.BaseFragment
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.util.CheckViewUtil
import com.seatrend.xj.electricbicyclesalesystem.util.ViewShowUtils
import kotlinx.android.synthetic.main.activity_main_other.*
import kotlinx.android.synthetic.main.common_no_data.*
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by ly on 2019/10/25 13:40
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class HomeFragment : BaseFragment() {
    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_home, container, false)
    }

    override fun initView() {
        getShowData()
        bindEvent()
    }

    //需要展示的内容
    private fun getShowData() {
        ViewShowUtils.showGoneView(rl_clcy, rl_ywdj, rl_ywfh, rl_dagd, rl_ywtb,ll_no_data)
        var enity = MainOtherActivity.mLoginEnity
        if (enity != null && enity.data != null && enity.data.seaPrograms != null && enity.data.seaPrograms.size > 0) {
            for (db in enity.data.seaPrograms) {
                if (Constants.PMS_CY.equals(db.qxdm)) {
                    ViewShowUtils.showVisibleView(rl_clcy)
                }
                if (Constants.PMS_DJ.equals(db.qxdm)) {
                    ViewShowUtils.showVisibleView(rl_ywdj)
                }
                if (Constants.PMS_FH.equals(db.qxdm)) {
                    ViewShowUtils.showVisibleView(rl_ywfh)
                }
                if (Constants.PMS_GD.equals(db.qxdm)) {
                    ViewShowUtils.showVisibleView(rl_dagd)
                }
                if (Constants.PMS_TB.equals(db.qxdm)) {
                    ViewShowUtils.showVisibleView(rl_ywtb)
                }
            }
        }
        if(!CheckViewUtil.isShow(rl_clcy, rl_ywdj, rl_ywfh, rl_dagd, rl_ywtb)){ //false都不可见
            ll_no_data.visibility =View.VISIBLE
            tv_msg.text = "当前账号无权限使用该功能！"
        }
    }

    private fun bindEvent() {
        //车辆查验
        rl_clcy.setOnClickListener {
            startActivity(Intent(context, ChaYanEntranceActivity::class.java))
        }
        //业务登记
        rl_ywdj.setOnClickListener {
            startActivity(Intent(context, YWEntranceActivity::class.java))
        }
        //业务复核
        rl_ywfh.setOnClickListener {
            startActivity(Intent(context, YWCheckActivity::class.java))
        }
        //档案归档
        rl_dagd.setOnClickListener {
            startActivity(Intent(context, Archive2FileActivity::class.java))
        }
        //业务退办
        rl_ywtb.setOnClickListener {
            startActivity(Intent(context, YwTBSearchActivity::class.java))
        }
    }
}