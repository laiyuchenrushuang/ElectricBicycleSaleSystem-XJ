package com.seatrend.xj.electricbicyclesalesystem.activity

import android.Manifest
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import java.util.logging.Handler as Handler1
import android.os.Handler
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.view.WindowManager
import java.util.ArrayList


/**
 * Created by ly on 2019/7/29 17:49
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class LoginLoadingActivity : BaseActivity() {
    val time: Long = 2000
    override fun initView() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        startLoginActivity()
    }

    private fun startLoginActivity() {
        val handler = Handler()
        handler.postDelayed({
            startActivity(Intent(this@LoginLoadingActivity, LoginByUserPasswordActivity::class.java))
//            startActivity(Intent(this@LoginLoadingActivity, LoginActivity::class.java))
            finish()
            overridePendingTransition(R.anim.zoomin, R.anim.zoomout)
        }, time)
    }

    override fun getLayout(): Int {
        return R.layout.activity_login_loading
    }
}