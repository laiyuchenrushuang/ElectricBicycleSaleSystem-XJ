package com.seatrend.xj.electricbicyclesalesystem.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.widget.Button
import android.widget.RadioGroup
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.ZpbpEntity
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.*
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import com.seatrend.xj.electricbicyclesalesystem.zxing.activity.CaptureActivity
import kotlinx.android.synthetic.main.activty_zpbp.*
import kotlinx.android.synthetic.main.bottom_button.*

/**
 * Created by ly on 2020/5/27 15:25
 */
class ZPBPActivity : BaseActivity(),NormalView{

    private var mDeviceScanUtils: DeviceScanUtils? = null
    private var mNormalPresenter: NormalPresenter? = null

    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        if(Constants.URL_ZPBP == commonResponse.getUrl()){
            try {
                val enty = GsonUtils.gson(commonResponse.getResponseString(),ZpbpEntity::class.java)
                CollectPhotoActivity.photoEntranceFlag = Constants.YWBP
                CollectPhotoActivity.mLsh = enty.data.lsh
                CollectPhotoActivity.mXh = enty.data.xh
                intent.putExtra("photoentranceflag", Constants.YWBP)
                intent.putExtra("zplx", if(rb_cyzp.isChecked) "1" else "2")
                intent.setClass(this, CollectPhotoActivity::class.java)
                val bundle = Bundle()
                bundle.putParcelable("photo_list", enty)
                intent.putExtras(bundle)
                startActivity(intent)
            } catch (e: Exception) {
                showToast(e.message.toString())
            }
        }
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        showErrorDialog(commonResponse.getResponseString())
    }


    override fun initView() {
        setPageTitle("照片补拍")
        mNormalPresenter = NormalPresenter(this)
        mDeviceScanUtils = DeviceScanUtils(this, mHandler)
        bindEvent()
    }

    private fun bindEvent() {
        ll_scan.setOnClickListener {
            showQcodeModeDialog()
        }
        rg_cxtj.setOnCheckedChangeListener(object :RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
                when(p1){
                    R.id.rb_zcbm ->{
                        ViewShowUtils.showVisibleView(ll_zcbm)
                        ViewShowUtils.showGoneView(ll_hphm)
                    }

                    R.id.rb_hphm ->{
                        ViewShowUtils.showVisibleView(ll_hphm)
                        ViewShowUtils.showGoneView(ll_zcbm)
                    }
                }
            }

        })


        bt_next.setOnClickListener {

            if(rb_zcbm.isChecked){
                if (!ParseQcodeUtil.isZcbmString(et_ccc_zcbm.text.toString())) {
                    showToast("整车编码是否是15位数字?")
                    return@setOnClickListener
                }
                val map = HashMap<String,String>()
                map["zcbm"] = et_ccc_zcbm.text.toString().toUpperCase()
                map["type"] =  if(rb_cyzp.isChecked) "1" else "2"  // 1 为查验照片  2 为登记照片
                showLoadingDialog()
                mNormalPresenter!!.doNetworkTask(map,Constants.URL_ZPBP)
            }else{

                if (TextUtils.isEmpty(et_hphm.text.toString()) || !CphmUtils.checkXjValueCphm(et_hphm.text.toString().toUpperCase())) {
                    showToast("请正确输入车牌号")
                    return@setOnClickListener
                }
                val map = HashMap<String,String>()
                map["hphm"] = et_hphm.text.toString().toUpperCase()
                map["type"] =  if(rb_cyzp.isChecked) "1" else "2"  // 1 为查验照片  2 为登记照片
                showLoadingDialog()
                mNormalPresenter!!.doNetworkTask(map,Constants.URL_ZPBP)
            }

        }


        et_ccc_zcbm.filters = arrayOf(inputFilter)
        et_hphm.filters = arrayOf(inputFilter)
        et_ccc_zcbm.transformationMethod = CarHphmUtils.TransInformation()
        et_hphm.transformationMethod = CarHphmUtils.TransInformation()
        setSelection(et_hphm)
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

    private fun qrCodeSubmmit(qrcode: String) {
        val zcbm = ParseQcodeUtil.getZcbmString(qrcode)
        if (!ObjectNullUtil.checknull(zcbm)) {
            showToast("没找到整车编码字段")
            return
        }
        et_ccc_zcbm.setText(zcbm)
    }

    private fun showQcodeModeDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_qcode_picker)
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
        val btn_pda = dialog.findViewById<Button>(R.id.btn_pda)
        val btn_other = dialog.findViewById<Button>(R.id.btn_other)
        if (!AppUtils.HCPDA()) {
            btn_pda.isEnabled = false
        }
        btn_pda.setOnClickListener {
            dialog.dismiss()
            if (mDeviceScanUtils == null) {
                mDeviceScanUtils = DeviceScanUtils(this, mHandler)
            }
            mDeviceScanUtils!!.startScan()
        }
        btn_other.setOnClickListener {
            dialog.dismiss()
            startActivityForResult(intent.setClass(this, CaptureActivity::class.java), 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mDeviceScanUtils!!.releaseDeviceScan()
    }

    override fun getLayout(): Int {
        return R.layout.activty_zpbp
    }

}
