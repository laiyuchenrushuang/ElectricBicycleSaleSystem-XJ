package com.seatrend.xj.electricbicyclesalesystem.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseFragment
import com.seatrend.xj.electricbicyclesalesystem.util.CheckBoxUtils
import kotlinx.android.synthetic.main.fragment_cy_project_judge.*

/**
 * Created by ly on 2020/4/3 10:52
 */
class CYProjectJudgeFragment :BaseFragment(){
    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_cy_project_judge, container, false)
    }

    override fun initView() {

        setInitView()
    }

    private fun setInitView() {
        //初始化 checkbox
        setCheckBoxDefault(cb_01_ok,cb_02_ok,cb_04_ok,cb_05_ok,cb_06_ok,cb_07_ok,cb_08_ok,cb_09_ok,cb_10_ok,cb_11_ok)
        CheckBoxUtils.setListener(cb_01_ok, cb_01_no)
        CheckBoxUtils.setListener(cb_02_ok, cb_02_no)
        CheckBoxUtils.setListener(cb_04_ok, cb_04_no)
        CheckBoxUtils.setListener(cb_05_ok, cb_05_no)
        CheckBoxUtils.setListener(cb_06_ok, cb_06_no)
        CheckBoxUtils.setListener(cb_07_ok, cb_07_no)
        CheckBoxUtils.setListener(cb_08_ok, cb_08_no)
        CheckBoxUtils.setListener(cb_09_ok, cb_09_no)
        CheckBoxUtils.setListener(cb_10_ok, cb_10_no)
        CheckBoxUtils.setListener(cb_11_ok, cb_11_no)
    }
}