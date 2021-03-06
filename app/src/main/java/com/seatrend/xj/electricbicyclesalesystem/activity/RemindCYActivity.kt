package com.seatrend.xj.electricbicyclesalesystem.activity

import android.content.Intent
import android.view.KeyEvent
import android.view.View
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.manager.AppManager
import com.seatrend.xj.electricbicyclesalesystem.util.PhotoFileUtils
import kotlinx.android.synthetic.main.activity_remind_cy.*


/**
 * Created by ly on 2019/10/22 9:26
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class RemindCYActivity : BaseActivity() {
    override fun initView() {
        setPageTitle("提示信息")
        initData()
        bindEvent()
    }

    private fun initData() {
        if ("1".equals(intent.getStringExtra("cyjlBj"))) {
            btn_back_ywdj.visibility = View.GONE
        }
    }

    private fun bindEvent() {
        btn_back_ywdj.setOnClickListener {
            val intent = Intent(this, YWEntranceActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
//            PhotoFileUtils.deleteCaptruePhotoFile()
        }
        btn_back_home.setOnClickListener {
            comeBack()
        }

//        bt_pdf.visibility=View.GONE
        bt_pdf.setOnClickListener {
            intent.putExtra("pdf_lx", "0") //查验
            intent.setClass(this, PDFActivity::class.java)
            startActivity(intent)
        }
        ivBack!!.visibility = View.GONE
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            comeBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun comeBack() {
        val intent = Intent(this, MainOtherActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
//            PhotoFileUtils.deleteCaptruePhotoFile()
        finish()
    }

    override fun getLayout(): Int {
        return R.layout.activity_remind_cy
    }
}