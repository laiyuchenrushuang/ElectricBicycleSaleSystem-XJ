package com.seatrend.xj.electricbicyclesalesystem.activity

import android.content.Intent
import android.view.KeyEvent
import android.view.View
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.manager.AppManager
import com.seatrend.xj.electricbicyclesalesystem.util.PhotoFileUtils
import kotlinx.android.synthetic.main.activity_remind_common.*
import kotlinx.android.synthetic.main.activity_remind_common.btn_back_home
import kotlinx.android.synthetic.main.activity_remind_cy.*

/**
 * Created by ly on 2019/10/28 10:14
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class RemindCommonActivity : BaseActivity() {
    override fun initView() {
        setPageTitle("操作详情")
        when {
            "1" == intent.getStringExtra(Constants.FORBIDDEN) -> {
                tv_cz.text = "禁用成功"
                btn_back_home.text = "返回上一页"
            }
            "2" == intent.getStringExtra(Constants.FORBIDDEN) -> {
                tv_cz.text = "启用成功"
                btn_back_home.text = "返回上一页"
            }
            else -> tv_cz.text = "操作成功"
        }
        btn_back_home.setOnClickListener {
            comeBack()
        }
        ivBack!!.visibility = View.GONE
    }

    private fun comeBack() {
        val intent = Intent(this, EmployeeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
//            PhotoFileUtils.deleteCaptruePhotoFile()
        finish()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            comeBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


    override fun getLayout(): Int {
        return R.layout.activity_remind_common
    }
}