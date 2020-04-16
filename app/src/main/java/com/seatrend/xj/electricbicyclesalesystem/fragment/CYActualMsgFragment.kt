package com.seatrend.xj.electricbicyclesalesystem.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseFragment
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteUtils
import com.seatrend.xj.electricbicyclesalesystem.util.CheckBoxUtils
import kotlinx.android.synthetic.main.fragment_cy_actual_msg.*

/**
 * Created by ly on 2020/4/3 10:52
 */
class CYActualMsgFragment :BaseFragment(){
    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_cy_actual_msg, container, false)
    }

    override fun initView() {

        setSpinner()
        setCheckBox()



        getData()
    }

    private fun setCheckBox() {
        setCheckBoxDefault(rb_dpxs_ok)
        CheckBoxUtils.setListener(rb_dpxs_ok,rb_dpxs_no)
    }

    private fun getData() {

    }

    private fun setSpinner() {
        setSpinnerAdapter(sp_csys_a)
        setSpinnerAdapter(sp_csys_b)
        setSpinnerAdapter(sp_csys_c)
    }

    private fun setSpinnerAdapter(spinner: Spinner) {
        val adapter = ArrayAdapter<String>(context, R.layout.my_simple_spinner_item)
        adapter.setDropDownViewResource(R.layout.item_spinner__down_common)
        when (spinner.id) {
            R.id.sp_csys_a, R.id.sp_csys_b, R.id.sp_csys_c -> {
                adapter.clear()
                adapter.add("")
                val sfzmmcList = CodeTableSQLiteUtils.queryByDMLB(Constants.CSYS)
                for (db in sfzmmcList) {
                    val dmz = db.dmz
                    val dmsm1 = db.dmsm1
                    adapter.add(dmz + ":" + dmsm1)
                }
                spinner.adapter = adapter
            }
        }
    }

}