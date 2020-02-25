package com.seatrend.xj.electricbicyclesalesystem.activity

import android.os.Bundle
import android.view.KeyEvent
import android.view.Window
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import kotlinx.android.synthetic.main.activity_show_photo.*
import kotlinx.android.synthetic.main.common_title.*

class ShowPhotoActivity : BaseActivity() {

    override fun initView() {
//        val mPhotoView=findViewById<PhotoView>(R.id.iv_photo)

        if(null != intent.getStringExtra(Constants.ZPLX)){
            setPageTitle(intent.getStringExtra(Constants.ZPLX))
        }else{
            setPageTitle("图片详情")
        }

        Glide.with(this).load(intent.getStringExtra(Constants.PATH)).placeholder(R.drawable.image_loading)
                .error(R.drawable.error_image).into(iv_photo)
        bindEvent()
    }

    private fun bindEvent() {
        iv_back.setOnClickListener {
            finish()
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_show_photo
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
         window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            finish()
            exitRotateAlphaAcaleAnimation()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
