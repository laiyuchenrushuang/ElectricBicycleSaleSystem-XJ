package com.seatrend.xj.electricbicyclesalesystem.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.widget.Spinner
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.adpater.CheckDataCYAdapter
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteUtils
import com.seatrend.xj.electricbicyclesalesystem.entity.*
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.CsysUtils
import com.seatrend.xj.electricbicyclesalesystem.util.GsonUtils
import com.seatrend.xj.electricbicyclesalesystem.util.StringUtils
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.activity_judge_project.*
import kotlinx.android.synthetic.main.bottom_button.*
import java.lang.StringBuilder

class CYProjectJudgeActivity : BaseActivity(), CheckDataCYAdapter.DataChange, NormalView {


    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {

        dismissLoadingDialog()

        if (Constants.SAVE_CY_PD == commonResponse.url) {
            count++
            var enity = GsonUtils.gson(commonResponse.responseString, ServicePhotoCamebackEnity::class.java)

            if (enity != null && enity.data != null && enity.data.size > 0) {
                val bundle = Bundle()
                bundle.putParcelable("photo_list", enity)
                intent.putExtras(bundle)
            }
        }

        if (Constants.SAVE_CY_MSG == commonResponse.url) {
            count++
        }
        showLog("  bgCsysBj = $bgCsysBj  count = $count")
        if (bgCsysBj) {
            if (count == 2) {
                val flag = getJudeProject()
                CollectPhotoActivity.photoEntranceFlag = Constants.CAR_CY
                intent.putExtra("photoentranceflag", Constants.CAR_CY)
                CollectPhotoActivity.jtxsFlag = flag
                intent.putExtra("jtxsflag", flag)
                intent.setClass(this, CollectPhotoActivity::class.java)
                startActivity(intent)
            }

        } else {
            val flag = getJudeProject()
            CollectPhotoActivity.photoEntranceFlag = Constants.CAR_CY
            intent.putExtra("photoentranceflag", Constants.CAR_CY)
            CollectPhotoActivity.jtxsFlag = flag
            intent.putExtra("jtxsflag", flag)
            intent.setClass(this, CollectPhotoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        count--
        dismissLoadingDialog()
        showErrorDialog(commonResponse.getResponseString())
    }


    var bgCsysBj: Boolean = false  //不是变更车身颜色标记
    private var mCheckDataAdapter: CheckDataCYAdapter? = null
    var data1: CYEntranceEnity? = null
    var list = ArrayList<JudgeProjectEnity>()
    var csys: String = ""
    var count = 0
    private var mNormalPresenter: NormalPresenter? = null

    override fun initView() {
        setPageTitle(resources.getString(R.string.home_clcy))
        //变更车身颜色
        if (Constants.D == intent.getStringExtra("ywlx") && Constants.BG_DC == intent.getStringExtra("ywyy")) {
            bgCsysBj = true
        }
        data1 = intent.getSerializableExtra("all_data") as CYEntranceEnity
        mNormalPresenter = NormalPresenter(this)
        initRecycleView()
        getData()
        bindEvent()
    }

    private fun initRecycleView() {
        m_recycler_view!!.layoutManager = LinearLayoutManager(this)
        mCheckDataAdapter = CheckDataCYAdapter(this, list)
        mCheckDataAdapter!!.setListener(this, bgCsysBj)
        m_recycler_view.adapter = mCheckDataAdapter
    }

    //判定项目是否是全是OK
    private fun getJudeProject(): Boolean {
        for (enity: JudgeProjectEnity in list) {
            if ("0" == enity.order) {
                return false
            }
        }
        return true
    }

    private fun bindEvent() {
        bt_next.setOnClickListener {
            count = 0
            showLoadingDialog()
            if (bgCsysBj) {  //需要传递变更车身颜色
                showLog("  csys  = $csys")

                if (csys.contains(",")) {
                    val newCsys = csys.replace(",", "")
                    if (StringUtils.containRepeatChar(newCsys)) {
                        showToast("请正确输入车身颜色，不能两两选择相同颜色")
                        return@setOnClickListener
                    }
                }
                if (csys == "") {
                    showToast("请输入车身颜色")
                    return@setOnClickListener
                }
                postCsysData()
            }
            postPdData()
        }
    }

    private fun postCsysData() {
        var map = HashMap<String, String>()
        map["lsh"] = data1!!.data.lsh
        map["xh"] = data1!!.data.xh
        map["cyr"] = UserInfo.XM
        map["cyrsfzmhm"] = UserInfo.SFZMHM//查验人身份证明号码
        map["czpt"] = Constants.CZPT //查验平台
        map["csys"] = csys
        mNormalPresenter!!.doNetworkTask(map, Constants.SAVE_CY_MSG)
    }

    private fun postPdData() {
        var map = HashMap<String, String>()

        map["ywlx"] = intent.getStringExtra("ywlx") // 业务类型
        map["ywyy"] = intent.getStringExtra("ywyy")//业务原因   ----  查询照片类型的回调

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

    override fun getCsysData(sp_csys_a: Spinner, sp_csys_b: Spinner, sp_csys_c: Spinner) {
        var sb = StringBuilder()
        if (sp_csys_a.selectedItem != null && !TextUtils.isEmpty(sp_csys_a.selectedItem.toString())) {
            sb.append(sp_csys_a.selectedItem.toString().split(":")[0] + ",")
        }
        if (sp_csys_b.selectedItem != null && !TextUtils.isEmpty(sp_csys_b.selectedItem.toString())) {
            sb.append(sp_csys_b.selectedItem.toString().split(":")[0] + ",")
        }
        if (sp_csys_c.selectedItem != null && !TextUtils.isEmpty(sp_csys_c.selectedItem.toString())) {
            sb.append(sp_csys_c.selectedItem.toString().split(":")[0] + ",")
        }
        if (sb.toString() != "") {
            this.csys = sb.toString().substring(0, sb.toString().length - 1)
        } else {
            this.csys = ""
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        count = 0
        bgCsysBj = false
    }

    override fun getLayout(): Int {
        return R.layout.activity_judge_project
    }

}
