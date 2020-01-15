package com.seatrend.xj.electricbicyclesalesystem.activity

import android.text.TextUtils
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.util.AppUtils
import com.seatrend.xj.electricbicyclesalesystem.util.SharedPreferencesUtils
import kotlinx.android.synthetic.main.activity_service_setting.*

class ServiceSettingActivity : BaseActivity() {


    override fun initView() {
        setPageTitle(getString(R.string.server_setting))
        et_network.setText(SharedPreferencesUtils.getNetworkAddress())

        btn_ok.setOnClickListener {
            if (TextUtils.isEmpty(et_network.text.toString())) {
                showToast("请输入网络地址")

                return@setOnClickListener

            }
            SharedPreferencesUtils.setNetworkAddress(et_network.text.toString())
            finish()
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_service_setting
    }
}
