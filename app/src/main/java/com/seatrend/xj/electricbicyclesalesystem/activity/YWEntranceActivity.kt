package com.seatrend.xj.electricbicyclesalesystem.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteUtils
import com.seatrend.xj.electricbicyclesalesystem.entity.AllBikeMsgEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.*
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import com.seatrend.xj.electricbicyclesalesystem.zxing.activity.CaptureActivity
import kotlinx.android.synthetic.main.activity_yw.*
import java.util.*

class YWEntranceActivity : BaseActivity(), NormalView {

    private var mNormalPresenter: NormalPresenter? = null
    private var mDeviceScanUtils: DeviceScanUtils? = null
    private var mAllBikeMsgEnity: AllBikeMsgEnity? = null


    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        LoadingDialog.getInstance().dismissLoadDialog()
        if (Constants.YW_GET_ALL_BIKE_DATA.equals(commonResponse.getUrl())) {
            mAllBikeMsgEnity = GsonUtils.gson(commonResponse.getResponseString(), AllBikeMsgEnity::class.java)
            resetData()


            CarInfoByCyActivity.mAllBikeMsgEnity = mAllBikeMsgEnity
            intent.putExtra("ywlx", sp_yw.selectedItem.toString().split(":")[0])
            intent.setClass(this, CarInfoByCyActivity::class.java)
            startActivity(intent)
        }
    }

    private fun resetData() {
        mAllBikeMsgEnity!!.data.fjdcBusiness.zzxsz="2" //默认纸质不需要

        mAllBikeMsgEnity!!.data.fjdcBusiness.sjrsfzmlx = null
        mAllBikeMsgEnity!!.data.fjdcBusiness.sjrsfzmhm = null
        mAllBikeMsgEnity!!.data.fjdcBusiness.sjrxm = null
        mAllBikeMsgEnity!!.data.fjdcBusiness.sjrlxdh = null
        mAllBikeMsgEnity!!.data.fjdcBusiness.sjryjxzqh = null
        mAllBikeMsgEnity!!.data.fjdcBusiness.sjryjxxdz = null
        mAllBikeMsgEnity!!.data.fjdcBusiness.sjryzbm = null

        mAllBikeMsgEnity!!.data.fjdcBusiness.dlrsfzmlx = null
        mAllBikeMsgEnity!!.data.fjdcBusiness.dlrsfzmhm = null
        mAllBikeMsgEnity!!.data.fjdcBusiness.dlrxm = null
        mAllBikeMsgEnity!!.data.fjdcBusiness.dlrlxdh = null
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        LoadingDialog.getInstance().dismissLoadDialog()
        showErrorDialog(commonResponse.getResponseString())
    }

    override fun initView() {
        setPageTitle("业务登记")
        if(AppUtils.isApkInDebug(this)){
            et_yw_hgzbh.setText("A1325731119198001")
        }
        mNormalPresenter = NormalPresenter(this)
        mDeviceScanUtils = DeviceScanUtils(this, mHandler)
        setSpinnerAdapter(sp_yw)
        bindEvent()
    }

    @SuppressLint("HandlerLeak")
    var mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val hgzData = msg.obj as String
            if (!TextUtils.isEmpty(hgzData)) {
                qrCodeSubmmit(hgzData)
            }
        }
    }

    private fun bindEvent() {
        iv_scan.setOnClickListener {
            showQcodeModeDialog()
        }
        sp_yw.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var spString = sp_yw.selectedItem.toString().split(":")[1]
                if (getString(R.string.zcdj).equals(spString)) {
                    ViewShowUtils.showVisibleView(ll_cphm, ll_zcbm)
                } else if (getString(R.string.lshp).equals(spString)) {
                    ViewShowUtils.showGoneView(ll_cphm, ll_zcbm)
                } else {
                    ViewShowUtils.showVisibleView(ll_cphm)
                    ViewShowUtils.showGoneView(ll_zcbm)
                }

                when (spString) {
                    getString(R.string.zcdj) -> {
                        CarInfoByCyActivity.entranceFlag = Constants.CAR_ZC
                    }
                    getString(R.string.bgdj) -> {
                        CarInfoByCyActivity.entranceFlag = Constants.CAR_BG
                    }
                    getString(R.string.zydj) -> {
                        CarInfoByCyActivity.entranceFlag = Constants.CAR_ZY
                    }
                    getString(R.string.zxdj) -> {
                        CarInfoByCyActivity.entranceFlag = Constants.CAR_ZX
                    }
                    getString(R.string.bhpz) -> {
                        CarInfoByCyActivity.entranceFlag = Constants.CAR_BH
                    }
                    getString(R.string.jchp) -> {
                        CarInfoByCyActivity.entranceFlag = Constants.CAR_JCHP
                    }
                    getString(R.string.lshp) -> {
                        CarInfoByCyActivity.entranceFlag = Constants.CAR_LSHP
                    }
                }
            }
        }

        bt_next.setOnClickListener {
            if (sp_yw.selectedItem != null && "A".equals(sp_yw.selectedItem.toString().split(":")[0])) {  //注册登记
                showLoadingDialog()
                if (!ObjectNullUtil.checknull(et_yw_cphm.text.toString()) && !ObjectNullUtil.checknull(et_yw_hgzbh.text.toString())) {
                    showToast("整车编码为空或者车牌号为空")
                    return@setOnClickListener
                }
                val map = HashMap<String, String?>()
                if (ObjectNullUtil.checknull(et_yw_hgzbh.text.toString())) {
                    showToast("整车编码查询")
                    map.put("zcbm", et_yw_hgzbh.text.toString())
                    map.put("ywlx", sp_yw.selectedItem.toString().split(":")[0])
                    mNormalPresenter!!.doNetworkTask(map, Constants.YW_GET_ALL_BIKE_DATA)
                } else {
                    showToast("车牌号码查询")
                    map.put("hphm", et_yw_cphm.text.toString().toUpperCase())
                    map.put("ywlx", sp_yw.selectedItem.toString().split(":")[0])
                    mNormalPresenter!!.doNetworkTask(map, Constants.YW_GET_ALL_BIKE_DATA)
                }

            } else {
                if (!ObjectNullUtil.checknull(et_yw_cphm.text.toString())) {
                    showToast("车牌号码为空")
                    return@setOnClickListener
                }
                showLoadingDialog()
                showToast("车牌号码查询")
                val map = HashMap<String, String?>()
                map.put("hphm", et_yw_cphm.text.toString().toUpperCase())
                map.put("ywlx", sp_yw.selectedItem.toString().split(":")[0])
                mNormalPresenter!!.doNetworkTask(map, Constants.YW_GET_ALL_BIKE_DATA)
            }
        }
        et_yw_cphm.transformationMethod = CarHphmUtils.TransInformation()
        et_yw_cphm.filters = arrayOf(inputFilter)
    }

    /**
     * 二维码扫码方式
     */
    private fun showQcodeModeDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_qcode_picker)
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
        val btn_pda = dialog.findViewById<Button>(R.id.btn_pda)
        val btn_other = dialog.findViewById<Button>(R.id.btn_other)
        btn_pda.setOnClickListener {
            dialog.dismiss()

            if (mDeviceScanUtils == null) {
                mDeviceScanUtils = DeviceScanUtils(this, mHandler)
            }
            mDeviceScanUtils!!.startScan()
        }
        btn_other.setOnClickListener {
            dialog.dismiss()
            startActivityForResult(intent.setClass(this, CaptureActivity::class.java),0)
        }
    }

    private fun qrCodeSubmmit(qrcode: String) {
        val zcbm = ParseQcodeUtil.getZcbmString(qrcode)
        if (!ObjectNullUtil.checknull(zcbm)) {
            showToast("没找到整车编码字段")
            return
        }
        et_yw_hgzbh.setText(zcbm)
//        map["hgzewm"] = qrcode
//        showLoadingDialog()
//        mNormalPresenter!!.doNetworkTask(map, Constants.QR_CODE_ANALYSIS)
    }

    private fun setSpinnerAdapter(spinner: Spinner) {
        val adapter = ArrayAdapter<String>(this, R.layout.my_simple_spinner_item)
        adapter.setDropDownViewResource(R.layout.item_spinner__down_common)
        when (spinner.id) {
            R.id.sp_yw -> {
                val ywList = CodeTableSQLiteUtils.queryByDMLB(Constants.YWLX)
                for (db in ywList) {
                    val dmz = db.dmz
                    val dmsm1 = db.dmsm1
                    if ("A".equals(dmz) || "B".equals(dmz) || "D".equals(dmz) || "G".equals(dmz) || "K".equals(dmz)) {
                        adapter.add(dmz + ":" + dmsm1)
                    }
                }
                spinner.adapter = adapter
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mDeviceScanUtils!!.releaseDeviceScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(data != null){
                et_yw_hgzbh.setText(data.getStringExtra("result_zcbm"))
            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_yw
    }
}