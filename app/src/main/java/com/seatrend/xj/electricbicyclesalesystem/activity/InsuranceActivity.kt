package com.seatrend.xj.electricbicyclesalesystem.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.*
import com.seatrend.xj.electricbicyclesalesystem.persenter.CollectBicyclePersenter
import com.seatrend.xj.electricbicyclesalesystem.util.*
import com.seatrend.xj.electricbicyclesalesystem.view.CollectBicycleView
import kotlinx.android.synthetic.main.activity_insurance.*
import org.json.JSONObject
import java.lang.Exception

class InsuranceActivity : BaseActivity(), CollectBicycleView {
    companion object {
        var dzpzFlag: Boolean = true //true 是需要电子凭证 false 不需要
    }

    var mDeviceScanUtils: DeviceScanUtils? = null

    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        if (Constants.UPDATA_LS_ZT.equals(commonResponse.getUrl())) {
            intent.setClass(this, RemindHPBFActivity::class.java)
            startActivity(intent)
        }
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        LoadingDialog.getInstance().dismissLoadDialog()
        showErrorDialog(commonResponse.getResponseString())
    }

    /**
     * 保险数据详情
     */
    fun getInsuranceDetails() {

    }

    private fun setInsuranceMessage(data: String?) {
        try {
            val jb = JSONObject(data)
            tv_bdh.text = jb.optString("BDH")
            tv_qbrq.text = jb.optString("BXKSRQ")
            tv_zbrq.text = jb.optString("BXJSRQ")
            tv_bf.text = jb.optString("BF") + "元"
            tv_bxrmc.text = jb.optString("SYR")
            tv_bxrsfzh.text = jb.optString("SFZMHM")
            tv_cph.text = jb.optString("PZH")
            tv_cpbm.text = jb.optString("BXCPDM")
            tv_bxgsmc.text = jb.optString("BXGSMC")
            tv_lxdh.text = jb.optString("LXDH")
        } catch (e: Exception) {
            showErrorDialog(e.toString())
        }
    }

    private val mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val msgString = msg.obj as String
            when (msg.what) {
                DeviceScanUtils.OTHER_CODE -> if (!TextUtils.isEmpty(msgString)) {
                    analysisInsuranceData(msgString)
                }
            }
        }
    }

    /**
     * 解析保险数据
     */
    fun analysisInsuranceData(qrcode: String) {

        val map = HashMap<String, String?>()
        map.put("code", qrcode)
        showLoadingDialog()
        mCollectBicyclePersenter!!.doNetworkTask(map, Constants.DECRYPT_INSURANCE_MSG)
    }

    private fun setSpinnerAdapter(list: List<String>) {
        val adapter = ArrayAdapter<String>(this, R.layout.my_simple_spinner_item)
        adapter.setDropDownViewResource(R.layout.item_spinner__down_common)
        adapter.addAll(list)
        sp_bxmc.adapter = adapter
    }

    private var mCollectBicyclePersenter: CollectBicyclePersenter? = null
    override fun initView() {
        setPageTitle(getString(R.string.bxxx))
        mCollectBicyclePersenter = CollectBicyclePersenter(this)
        bindEvent()
        rb_bgm.isChecked = true
//    getBDType()
    }

    /**
     * 获取二维码(OK)
     */
    private fun getCodeImage() {

    }

    /**
     * 获取保险机构类型(OK)
     */
    private fun getBDType() {
        showLoadingDialog()
        mCollectBicyclePersenter!!.doNetworkTask(HashMap<String, String?>(), Constants.GET_COMPANY_NAME)
    }

    private fun bindEvent() {
        btn_ok.setOnClickListener {
            //            if(rb_bgm.isChecked){
//                getDZPZImage()
//            }else{
//                commitData()
//            }

            val map = HashMap<String, String?>()
            map["lsh"] = CollectPhotoActivity.mLsh
            map["xh"] = CollectPhotoActivity.mXh
            map["glbm "] = UserInfo.GLBM
            map["zt"] = "2" //流程状态 0-查验未通过，1-已查验，2-已登记，3-已制证，E-已归档，Q-已退办
            map["cyrsfzmhm"] = UserInfo.SFZMHM//查验人身份证明号码
            map["czpt"] = Constants.CZPT //查验平台

            map["ywlx"] = (intent.getSerializableExtra("all_data") as AllBikeMsgEnity).data.checkData.ywlx //业务类型（注册业务才有保险）
            mCollectBicyclePersenter!!.doNetworkTask(map, Constants.UPDATA_LS_ZT)

        }
        rb_buy.setOnCheckedChangeListener { _, position ->
            when (position) {
                R.id.rb_bgm -> {
                    sv_bdxx.visibility = View.INVISIBLE
                }
                R.id.btn_gm -> {
                    sv_bdxx.visibility = View.VISIBLE
                }
            }
        }
        tv_scan.setOnClickListener {
            if (mDeviceScanUtils == null) {
                mDeviceScanUtils = DeviceScanUtils(this, mHandler)
            }
            mDeviceScanUtils!!.startScan()
        }

        sp_bxmc.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                getCodeImage()
            }

        }
    }

    private fun commitData() {
        val map = HashMap<String, String?>()
        map.put("bxd", tv_bdh.text.toString())      //保险单
        map.put("bxgsmc", tv_bxgsmc.text.toString())//保险公司名称
        map.put("bxksrq", tv_qbrq.text.toString())  //保险开始时间
        map.put("bxjsrq", tv_zbrq.text.toString())  //保险结束时间
        map.put("bf", tv_bf.text.toString())        //保费
        map.put("bxcpbm", tv_cpbm.text.toString())  //保险产品编码

    }

    override fun onDestroy() {
        super.onDestroy()
        if (mDeviceScanUtils != null) {
            mDeviceScanUtils!!.releaseDeviceScan()
            mDeviceScanUtils = null
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_insurance
    }

}
