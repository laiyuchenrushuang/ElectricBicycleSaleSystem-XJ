package com.seatrend.xj.electricbicyclesalesystem.activity

import android.content.Intent
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.manager.AppManager
import kotlinx.android.synthetic.main.activity_employee_remind.*
import kotlinx.android.synthetic.main.bottom_button.*


/**
 * Created by ly on 2019/10/28 16:08
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class EmployeeRemindActivity : BaseActivity(){
    override fun initView() {
        setPageTitle("提示信息")
        bt_next.text = "完成"
        getData()
        bindEvent()
    }

    private fun getData() {
        tv_xm.text = intent.getStringExtra("yg_xm")
        tv_yhm.text = intent.getStringExtra("yg_sfz")
        tv_psw.text = "888888"
    }

    private fun bindEvent() {
        bt_next.setOnClickListener {
            val intent = Intent(this, EmployeeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_employee_remind
    }
}