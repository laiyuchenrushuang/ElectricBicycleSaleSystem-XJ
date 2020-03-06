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
import com.seatrend.xj.electricbicyclesalesystem.entity.CYEntranceEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.ThreeCEnity
import com.seatrend.xj.electricbicyclesalesystem.entity.UserInfo
import com.seatrend.xj.electricbicyclesalesystem.persenter.NormalPresenter
import com.seatrend.xj.electricbicyclesalesystem.util.*
import com.seatrend.xj.electricbicyclesalesystem.view.NormalView
import com.seatrend.xj.electricbicyclesalesystem.zxing.activity.CaptureActivity
import kotlinx.android.synthetic.main.activity_chayan_entrance.*
import kotlinx.android.synthetic.main.bottom_button.*
import java.util.*

/**
 * 查验入口界面
 */
class ChaYanEntranceActivity : BaseActivity(), NormalView {
    private var mDeviceScanUtils: DeviceScanUtils? = null
    private var mNormalPresenter: NormalPresenter? = null
    var cYEntranceEnity: CYEntranceEnity? = null
    var cThreeCEnity: ThreeCEnity? = null

    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        //set 合格证编号
        LoadingDialog.getInstance().dismissLoadDialog()

        try {
            if (Constants.CY_ENTRANCE.equals(commonResponse.getUrl())) {
                cYEntranceEnity = GsonUtils.gson(commonResponse.getResponseString(), CYEntranceEnity::class.java)

                if (!SpinnerUtil.checkSpinnerValuable(sp_cy)) {
                    showToast("业务类型获取失败,请同步代码")
                    return
                }

                intent.putExtra("ywlx", sp_cy.selectedItem.toString().split(":")[0]) // 业务类型
                intent.putExtra("ywyy", if (null == sp_ywyy.selectedItem) "" else sp_ywyy.selectedItem.toString().split(":")[0]) // 业务原因
                intent.putExtra("lsh", cYEntranceEnity!!.data.lsh) // 流水
                intent.putExtra("xh", cYEntranceEnity!!.data.xh) // 序号
                intent.putExtra("zcbm", et_cy_zcbm.text.toString()) // 整车编码
                if (!"A".equals(sp_cy.selectedItem.toString().split(":")[0])) {
                    intent.putExtra("hphm", et_cy_cphm.text.toString().toUpperCase()) // 车牌号码
                }
                intent.putExtra("all_data",cYEntranceEnity)
                intent.setClass(this, CarInfoActivity::class.java)
                startActivity(intent)
            }

            if(Constants.CY_ENTRANCE_3C == commonResponse.getUrl()){
                cThreeCEnity = GsonUtils.gson(commonResponse.getResponseString(), ThreeCEnity::class.java)
                if (cThreeCEnity == null || cThreeCEnity!!.data == null ||cThreeCEnity!!.data.threeCertificates ==null ||cThreeCEnity!!.data.threeCertificates.data ==null) {
                    showToast("注册登记3C信息获取为空")
                    return
                }
                intent.putExtra("3c_data",cThreeCEnity)
                val map = HashMap<String, String?>()
                map["glbm"] = UserInfo.GLBM
                map["zcbm"] = et_cy_zcbm.text.toString()
                map["ywlx"] = sp_cy.selectedItem.toString().split(":")[0]
                mNormalPresenter!!.doNetworkTask(map, Constants.CY_ENTRANCE)
            }

        } catch (e: Exception) {
            showToast(e.message.toString())
        }
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        LoadingDialog.getInstance().dismissLoadDialog()
        showErrorDialog(commonResponse.getResponseString())
    }

    override fun initView() {
        setPageTitle("车辆查验")
        if(AppUtils.isApkInDebug(this)){
            et_cy_zcbm.setText("117321900000195")
        }
        mNormalPresenter = NormalPresenter(this)
        mDeviceScanUtils = DeviceScanUtils(this, mHandler)
        initSp()
        bindEvent()
    }

    private fun initSp() {
        setSpinnerAdapter(sp_cy)
    }

    private fun bindEvent() {
        bt_next.setOnClickListener {

            try {
                var flag = sp_cy.selectedItem.toString().split(":")[0]
                when (flag) {
                    "A" -> {   // 注册登记    Only 整车编码
                        if(!ParseQcodeUtil.isZcbmString(et_cy_zcbm.text.toString())){
                            showToast("整车编码是否是15位数字?")
                            return@setOnClickListener
                        }
                        showLoadingDialog()
                        val map1 = HashMap<String, String?>()
                        map1["clsbdh"] = et_cy_zcbm.text.toString()
                        mNormalPresenter!!.doNetworkTask(map1, Constants.CY_ENTRANCE_3C)
                    }
                    "B" -> {   // 转移登记  only 车牌号
                        et_cy_zcbm.setText("")
                        if (TextUtils.isEmpty(et_cy_cphm.text.toString())) {
                            showToast("请正确输入车牌号")
                            return@setOnClickListener
                        }
                        showLoadingDialog()
                        val map = HashMap<String, String?>()
                        map["hphm"] = et_cy_cphm.text.toString().toUpperCase()
                        map["glbm"] = UserInfo.GLBM
                        map["ywlx"] = sp_cy.selectedItem.toString().split(":")[0]
                        mNormalPresenter!!.doNetworkTask(map, Constants.CY_ENTRANCE)
                    }
                    "D" -> {   // 变更登记  only 车牌号
                        et_cy_zcbm.setText("")
                        if (TextUtils.isEmpty(et_cy_cphm.text.toString())) {
                            showToast("请正确输入车牌号")
                            return@setOnClickListener
                        }
                        showLoadingDialog()
                        val map = HashMap<String, String?>()
                        map["hphm"] = et_cy_cphm.text.toString().toUpperCase()
                        map["glbm"] = UserInfo.GLBM
                        map["ywlx"] = sp_cy.selectedItem.toString().split(":")[0]
                        mNormalPresenter!!.doNetworkTask(map, Constants.CY_ENTRANCE)
                    }
                    "J" -> {   // 旧车登记  only 车牌号
                        et_cy_zcbm.setText("")
                        if (TextUtils.isEmpty(et_cy_cphm.text.toString())) {
                            showToast("请正确输入车牌号")
                            return@setOnClickListener
                        }
                        showLoadingDialog()
                        val map = HashMap<String, String?>()
                        map.put("hphm", et_cy_cphm.text.toString().toUpperCase())
                        map.put("glbm", UserInfo.GLBM)
                        map.put("ywlx", sp_cy.selectedItem.toString().split(":")[0])
                        mNormalPresenter!!.doNetworkTask(map, Constants.CY_ENTRANCE)
                    }
                }
            } catch (e: Exception) {
                showToast(e.message.toString())
            }

        }
        sp_cy.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                var ywString = sp_cy.selectedItem.toString().split(":")[0]
                if ("D".equals(ywString) || "B".equals(ywString)) {
                    ll_cphm.visibility = View.VISIBLE
                    ll_zcbm.visibility = View.GONE
                    ll_ywyy.visibility = View.VISIBLE
                    if ("D".equals(ywString)) {
                        setSpinnerAdapter(sp_ywyy)
                    } else if ("B".equals(ywString)) {
                        setSpinnerAdapter(sp_ywyy)
                    }
                } else {
                    ll_ywyy.visibility = View.GONE
                    if ("J".equals(ywString)) {
                        ll_cphm.visibility = View.VISIBLE
                        ll_zcbm.visibility = View.GONE
                    } else {
                        ll_cphm.visibility = View.GONE
                        ll_zcbm.visibility = View.VISIBLE
                    }
                }
            }
        }
        iv_scan.setOnClickListener {
            showQcodeModeDialog()
        }
        et_cy_cphm.transformationMethod = CarHphmUtils.TransInformation()
        et_cy_cphm.filters = arrayOf(inputFilter)
        et_cy_zcbm.transformationMethod = CarHphmUtils.TransInformation()
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
        if(!AppUtils.HCPDA()){
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
            startActivityForResult(intent.setClass(this, CaptureActivity::class.java),0)
        }
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
        et_cy_zcbm.setText(zcbm)
    }

    private fun setSpinnerAdapter(spinner: Spinner) {
        val adapter = ArrayAdapter<String>(this, R.layout.my_simple_spinner_item)
        adapter.setDropDownViewResource(R.layout.item_spinner__down_common)
        when (spinner.id) {
            R.id.sp_cy -> {
                val ywList = CodeTableSQLiteUtils.queryByDMLB(Constants.YWLX)

                for (db in ywList) {
                    val dmz = db.dmz
                    val dmsm1 = db.dmsm1
                    if ("A" == dmz || "B" == dmz || "D" == dmz) {  // 查验支持四大业务 注册 变更 转移
                        adapter.add("$dmz:$dmsm1")
                    }
                }
                spinner.adapter = adapter
            }
            R.id.sp_ywyy -> {
                adapter.clear()
                if ("D" == sp_cy.selectedItem.toString().split(":")[0]) {
                    val bgyyList = CodeTableSQLiteUtils.queryByDMLB(Constants.BG_YY)
                    for (db in bgyyList) {
                        val dmz = db.dmz.trim()
                        val dmsm1 = db.dmsm1.trim()
                        adapter.addAll("$dmz:$dmsm1")
                    }
                    spinner.adapter = adapter
                } else {
                    val zyyyList = CodeTableSQLiteUtils.queryByDMLB(Constants.ZY_YY)
                    for (db in zyyyList) {
                        val dmz = db.dmz.trim()
                        val dmsm1 = db.dmsm1.trim()
                        adapter.addAll(dmz + ":" + dmsm1)
                    }
                    spinner.adapter = adapter
                }
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
                et_cy_zcbm.setText(data.getStringExtra("result_zcbm"))
            }
        }
    }


    override fun getLayout(): Int {
        return R.layout.activity_chayan_entrance
    }
}
