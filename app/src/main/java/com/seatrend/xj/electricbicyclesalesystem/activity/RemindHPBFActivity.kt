package com.seatrend.xj.electricbicyclesalesystem.activity

import android.content.Intent
import android.view.View
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.manager.AppManager
import com.seatrend.xj.electricbicyclesalesystem.util.PhotoFileUtils
import kotlinx.android.synthetic.main.activity_hpbf_remind.*
import kotlinx.android.synthetic.main.activity_hpbf_remind.bt_pdf
import kotlinx.android.synthetic.main.activity_hpbf_remind.btn_back_home
import kotlinx.android.synthetic.main.activity_remind_cy.*

class RemindHPBFActivity : BaseActivity() {
    override fun initView() {
        setPageTitle("提示信息")
        bindEvent()
    }

    private fun bindEvent() {
        btn_back_home.setOnClickListener {
            //            PhotoFileUtils.deleteCaptruePhotoFile()
            val intent = Intent(this, MainOtherActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

//        bt_pdf.visibility = View.GONE
        bt_pdf.setOnClickListener {
            intent.putExtra("pdf_lx", "1")//登记
            intent.setClass(this, PDFActivity::class.java)
            startActivity(intent)
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_hpbf_remind
    }
}
