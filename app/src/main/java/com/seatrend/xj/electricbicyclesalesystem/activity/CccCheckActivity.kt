package com.seatrend.xj.electricbicyclesalesystem.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.widget.Button
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.util.*
import com.seatrend.xj.electricbicyclesalesystem.zxing.activity.CaptureActivity
import kotlinx.android.synthetic.main.activity_ccccheck.*
import kotlinx.android.synthetic.main.bottom_button.*


class CccCheckActivity : BaseActivity() {

    private var mDeviceScanUtils: DeviceScanUtils? = null


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

    override fun initView() {
        setPageTitle(resources.getString(R.string.ccc_check_title))
        bt_next.text = resources.getString(R.string.ccc_check_search)
        mDeviceScanUtils = DeviceScanUtils(this, mHandler)

        bindEvent()
    }

    private fun bindEvent() {
        ll_scan.setOnClickListener {
            showQcodeModeDialog()
        }
        bt_next.setOnClickListener {
            if (!ParseQcodeUtil.isZcbmString(et_ccc_zcbm.text.toString())) {
                showToast("整车编码是否是15位数字?")
                return@setOnClickListener
            }
            intent.setClass(this,CccDetailActivity::class.java)
            intent.putExtra("ccc_check_zcbm",et_ccc_zcbm.text.toString().toUpperCase())
            startActivity(intent)
        }
        et_ccc_zcbm.transformationMethod = CarHphmUtils.TransInformation()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                et_ccc_zcbm.setText(data.getStringExtra("result_zcbm"))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mDeviceScanUtils!!.releaseDeviceScan()
    }

    override fun getLayout(): Int {
        return R.layout.activity_ccccheck
    }


}
