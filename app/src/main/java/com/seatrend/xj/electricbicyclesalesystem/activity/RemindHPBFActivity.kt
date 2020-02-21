package com.seatrend.xj.electricbicyclesalesystem.activity

import android.content.Intent
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.util.PhotoFileUtils
import kotlinx.android.synthetic.main.activity_hpbf_remind.*

class RemindHPBFActivity : BaseActivity(){
    override fun initView() {
       setPageTitle("提示信息")
        btn_back_home.setOnClickListener {
//            PhotoFileUtils.deleteCaptruePhotoFile()
            val intent= Intent(this, MainOtherActivity::class.java)
            intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_hpbf_remind
    }
}
