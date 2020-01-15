package com.seatrend.xj.electricbicyclesalesystem.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import kotlinx.android.synthetic.main.activity_help.*

class HelpActivity : BaseActivity() {

    override fun initView() {
        setPageTitle("帮助")
        ll_exit_login.setOnClickListener {
            startActivity(Intent(this, ExitLoginActivity::class.java))
        }
    }

    override fun getLayout(): Int {
        return  R.layout.activity_help
    }
}
