package com.seatrend.xj.electricbicyclesalesystem.activity

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.common.Constants.Companion.CAR_CY
import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteUtils
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.HpHmEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.UserInfo
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.*
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import kotlinx.android.synthetic.main.activity_chayan.*
import kotlinx.android.synthetic.main.activity_chayan.ll_cphm
import kotlinx.android.synthetic.main.activity_chayan_entrance.*
import kotlinx.android.synthetic.main.bottom_button.*

/**
 * Created by ly on 2019/9/26 14:35
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class ChaYanActivity : BaseActivity(), NormalView {
    private var mNormalPresenter: NormalPresenter? = null
    val data1 = CarInfoActivity.mDataZcbm
    val data3c = CarInfoActivity.mData3C

    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        LoadingDialog.getInstance().dismissLoadDialog()
        if (Constants.SAVE_CY_MSG.equals(commonResponse.getUrl())) {
            CollectPhotoActivity.photoEntranceFlag = CAR_CY
            CollectPhotoActivity.jtxsFlag = rb_jtxs_ok.isChecked && et_zczl.text.toString().toFloat() <= 55.0 && et_zgss.text.toString().toFloat() <= 25.0
            intent.setClass(this, CollectPhotoActivity::class.java)
            startActivity(intent)
        }
        if (Constants.SYSTEM_PRODUCT_HPHM.equals(commonResponse.getUrl())) {
            val enity = GsonUtils.gson(commonResponse.responseString, HpHmEnity::class.java)
            if (!ObjectNullUtil.checknull(enity.data)) {
                showToast("号牌号码获取为空！")
                return
            }
            runOnUiThread {
                et_cphm.text = enity.data
            }
        }
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        LoadingDialog.getInstance().dismissLoadDialog()
        showErrorDialog(commonResponse.getResponseString())
    }

    override fun initView() {
        setPageTitle("车辆查验")
        mNormalPresenter = NormalPresenter(this)
        initData()
        bindEvent()
        getData()
    }

    private fun initData() {
        setSpinnerAdapter(sp_csys_a)
        setSpinnerAdapter(sp_csys_b)
        setSpinnerAdapter(sp_csys_c)

        rb_jtxs_ok.isChecked = true
        rb_dpxs_no.isChecked = true
    }

    //获取数据 网络的
    @SuppressLint("SetTextI18n", "ResourceAsColor")
    private fun getData() {

        if (data3c == null || data3c.data == null || data3c.data.threeCertificates == null || data3c.data.threeCertificates.data == null ) {
            showToast("获取技术参数失败")
            return
        }
        et_c.setText(data3c.data.threeCertificates.data.length)
        et_k.setText(data3c.data.threeCertificates.data.width)
        et_g.setText(data3c.data.threeCertificates.data.height)
        et_zczl.setText(data3c.data.threeCertificates.data.weight)
        et_zgss.setText(data3c.data.threeCertificates.data.maxSpeed)
//        et_qhlzxj.setText(data1!!.data.fjdcJscu.j)  //前后轮中心距
        if (!TextUtils.isEmpty(data3c.data.threeCertificates.data.vehicleManufacturer)) {
            et_zzc.setText(data3c.data.threeCertificates.data.vehicleManufacturer)
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
        if(data3c.data.threeCertificates.data.color != null && data3c.data.threeCertificates.data.color.contains("/")){
            var str = data3c.data.threeCertificates.data.color.split("/")
            if(str.size ==2){
                OtherUtils.setSpinnerToDmsm(str[0],sp_csys_a)
                OtherUtils.setSpinnerToDmsm(str[1],sp_csys_b)
            }else if(str.size == 3){
                OtherUtils.setSpinnerToDmsm(str[0],sp_csys_a)
                OtherUtils.setSpinnerToDmsm(str[1],sp_csys_b)
                OtherUtils.setSpinnerToDmsm(str[2],sp_csys_c)

            }
        }else{ //只有一个数据
            OtherUtils.setSpinnerToDmsm(data3c.data.threeCertificates.data.color,sp_csys_a)
        }

    }

    private fun setSpinnerAdapter(spinner: Spinner) {
        val adapter = ArrayAdapter<String>(this, R.layout.my_simple_spinner_item)
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
//            R.id.sp_dq -> {
//                adapter.clear()
//                val provinceList = CarHphmUtils.getProvince()
//                adapter.addAll(provinceList)
//                spinner.adapter = adapter
//            }
//            R.id.sp_hm -> {
//                adapter.clear()
//                val a_zList = CarHphmUtils.getA_Z()
//                adapter.addAll(a_zList)
//                spinner.adapter = adapter
//            }
        }
    }

    private fun bindEvent() {

        CheckBoxUtils.setListenerAndView(rb_dpxs_no, rb_dpxs_ok, ll_cphm)
        CheckBoxUtils.setListener(rb_jtxs_ok, rb_jtxs_no)

        et_cphm.transformationMethod = CarHphmUtils.TransInformation()

        bt_next.setOnClickListener {
            try {
                if (!CsysCompareSameUtils.compare3(sp_csys_a, sp_csys_b, sp_csys_c)) {
                    showToast("请正确输入车身颜色，不能两两选择相同颜色")
                    return@setOnClickListener
                }

                if (!CheckEditTxetUtils.checkEditextValuable(et_c, et_k, et_g, et_zczl, et_zgss, et_qhlzxj, et_zzc)) {
                    showToast("请填完所有信息再提交")
                    return@setOnClickListener
                }
                if (rb_dpxs_ok.isChecked && !CheckEditTxetUtils.checkEditextValuable(et_cphm)) {
                    showToast("请填完车牌号码信息")
                    return@setOnClickListener
                }

                val map = HashMap<String, String?>()
                map["lsh"] = data1!!.data.lsh
                map["xh"] = data1.data.xh
                map["cyr"] = UserInfo.XM
                map["cyrsfzmhm"] = UserInfo.SFZMHM//查验人身份证明号码
                map["czpt"] = Constants.CZPT //查验平台

                map["zcbm"] = intent.getStringExtra("zcbm")
                map["cybm"] = UserInfo.GLBM
                map["cphgzbh"] = if (data3c == null || data3c.data == null || data3c.data.threeCertificates.data == null ) "" else data3c.data.threeCertificates.data.qualificationCode

                map["ywlx"] = if (!TextUtils.isEmpty(intent.getStringExtra("ywlx"))) intent.getStringExtra("ywlx") else "" //为空传”“
                map["ywyy"] = if (!TextUtils.isEmpty(intent.getStringExtra("ywyy"))) intent.getStringExtra("ywyy") else "" //为空传”“
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
                map["jtgn"] = if (rb_jtxs_ok.isChecked) "1" else "0" // 脚踏功能 0没有 1 有
                map["shdp"] = if (rb_dpxs_ok.isChecked) "1" else "0" // 是否带牌销售 0否 1是
                map["cllx"] = if (rb_jtxs_ok.isChecked && et_zczl.text.toString().toFloat() <= 55.0 && et_zgss.text.toString().toFloat() <= 25.0) "1" else "2"// 车辆类型  1标准 2超
                if ("1" == UserInfo.GlobalParameter.DPBJ) {
                    map["ywyy"] = "是否带牌销售"
                } else {
                    map["ywyy"] = "正常登记"
                }

                if (rb_dpxs_ok.isChecked) {
                    var cphm = et_cphm.text.toString().toUpperCase()
                    if (!ObjectNullUtil.checknull(cphm)) {
                        showToast("请获取车牌号码")
                        return@setOnClickListener
                    }
                    map["cph"] = cphm
                }
                showLog("result [CY-POST] === " + GsonUtils.toJson(map))
                LoadingDialog.getInstance().showLoadDialog(this)
                mNormalPresenter!!.doNetworkTask(map, Constants.SAVE_CY_MSG)
            } catch (e: Exception) {
                showToast(e.message.toString())
            }
        }
        btn_hqhphm.setOnClickListener {
            val map = HashMap<String, String>()
            map["lybm"] = UserInfo.GLBM
            mNormalPresenter!!.doNetworkTask(map, Constants.SYSTEM_PRODUCT_HPHM)
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_chayan
    }
}