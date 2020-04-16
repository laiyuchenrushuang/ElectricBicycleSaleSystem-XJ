package com.seatrend.xj.electricbicyclesalesystem.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseFragment

/**
 * Created by ly on 2020/4/7 9:34
 */
class MainParameterFragment :BaseFragment(){
    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_main_para,container,false)
    }

    override fun initView() {

    }
}