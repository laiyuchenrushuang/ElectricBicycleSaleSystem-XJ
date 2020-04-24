package com.seatrend.xj.electricbicyclesalesystem.activity

import android.support.v7.widget.LinearLayoutManager
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.adpater.CheckDataCYAdapter
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteUtils
import com.seatrend.xj.electricbicyclesalesystem.entity.CYEntranceEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.JudgeProjectEnity
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.GsonUtils
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.activity_judge_project.*
import kotlinx.android.synthetic.main.bottom_button.*

class CYProjectJudgeActivity : BaseActivity(), CheckDataCYAdapter.DataChange, NormalView {
    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        val flag = getJudeProject()
        CollectPhotoActivity.photoEntranceFlag = Constants.CAR_CY
        intent.putExtra("photoentranceflag", Constants.CAR_CY)
        CollectPhotoActivity.jtxsFlag = flag
        intent.putExtra("jtxsflag", flag)
        intent.setClass(this, CollectPhotoActivity::class.java)
        startActivity(intent)
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        showErrorDialog(commonResponse.getResponseString())
    }


    private var mCheckDataAdapter: CheckDataCYAdapter? = null
    var data1: CYEntranceEnity? = null
    var list = ArrayList<JudgeProjectEnity>()
    private var mNormalPresenter: NormalPresenter? = null

    override fun initView() {
        setPageTitle(resources.getString(R.string.home_clcy))

        data1 = intent.getSerializableExtra("all_data") as CYEntranceEnity

        mNormalPresenter = NormalPresenter(this)
        initRecycleView()
        getData()
        bindEvent()
    }

    private fun initRecycleView() {
        m_recycler_view!!.layoutManager = LinearLayoutManager(this)
        mCheckDataAdapter = CheckDataCYAdapter(this, list)
        mCheckDataAdapter!!.setListener(this)
        m_recycler_view.adapter = mCheckDataAdapter
    }

    //判定项目是否是全是OK
    private fun getJudeProject(): Boolean {
        var map = list
        for (enity: JudgeProjectEnity in list) {
            if ("0" == enity.order) {
                return false
            }
        }
        return true
    }

    private fun bindEvent() {
        bt_next.setOnClickListener {
            postData()
        }
    }

    private fun postData() {
        var map = HashMap<String, String>()

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
        mNormalPresenter!!.doNetworkTask(map, Constants.SAVE_CY_PD)
    }

    private fun getData() {
        val sfzmmcList = CodeTableSQLiteUtils.queryByDMLB(Constants.PD_LIST)
        for (db in sfzmmcList) {
            var enity = JudgeProjectEnity()
            val dmz = db.dmz
            val dmsm1 = db.dmsm1
            enity.content = "$dmz:$dmsm1"
            enity.order = "1"
            list.add(enity)
        }
    }

    override fun DataChangeListener(data: ArrayList<JudgeProjectEnity>) {
        this.list = data
    }

    override fun getLayout(): Int {
        return R.layout.activity_judge_project
    }

}
