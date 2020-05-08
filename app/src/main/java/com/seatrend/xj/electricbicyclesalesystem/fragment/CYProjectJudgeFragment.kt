package com.seatrend.xj.electricbicyclesalesystem.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.adpater.CheckDataCYAdapter
import com.seatrend.xj.electricbicyclesalesystem.common.BaseFragment
import com.seatrend.xj.electricbicyclesalesystem.entity.CYEntranceEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.JudgeProjectEnity
import com.seatrend.xj.electricbicyclesalesystem.util.GsonUtils
import kotlinx.android.synthetic.main.fragment_cy_project_judge.*

/**
 * Created by ly on 2020/4/3 10:52
 */
class CYProjectJudgeFragment : BaseFragment(), CheckDataCYAdapter.DataChange {


    private var mCheckDataAdapter: CheckDataCYAdapter? = null
    var data1: CYEntranceEnity? = null
    var list = ArrayList<JudgeProjectEnity>()

    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_cy_project_judge, container, false)
    }

    override fun initView() {
        initRecycleView()
    }

    private fun initRecycleView() {
        m_recycler_view!!.layoutManager = LinearLayoutManager(context)
        mCheckDataAdapter = CheckDataCYAdapter(context, list)
        mCheckDataAdapter!!.setListener(this, false)
        m_recycler_view.adapter = mCheckDataAdapter
    }

    fun getSendData(): HashMap<String, String> {
        var map = HashMap<String, String>()


        map["ywlx"] = activity.intent.getStringExtra("ywlx") // 业务类型
        map["ywyy"] = activity.intent.getStringExtra("ywyy")//业务原因   ----  查询照片类型的回调

        map["lsh"] = data1!!.data.lsh
        map["xh"] = data1!!.data.xh
        for (index in 0 until list.size) {

            //代码值ABC...
            if ("A" == list[index].content.split(":")[0]) {
                map["wgcl"] = list[index].order
            }

            if ("B" == list[index].content.split(":")[0]) {
                map["wkcc"] = list[index].order
            }
            if ("C" == list[index].content.split(":")[0]) {
                map["csys"] = list[index].order
            }
            if ("D" == list[index].content.split(":")[0]) {
                map["zcbm"] = list[index].order
            }
            if ("E" == list[index].content.split(":")[0]) {
                map["ddjbm"] = list[index].order
            }
            if ("F" == list[index].content.split(":")[0]) {
                map["zczl"] = list[index].order
            }
            if ("J" == list[index].content.split(":")[0]) {
                map["jtxsnl"] = list[index].order
            }
            if ("M" == list[index].content.split(":")[0]) {
                map["xdcdy"] = list[index].order
            }
            if ("N" == list[index].content.split(":")[0]) {
                map["hsj"] = list[index].order
            }
            if ("P" == list[index].content.split(":")[0]) {
                map["lb"] = list[index].order
            }
            if ("Q" == list[index].content.split(":")[0]) {
                map["zm"] = list[index].order
            }
        }
        showLog("result [CY-PD-POST] === " + GsonUtils.toJson(map))
        return map
    }

    fun setGetData(data: ArrayList<JudgeProjectEnity>) {
        this.list = data
    }

    fun setGetData(data: CYEntranceEnity) {
        this.data1 = data
    }

    override fun DataChangeListener(data: ArrayList<JudgeProjectEnity>) {
        this.list = data
    }

    override fun getCsysData(sp_csys_a: Spinner, sp_csys_b: Spinner, sp_csys_c: Spinner) {
    }
}