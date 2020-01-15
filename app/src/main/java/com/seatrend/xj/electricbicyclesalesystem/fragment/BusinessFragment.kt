package com.seatrend.xj.electricbicyclesalesystem.fragment


import android.content.Intent
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.activity.*
import com.seatrend.xj.electricbicyclesalesystem.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_business.*


/**
 * A simple [Fragment] subclass.
 */
class BusinessFragment : BaseFragment() {

    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_business, container, false)
    }

    override fun initView() {
        bindEvent()
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
        //档案归档
        rl_dagd.setOnClickListener {
            startActivity(Intent(context, ArchiveFileActivity::class.java))
        }
    }
}
