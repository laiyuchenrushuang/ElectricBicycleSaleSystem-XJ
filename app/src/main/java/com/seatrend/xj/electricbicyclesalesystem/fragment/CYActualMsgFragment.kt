package com.seatrend.xj.electricbicyclesalesystem.fragment

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.activity.CarInspectionActivity
import com.seatrend.xj.electricbicyclesalesystem.common.BaseFragment
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteUtils
import com.seatrend.xj.electricbicyclesalesystem.entity.*
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.*
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.fragment_cy_actual_msg.*

/**
 * Created by ly on 2020/4/3 10:52
 */
class CYActualMsgFragment : BaseFragment(), NormalView {


    private var data1: CYEntranceEnity? = null
    private var data3c: ThreeCEnity? = null

    private var mNormalPresenter: NormalPresenter? = null

    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_cy_actual_msg, container, false)
    }

    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {

        dismissLoadingDialog()
        if (Constants.SYSTEM_PRODUCT_HPHM.equals(commonResponse.getUrl())) {
            val enity = GsonUtils.gson(commonResponse.responseString, HpHmEnity::class.java)
            if (!ObjectNullUtil.checknull(enity.data)) {
                showToast("号牌号码获取为空！")
                return
            }
            activity.runOnUiThread {
                et_cphm.text = enity.data
                btn_hqhphm.visibility = View.GONE
            }
        }
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        showErrorDialog(commonResponse.getResponseString())
    }


    override fun initView() {
        mNormalPresenter = NormalPresenter(this)
        setSpinner()
        setCheckBox()
        getData()
        bindEvent()
    }

    private fun bindEvent() {
        CheckBoxUtils.setListenerAndView(rb_dpxs_no, rb_dpxs_ok, ll_cphm)
        et_ddjbh.transformationMethod = CarHphmUtils.TransInformation()
        et_zcbm.transformationMethod = CarHphmUtils.TransInformation()

        btn_hqhphm.setOnClickListener {
            showLoadingDialog()
            val map = HashMap<String, String>()
            map["lybm"] = UserInfo.GLBM
            mNormalPresenter!!.doNetworkTask(map, Constants.SYSTEM_PRODUCT_HPHM)
        }
    }

    private fun setCheckBox() {
        setCheckBoxDefault(rb_dpxs_no)
//        CheckBoxUtils.setListener(rb_dpxs_ok, rb_dpxs_no)
    }

    @SuppressLint("ResourceAsColor")
    private fun getData() {


        try {
            data3c = (activity as CarInspectionActivity).get3cData()
            data1 = (activity as CarInspectionActivity).getJcData()


            if (!(data3c?.data != null && data3c!!.data.threeCertificates != null && data3c!!.data.threeCertificates.data != null)) {
                showToast("获取ccc参数失败")
                return
            }
//            if (!(data1?.data != null && data1!!.data.fjdcJscu != null && data3c!!.data.threeCertificates.data != null)) {
//                showToast("获取技术参数参数失败")
//                return
//            }
            et_c.setText(data3c!!.data.threeCertificates.data.length)
            et_k.setText(data3c!!.data.threeCertificates.data.width)
            et_g.setText(data3c!!.data.threeCertificates.data.height)
            et_zczl.setText(data3c!!.data.threeCertificates.data.weight)
            et_zgss.setText(data3c!!.data.threeCertificates.data.maxSpeed)

            et_qhlzxj.setText(if (data1 == null || data1!!.data == null || data1!!.data.fjdcJscu == null) "" else data1!!.data.fjdcJscu.qhlzxj)  //前后轮中心距
            et_ddjbh.setText(if (data1 == null || data1!!.data == null || data1!!.data.fjdcJscu == null) "" else data1!!.data.fjdcJscu.ddjbm)  //电动机编号

            if (!TextUtils.isEmpty(data3c!!.data.threeCertificates.data.vehicleManufacturer)) {
                et_zzc.setText(data3c!!.data.threeCertificates.data.vehicleManufacturer)
                et_zzc.isFocusable = false
            } else {
                et_zzc.isFocusable = true
            }

            if ("1" == UserInfo.GlobalParameter.DPBJ) {
                ll_dpxs.visibility = View.VISIBLE
            } else {
                ll_dpxs.visibility = View.GONE
            }

            if ("1" != UserInfo.GlobalParameter.SCBJ) {
                et_c.isEnabled = false
                et_c.setTextColor(R.color.gray)

                et_k.isEnabled = false
                et_k.setTextColor(R.color.gray)

                et_g.isEnabled = false
                et_g.setTextColor(R.color.gray)

                et_zczl.isEnabled = false
                et_zczl.setTextColor(R.color.gray)

                et_zgss.isEnabled = false
                et_zgss.setTextColor(R.color.gray)

                tv_scckg.setTextColor(R.color.gray)
                tv_zczl.setTextColor(R.color.gray)
                tv_sczgss.setTextColor(R.color.gray)
            }

            //车身颜色
            if (data3c!!.data.threeCertificates.data.color != null && data3c!!.data.threeCertificates.data.color.contains("/")) {
                var str = data3c!!.data.threeCertificates.data.color.split("/")
                if (str.size == 2) {
                    OtherUtils.setSpinnerToDmsm(str[0], sp_csys_a)
                    OtherUtils.setSpinnerToDmsm(str[1], sp_csys_b)
                } else if (str.size == 3) {
                    OtherUtils.setSpinnerToDmsm(str[0], sp_csys_a)
                    OtherUtils.setSpinnerToDmsm(str[1], sp_csys_b)
                    OtherUtils.setSpinnerToDmsm(str[2], sp_csys_c)

                }
            } else { //只有一个数据
                OtherUtils.setSpinnerToDmsm(data3c!!.data.threeCertificates.data.color, sp_csys_a)
            }

            //整车编码
            et_zcbm.setText(if (TextUtils.isEmpty(activity.intent.getStringExtra("zcbm"))) data1!!.data.threeCertificates.zcbm else activity.intent.getStringExtra("zcbm")) //整车编码

        } catch (e: Exception) {
            showToast(e.message.toString())
        }
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
                    adapter.add("$dmz:$dmsm1")
                }
                spinner.adapter = adapter
            }
        }
    }

    fun getSendData(): HashMap<String, String> {

        if (!CsysCompareSameUtils.compare3(sp_csys_a, sp_csys_b, sp_csys_c)) {
            showToast("请正确输入车身颜色，不能两两选择相同颜色")
            return null!!
        }

        if (!CheckEditTxetUtils.checkEditextValuable(et_c, et_k, et_g, et_zczl, et_zgss, et_qhlzxj, et_zzc)) {
            showToast("请填完所有信息再提交")
            return null!!
        }
        var map = HashMap<String, String>()
        map["ddjbh"] = et_ddjbh.text.toString().toUpperCase()
        map["zcbm"] = et_zcbm.text.toString().toUpperCase()
        map["lsh"] = data1!!.data.lsh
        map["xh"] = data1!!.data.xh
        map["cyr"] = UserInfo.XM
        map["cyrsfzmhm"] = UserInfo.SFZMHM//查验人身份证明号码
        map["czpt"] = Constants.CZPT //查验平台

        map["zcbm"] = activity.intent.getStringExtra("zcbm")
        map["cybm"] = UserInfo.GLBM
        map["cphgzbh"] = if (data3c == null || data3c!!.data == null || data3c!!.data.threeCertificates.data == null) "" else data3c!!.data.threeCertificates.data.qualificationCode

        map["ywlx"] = if (!TextUtils.isEmpty(activity.intent.getStringExtra("ywlx"))) activity.intent.getStringExtra("ywlx") else "" //为空传”“
        map["ywyy"] = if (!TextUtils.isEmpty(activity.intent.getStringExtra("ywyy"))) activity.intent.getStringExtra("ywyy") else "" //为空传”“
        map["csys"] = CsysUtils.getSpCommitString(sp_csys_a, sp_csys_b, sp_csys_c)
        if ("1" == UserInfo.GlobalParameter.SCBJ) {
            map["scc"] = et_c.text.toString()
            map["sck"] = et_k.text.toString()
            map["scg"] = et_g.text.toString()

            map["sczgcs"] = et_zgss.text.toString()
            map["sczbzl"] = et_zczl.text.toString()
            map["scqhlzxj"] = et_qhlzxj.text.toString()
        }

        map["clzzs"] = et_zzc.text.toString()
        map["shdp"] = if (rb_dpxs_ok.isChecked) "1" else "0" // 是否带牌销售 0否 1是
        map["cllx"] = if (et_zczl.text.toString().toFloat() <= 55.0 && et_zgss.text.toString().toFloat() <= 25.0) "1" else "2"// 车辆类型  1标准 2超
        if ("1" == UserInfo.GlobalParameter.DPBJ) {
            map["ywyy"] = "是否带牌销售"
        } else {
            map["ywyy"] = "正常登记"
        }

        if (rb_dpxs_ok.isChecked) {
            var cphm = et_cphm.text.toString().toUpperCase()
            if (!ObjectNullUtil.checknull(cphm)) {
                showToast("请获取号牌号码")
                return null!!
            }
            map["cph"] = cphm
        }
        showLog("result [CY-SC-POST] === " + GsonUtils.toJson(map))
        return map
    }
}