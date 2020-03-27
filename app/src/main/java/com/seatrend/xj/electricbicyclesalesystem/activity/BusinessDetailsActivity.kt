package com.seatrend.xj.electricbicyclesalesystem.activity

import android.app.Dialog
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.entity.BusinessEntity
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse
import com.seatrend.xj.electricbicyclesalesystem.entity.UserInfo
import com.seatrend.xj.electricbicyclesalesystem.persenter.BusinessDetailsPersenter
import com.seatrend.xj.electricbicyclesalesystem.util.GsonUtils
import com.seatrend.xj.electricbicyclesalesystem.util.LoadingDialog
import com.seatrend.xj.electricbicyclesalesystem.util.SharedPreferencesUtils
import com.seatrend.xj.electricbicyclesalesystem.util.StringUtils
import com.seatrend.xj.electricbicyclesalesystem.view.BusinessDetailsView
import kotlinx.android.synthetic.main.activity_tongji.*
import kotlinx.android.synthetic.main.activity_tongji.tv_name
import kotlinx.android.synthetic.main.activity_tongji.tv_tjqssj
import kotlinx.android.synthetic.main.activity_tongji.tv_tjzzsj
import java.lang.Exception

/**
 * Created by ly on 2019/10/25 14:26
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class BusinessDetailsActivity : BaseActivity(), BusinessDetailsView {
    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        LoadingDialog.getInstance().dismissLoadDialog()
        try {
            val businessEntity = GsonUtils.gson(commonResponse.getResponseString(), BusinessEntity::class.java)
            runOnUiThread {
                tv_ywzs.text = businessEntity.data.ywzs.toString()
                tv_dgd.text = businessEntity.data.dgd.toString()
                tv_dfh.text = businessEntity.data.dfh.toString()
                tv_ytb.text = businessEntity.data.ytb.toString()
                tv_ygd.text = businessEntity.data.ygd.toString()
            }
        } catch (e: Exception) {
            showToast(e.message.toString())
        }

    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        LoadingDialog.getInstance().dismissLoadDialog()
        showErrorDialog(commonResponse.getResponseString())
    }

    private var isViewCreated = false
    private var isUIVisible = false
    private var isFirst = true
    private var mBusinessDetailsPersenter: BusinessDetailsPersenter? = null
    override fun initView() {
        setPageTitle("业务统计")
        mBusinessDetailsPersenter = BusinessDetailsPersenter(this)
        tv_name.text = getString(R.string.who_hello, UserInfo.XM)
        tv_tjqssj.text = SharedPreferencesUtils.getTjqssj()
        tv_tjzzsj.text = SharedPreferencesUtils.getTjzzsj()
        tv_xsdmc.text = StringUtils.isNull(UserInfo.BMMC)  //20-03-26 duan 说改
        bindEvent()
        lazyLoad()
    }

    private fun bindEvent() {

        tv_xsdmc!!.movementMethod = ScrollingMovementMethod.getInstance()
        tv_tjqssj.setOnClickListener {
            showTimeDialog(0)
        }

        tv_tjzzsj.setOnClickListener {
            showTimeDialog(1)
        }
    }

    private fun showTimeDialog(tag: Int) {
        val dialog = Dialog(this)
        // MAlertDialog dialog=new MAlertDialog(this);
        dialog.setContentView(R.layout.dialog_date_picker)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        val btnCancel = dialog.findViewById<Button>(R.id.btn_cancel)
        val btnOk = dialog.findViewById<Button>(R.id.btn_ok)
        val mDatePicker = dialog.findViewById<DatePicker>(R.id.m_date_picker)

        btnCancel.setOnClickListener { dialog.dismiss() }

        btnOk.setOnClickListener {
            val year = mDatePicker.year
            val month = mDatePicker.month + 1
            val dayOfMonth = mDatePicker.dayOfMonth
            val time = "$year-$month-$dayOfMonth"

            if (tag == 0) {
                val startTime = StringUtils.dateToStamp(time)
                val endTime = StringUtils.dateToStamp(tv_tjzzsj.text.toString())
                if (startTime > endTime) {
                    showToast("起始时间不能大于终止时间")
                    return@setOnClickListener
                } else {
                    tv_tjqssj.text = time
                    SharedPreferencesUtils.setTjqssj(time)
                    dialog.dismiss()
                    getData()
                }

            } else if (tag == 1) {


                val startTime = StringUtils.dateToStamp(tv_tjqssj.text.toString())
                val endTime = StringUtils.dateToStamp(time)
                if (startTime > endTime) {
                    showToast("起始时间不能大于终止时间")
                    return@setOnClickListener
                } else if (endTime - System.currentTimeMillis() > 0) {
                    showToast("日期不能超过当前日期")
                    return@setOnClickListener
                } else {
                    tv_tjzzsj.text = time
                    SharedPreferencesUtils.setTjzzsj(time)
                    dialog.dismiss()
                    getData()
                }

            }


        }

        dialog.setOnDismissListener {
            // getData()
        }


    }

    private fun lazyLoad() {
//        if (isUIVisible && isViewCreated && isFirst){
//            isUIVisible=false
//            isViewCreated=false
//            isFirst=false
        getData()
//        }
    }

    private fun getData() {
        val map = HashMap<String, String>()
        map.put("djbm", UserInfo.GLBM)
        map.put("kssj", tv_tjqssj.text.toString() + " 00:00:00")
        map.put("jssj", tv_tjzzsj.text.toString() + " 23:59:59")
        showLoadingDialog()
        mBusinessDetailsPersenter!!.doNetworkTask(map, Constants.BUSINESS_MESSAGE)
    }

    override fun getLayout(): Int {
        return R.layout.activity_tongji
    }
}