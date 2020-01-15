package com.seatrend.xj.electricbicyclesalesystem.activity

import android.content.Intent
import android.graphics.BitmapFactory
import android.text.TextUtils
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.util.StringUtils
import kotlinx.android.synthetic.main.activity_dzpz.*


class RemindDZPZactivity: BaseActivity() {
    override fun initView() {
        setPageTitle("扫码领凭证")
        val url=intent.getStringExtra("url")
        if(!TextUtils.isEmpty(url)){
            val bitmap= StringUtils.createQRCode(url)
            iv_ewm.setImageBitmap(bitmap)
        }else{
            iv_ewm.setImageBitmap(BitmapFactory.decodeResource(resources,R.drawable.error_image))
        }


        btn_back_home.setOnClickListener {
            val intent= Intent(this, MainOtherActivity::class.java)
            intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_dzpz
    }
}
